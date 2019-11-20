package server.dealwith;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import server.Server;
import server.jobs.Job;

public class DealWithClient extends DealWith {

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
		try {
			serve();
		} catch (IOException e) {}
	}

	@Override
	void serve() throws IOException {
		try {
			byte[] img = (byte[]) super.in.readObject();
			byte[] subimg = (byte[]) super.in.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<String> types = (ArrayList<String>) super.in.readObject();
			super.sendToServer(new Job(img,subimg,super.out,types));
		} catch (ClassNotFoundException e) {}
	}

}
