package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import server.Server;
import server.objects.Task;
import streamedobjects.Job;

public class DealWithClient extends DealWith {

	private Results results;

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
	}

	@Override
	void serve() throws IOException {
		out.writeObject(server.getTypesAvailable());
		out.flush();
		while (true) {
			try {
				Job job = (Job) in.readObject();
				results = new Results(job.getTypes().size(), job.getImgs().size());
				int index = 0;
				for (byte[] img : job.getImgs()) {
					results.createEntry(index);
					for (String t : job.getTypes()) {
						sendToServer(new Task(img, job.getSubimg(), this, t, index));
					}
					index++;
				}
			} catch (ClassNotFoundException e) {
			}
			waitResults();
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

	public synchronized void receiveResult(int imgIndex, ArrayList<Point[]> points) {
		results.addResult(imgIndex, points);
		notifyAll();
	}

	private class Results {
		private HashMap<Integer, ArrayList<Point[]> > resultsMap = new HashMap<>();
		private int numReceived;
		private int expectedResultsNum;

		private Results(int numTypes, int numImages) {
			numReceived = 0;
			expectedResultsNum = numTypes * numImages;
		}

		private void createEntry(int imgIndex) {
			resultsMap.put(new Integer(imgIndex), new ArrayList<>());
		}

		private void addResult(int index, ArrayList<Point[]> points) {
			numReceived++;
			for (Point[] p : points) {
				resultsMap.get(new Integer(index)).add(p);
			}
		}
	}

}
