package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DealWithClient extends DealWith {

	public DealWithClient(Server server, ObjectInputStream in, ObjectOutputStream out) {
		super(server, in, out);
		try {
			serve();
		} catch (IOException e) {}
	}

	@Override
	void serve() throws IOException {
		
	}

}
