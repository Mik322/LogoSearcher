package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jobs.Job;
import server.Server;

public class DealWithWorker extends DealWith {
	
	public DealWithWorker(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server,in,out);
		try {
			serve();
		} catch (IOException e) {}
	}
	
	@Override
	void serve() throws IOException {
		while (true) {
		}
	}

}
