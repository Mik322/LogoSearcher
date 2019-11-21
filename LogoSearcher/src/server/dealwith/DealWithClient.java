package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import server.Server;
import streamedobjects.Job;

public class DealWithClient extends DealWith {
	
	private Job job;

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
		try {
			serve();
		} catch (IOException e) {}
	}

	@Override
	void serve() throws IOException {
		try {
			job = (Job) super.in.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<String> types = (ArrayList<String>) super.in.readObject();
			//super.sendToServer(new Job(img,subimg,super.out,types));
		} catch (ClassNotFoundException e) {}
	}

}
