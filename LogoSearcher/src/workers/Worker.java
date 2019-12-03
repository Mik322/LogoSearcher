package workers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import streamedobjects.SearchTask;

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

	public void runWorker() {
		try {
			connectToServer(endereco, PORTO);
			out.writeInt(WORKER);
			out.flush();
			out.writeObject(typeOfWorker);
			out.flush();
			recieveAndExecute();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			while (true) {
				try {
					SearchTask sT= (SearchTask) in.readObject(); 
					ArrayList<Point[]> results = procura(sT.getImg(), sT.getSubimg());
					out.writeObject(results);
					out.flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			runWorker();
		}

	}

	public abstract ArrayList<Point[]> procura(byte[] img, byte[] subimg);

	private void connectToServer(String endereco, int PORTO) throws IOException {
		InetAddress server = InetAddress.getByName(endereco);
		socket = new Socket(server, PORTO);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public static BufferedImage convertToImage(byte[] img) {
		try {
			InputStream in = new ByteArrayInputStream(img);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			return bImageFromConvert;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		switch (args[2]) {
		case "normal":
			new NormalWorker(args[0], Integer.parseInt(args[1]), args[2]);
			break;

		case "90degrees":
			new NoventaGrausWorker(args[0], Integer.parseInt(args[1]), args[2]);
			break;

		case "180degrees":
			new CentoEoitentaGrausWorker(args[0], Integer.parseInt(args[1]), args[2]);
			break;

		default:
			System.out.println("Type of Worker not available, try other type:");
			main(args);
		}
	}

}
