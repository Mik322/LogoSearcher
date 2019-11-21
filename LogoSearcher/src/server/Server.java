package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.taskqueue.TaskMap;
import streamedobjects.Task;

public class Server {

	private static final int PORTO = 8080;
	private final ExecutorService pool = Executors.newFixedThreadPool(2);
	private TaskMap tasks = new TaskMap();
	
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

	public static void main(String[] args) {

		try {
			Server server = new Server();
			server.startServing();
		} catch (IOException e) {

		}

	}

}
