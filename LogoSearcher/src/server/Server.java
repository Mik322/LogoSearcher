package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.taskqueue.TaskMap;
import server.taskqueue.TaskQueue;
import streamedobjects.Task;

public class Server {

	private static final int PORTO = 8080;
	private final ExecutorService pool = Executors.newFixedThreadPool(2);
	private TaskMap tasks = new TaskMap();
	private HashMap<String, Integer> types = new HashMap<>();
	
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
		}
		tasks.addType(type);
	}

	public Set<String> getTypesAvailable() {
		return types.keySet();
	}
	
	public static void main(String[] args) {

		try {
			Server server = new Server();
			server.startServing();
		} catch (IOException e) {

		}

	}

	public TaskQueue getQueue(String type) {
		return tasks.getQueue(type);
	}


}
