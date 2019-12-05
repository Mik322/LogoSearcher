package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.Server;
import server.objects.BlockingQueue;
import server.objects.Task;
import streamedobjects.SearchTask;

public class DealWithWorker extends DealWith {

	private String type;
	private Task task = null;
	private TimeCounter tc;;
	private boolean waitingResult = false;
	private Socket s;

	public DealWithWorker(Server server, ObjectInputStream in, ObjectOutputStream out, Socket s) {
		super(server, in, out);
		this.s = s;
	}

	@Override
	void serve() {
		try {
			type = (String) in.readObject();
			server.addSearchType(type);
		} catch (ClassNotFoundException | IOException e) {
		}
		sendTask();
	}
	
	private synchronized void sendTask() {
		try {
			ReceiveResult rr = new ReceiveResult(in, this);
			rr.start();
			while (true) {
				while(waitingResult) {
					wait();
				}
				task = server.getQueue(type).poll();
				out.writeObject(new SearchTask(task.getImg(), task.getSubimg()));
				out.flush();
				waitingResult = true;
				tc = new TimeCounter();
				tc.start();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void workerDisconnected(Task task) {
		BlockingQueue<Task> queue = server.getQueue(type);
		queue.offer(task);
		if (server.isLastWorker(type)) {
			while (queue.getSize() != 0) {
				Task t = queue.poll();
				t.getDwc().receiveResult(t.getIndex(), new ArrayList<Point[]>());
			}
		}
	}
	
	private synchronized void notifySend() {
		task = null;
		waitingResult = false;
		notify();
	}

	private class ReceiveResult extends Thread {
		private ObjectInputStream in;
		private DealWithWorker dww;

		private ReceiveResult(ObjectInputStream in, DealWithWorker dww) {
			this.in = in;
			this.dww = dww;
		}

		@SuppressWarnings("unchecked")
		public synchronized void run() {
			try {
				while (true) {
					try {
						ArrayList<Point[]> points = (ArrayList<Point[]>) in.readObject();
						tc.interrupt();
						task.getDwc().receiveResult(task.getIndex(), points); // Envia o resultado para o DWC
						notifySend();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				dww.interrupt();
				if (task != null) {
					workerDisconnected(task);
				}
				server.eliminateWorker(type);
			}
		}
	}

	private class TimeCounter extends Thread {
		private static final int MAX_TIME = 10000;

		public void run() {
			try {
				sleep(MAX_TIME);
				s.close();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
