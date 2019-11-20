package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

public class Client extends Observable {
	private static String OUTPUT_NAME = "OUT";
	private File file;
	
	static final private int PORTO = 8080;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	
	private static final int CLIENT = 1;
	
	private void runClient() {
		try {
			doConections();
			GUI window = new GUI(this);
			this.addObserver(window);
			window.open();
			//window.writeSearchTypes(); // Vai escrever os tipos de pesquisa disponoveis
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doConections() throws IOException {
		InetAddress endereco = InetAddress.getByName(null);
		socket = new Socket(endereco, PORTO);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void sendImages(File[] imagensDir, BufferedImage subimagem) {
		byte[] subimg = convertToByteArray(subimagem);
		int n = 1;
		
		for (File f:imagensDir) {
			try {
				BufferedImage imagem = ImageIO.read(f);
				byte[] img = convertToByteArray(imagem);
				out.write(img);
				out.write(subimg);
				@SuppressWarnings("unchecked")
				ArrayList<Point[]> results = (ArrayList<Point[]>) in.readObject();
				if (results.size()!=0) {
					drawImage(results, imagem, n);
					n++;
				}
			} catch (Exception e) {}
		}
		file = new File(OUTPUT_NAME);
		setChanged();
		notifyObservers(file);
	}

	private void drawImage(ArrayList<Point[]> results, BufferedImage imagem, int n) {
		for (Point[] p: results) {
			Graphics2D g2d = imagem.createGraphics();
			g2d.setColor(Color.RED);
			g2d.drawRect((int)p[0].getX(), (int)p[0].getY(), (int)(p[0].getX()-p[1].getX()), (int)(p[0].getY()-p[1].getY()));
			g2d.dispose();
		}
		String fileName = OUTPUT_NAME+"\\out" + n + ".png";
		try {
			ImageIO.write(imagem, "png", new File(fileName));
		} catch (IOException e) {
		}
	}

	private byte[] convertToByteArray(BufferedImage img) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			byte[] bImg = baos.toByteArray();
			baos.close();
			return bImg;
		} catch (IOException e) {
		}
		return null;
	}
	
	public Client() {
		runClient();
	}

	public static void main(String[] args) {
		Client c = new Client();
	}

}
