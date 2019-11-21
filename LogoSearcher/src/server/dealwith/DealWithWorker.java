package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.Server;

public class DealWithWorker extends DealWith {
	
	private String type;
	
	public DealWithWorker(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server,in,out);
		try {
			serve();
		} catch (IOException e) {}
	}
	
	@Override
	void serve() throws IOException {
		try {
			type = (String) in.readObject();
			server.addType(type);
		} catch (ClassNotFoundException e) {}
		while (true) {
		}
	}

}
