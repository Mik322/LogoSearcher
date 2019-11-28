package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import server.Server;
import streamedobjects.Task;

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
			System.out.println("Novo trabalhador do tipo " + type);
			server.addSearchType(type);
		} catch (ClassNotFoundException e) {}
		while (true) {
			try {
				Task task = server.getQueue(type).poll();
				out.writeObject(task);
				out.flush();
				@SuppressWarnings("unchecked")
				ArrayList<Point[]> points = (ArrayList<Point[]>) in.readObject(); //Recebe os pontos
				task.getDwc().receiveResult(task.getImg(), points); //Envia o resultado para o DWC correspondente
			} catch (ClassNotFoundException e) {
			}
		}
	}

}

