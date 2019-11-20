package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class DealWith extends Thread {
	
	private Server server;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	public DealWith(Server server,ObjectInputStream in, ObjectOutputStream out) {
		this.server = server;
		this.out = out;
	}
	
	abstract void serve() throws IOException;
}
