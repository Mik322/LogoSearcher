package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.Server;
import server.objects.Task;

public abstract class DealWith extends Thread {
	
	protected Server server;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	public DealWith(Server server,ObjectInputStream in, ObjectOutputStream out) {
		this.server = server;
		this.in = in;
		this.out = out;
	}
	
	public void run() {
		try {
			serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	abstract void serve() throws IOException;

	public void sendToServer(Task task) {
		server.sendTask(task);
	}
}
