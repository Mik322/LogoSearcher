package server.dealwith;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import server.Server;
import streamedobjects.Result;
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
			server.addType(type);
		} catch (ClassNotFoundException e) {}
		while (true) {
			try {
				Task task = server.getQueue(type).pull();
				out.writeObject(task);
				out.flush();
				@SuppressWarnings("unchecked")
				ArrayList<Point[]> points = (ArrayList<Point[]>) in.readObject(); //Recebe os pontos
				Result result = new Result(task.getImg(), points); //Cria um resultado que contem os pontos e a imagem a que os resultados pertencem
				task.getDwc().receiveResult(result); //Envia o resultado para o DWC correspondente
			} catch (InterruptedException e) {
			} catch (ClassNotFoundException e) {
			}
		}
	}

}

