package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import server.Server;
import streamedobjects.Job;
import streamedobjects.Task;

public class DealWithClient extends DealWith {

	private Results results;

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
		try {
			serve();
		} catch (IOException e) {
		}
	}

	@Override
	void serve() throws IOException {
		System.out.println("Conectado a novo cliente");
		out.writeObject(server.getTypesAvailable());
		out.flush();
		while (true) {
			try {
				Job job = (Job) super.in.readObject();
				results = new Results(job.getTypes().size(), job.getImgs().size());
				for (byte[] img : job.getImgs()) {
					results.createEntry(img);
					for (String t : job.getTypes()) {
						sendToServer(new Task(img, job.getSubimg(), this, t));
					}
				}
				waitResults();
			} catch (ClassNotFoundException e) {
			}
		}
	}

	private synchronized void waitResults() {
		while (results.expectedResultsNum > results.numReceived) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		// manda resultados para cliente
		try {
			out.writeObject(results.resultsMap);
		} catch (IOException e) {
		}
	}

	public synchronized void receiveResult(byte[] img, ArrayList<Point[]> points) {
		results.addResult(img, points);
		notifyAll();
	}

	private class Results {
		private HashMap<byte[], ArrayList<Point[]> > resultsMap = new HashMap<>();
		private int numReceived;
		private int expectedResultsNum;

		private Results(int numTypes, int numImages) {
			numReceived = 0;
			expectedResultsNum = numTypes * numImages;
		}

		private void createEntry(byte[] img) {
			resultsMap.put(img, new ArrayList<>());
		}

		private void addResult(byte[] img, ArrayList<Point[]> points) {
			numReceived++;
			for (Point[] p : points) {
				resultsMap.get(img).add(p);
			}
		}
	}

}
