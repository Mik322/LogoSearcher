package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static final int PORTO = 8080;
	public final ExecutorService pool = Executors.newFixedThreadPool(2);
	
	public void startServing() throws IOException {
		ServerSocket s = new ServerSocket(PORTO);
		System.out.println("Lançou ServerSocket: " + s);
		try {
			while (true) {
				pool.execute(new Handler(s.accept(),this));
				//new DealWithClient(socket, this).start();
			}

		} finally {
			s.close();
		}
	}

	public static void main(String[] args) {

		try {
			Server server = new Server();
			server.startServing();
		} catch (IOException e) {

		}

	}

}
