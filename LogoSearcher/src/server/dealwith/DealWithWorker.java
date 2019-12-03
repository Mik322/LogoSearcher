package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import server.Server;
import server.objects.BlockingQueue;
import server.objects.Task;
import streamedobjects.SearchTask;

public class DealWithWorker extends DealWith {

	private String type;

	public DealWithWorker(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
	}

	@Override
	void serve() {
		try {
			type = (String) in.readObject();
			server.addSearchType(type);
		} catch (ClassNotFoundException | IOException e) {
		}
		Task task = null;
		try {
			while (true) {
				try {
					task = server.getQueue(type).poll();
					out.writeObject(new SearchTask(task.getImg(), task.getSubimg()));
					out.flush();
					@SuppressWarnings("unchecked")
					ArrayList<Point[]> points = (ArrayList<Point[]>) in.readObject(); // Recebe os pontos
					task.getDwc().receiveResult(task.getIndex(), points); // Envia o resultado para o DWC correspondente
					task = null;
				} catch (ClassNotFoundException e) {
				}
			}
		} catch (IOException e) {
			if (task!=null) {
				workerDisconnected(task);
			}
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
		server.eliminateWorker(type);
	}

}
