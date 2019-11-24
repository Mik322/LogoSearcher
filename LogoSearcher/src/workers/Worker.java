package workers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import streamedobjects.Task;


public abstract class Worker extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String endereco;
	private int PORTO;
	private Object typeOfWorker;
	
	private static final int WORKER = 2;
	
	
	public Worker(String endereco, int PORTO, String typeOfWorker) {
		this.endereco = endereco;
		this.PORTO = PORTO;
		this.typeOfWorker = typeOfWorker;
	}
	
	public void runClient() {
		try {
			connectToServer(endereco, PORTO);
	        out.writeInt(WORKER);
	        out.flush();
	        out.writeObject(typeOfWorker);
	        out.flush();
	        recieveAndExecute();
		} catch (Exception e) {

		} finally {
			System.out.println("a fechar...");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void recieveAndExecute() {
		while(true) {
			try {
				Task task = (Task) in.readObject();
				ArrayList<Point[]> results = procura(task.getImg(), task.getSubimg());
				out.writeObject(results);
				out.flush();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public abstract ArrayList<Point[]> procura(byte[] img, byte[] subimg);
	
	private void connectToServer(String endereco, int PORTO) throws IOException {
		socket = new Socket(endereco, PORTO);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());	
	}

	
	public static BufferedImage convertToImage(byte[] img) {
		try {
			InputStream in = new ByteArrayInputStream(img);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			return bImageFromConvert;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
		if (args[2].equals("0")) {
		new NormalWorker(args[0], Integer.parseInt(args[1]), args[2]);
		}
	}
	
}
