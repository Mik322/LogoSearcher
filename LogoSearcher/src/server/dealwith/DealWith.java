package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jobs.Job;
import server.Server;

public abstract class DealWith extends Thread {
	
	private Server server;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	public DealWith(Server server,ObjectInputStream in, ObjectOutputStream out) {
		this.server = server;
		this.in = in;
		this.out = out;
	}
	
	abstract void serve() throws IOException;

	public void sendToServer(Job job) {
		server.sendJob(job);
	}
}
