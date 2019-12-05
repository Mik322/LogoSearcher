package server.objects.threadpool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.Server;
import server.dealwith.DealWithClient;
import server.dealwith.DealWithWorker;

public class Handler implements Runnable {

	private Socket s;
	private Server server;
	
	private static int CLIENT = 1;
	private static int WORKER = 2;
	
	@Override
	public void run() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			int type = in.readInt();
			if (type == CLIENT) {
				new DealWithClient(server, in, out).start();
			} else if (type == WORKER) {
				new DealWithWorker(server, in, out, s).start();
			}
		} catch (IOException e) {}
	}
	
	public Handler(Socket s, Server server) {
		this.s = s;
		this.server = server;
	}

}
