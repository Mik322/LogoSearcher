package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import server.Server;
import server.dealwith.comparator.ResultsComparator;
import server.objects.Task;
import streamedobjects.Job;

public class DealWithClient extends DealWith {

	private Results results;

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
		server.addDWC(this);
	}

	@Override
	void serve() {
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
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
			out.writeObject(sortMap());
		} catch (IOException e) {
		}
	}

	private HashMap<Integer, ArrayList<Point[]>> sortMap() {
		HashMap<Integer, ArrayList<Point[]>> sortedMap = new HashMap<>();
		for (Integer i : results.resultsMap.keySet()) {
			if (results.resultsMap.get(i).size() != 0) {
				sortedMap.put(i, results.resultsMap.get(i));
			}
		}
		List<ArrayList<Point[]>> listBySize = new ArrayList<>(sortedMap.values());
		Collections.sort(listBySize, new ResultsComparator());
		return sortedMap;
	}

	public synchronized void receiveResult(int imgIndex, ArrayList<Point[]> points) {
		results.addResult(imgIndex, points);
		if (results.numReceived == results.expectedResultsNum)
			notify();
	}

	private class Results {
		private HashMap<Integer, ArrayList<Point[]>> resultsMap = new HashMap<>();
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

	public void sendTypes(ArrayList<String> typesAvailable) {
		try {
			out.writeObject(typesAvailable);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
