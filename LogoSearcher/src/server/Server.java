package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import server.objects.BlockingQueue;
import server.objects.TaskMap;
import server.objects.threadpool.Handler;
import server.objects.threadpool.ThreadPool;
import streamedobjects.Task;

public class Server {

	private final int PORTO;
	private final ThreadPool pool = new ThreadPool(2);
	private TaskMap tasks = new TaskMap();
	private HashMap<String, Integer> types = new HashMap<>();
	
	public Server(String port) {
		PORTO = Integer.parseInt(port);
	}

	public void startServing() throws IOException {
		ServerSocket s = new ServerSocket(PORTO);
		System.out.println("Lançou ServerSocket: " + s);
		try {
			while (true) {
				pool.execute(new Handler(s.accept(),this));
			}

		} finally {
			pool.shutdown();
			s.close();
		}
	}
	
	public void sendTask(Task task) {
		tasks.add(task);
	}

	//Adiciona tipo de pesquisa ao map
	public synchronized void addSearchType(String type) {
		if (types.containsKey(type)) {
			types.put(type, types.get(type)+1);
		} else {
			types.put(type, 1);
			tasks.addType(type);
		}
		System.out.println(types.keySet().toString());
	}

	public ArrayList<String> getTypesAvailable() {
		ArrayList<String> typesList = new ArrayList<>();
		for (String t : types.keySet()) {
			typesList.add(t);
		}
		return typesList;
	}
	
	public static void main(String[] args) {

		try {
			Server server = new Server(args[0]);
			server.startServing();
		} catch (IOException e) {

		}

	}

	public BlockingQueue<Task> getQueue(String type) {
		return tasks.getQueue(type);
	}


}
