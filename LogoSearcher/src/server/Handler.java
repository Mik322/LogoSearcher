package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Handler implements Runnable {

	private Socket s;
	private Server server;
	
	private static int CLIENT = 1;
	private static int WORKER = 1;
	
	@Override
	public void run() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			int type = (int) in.read();
			if (type == CLIENT) {
				new DealWithClient(server, in, out).start();
			} else if (type == WORKER) {
				new DealWithServer(server, in, out).start();
			}
		} catch (IOException e) {}
	}
	
	public Handler(Socket s, Server server) {
		this.s = s;
		this.server = server;
	}

}
