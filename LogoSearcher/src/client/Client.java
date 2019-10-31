package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

public class Client extends Observable {
	
	public void sendImages(BufferedImage imagem, BufferedImage subimagem) {
		byte[] img = convertToByteArray(imagem);
		byte[] subimg = convertToByteArray(subimagem);
		ArrayList<Integer> results = Algorithm.procura(img, subimg);
		notifyObservers(results);
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

	public static void main(String[] args) {
		Client c = new Client();
		GUI window = new GUI(c);
		c.addObserver(window);
		window.open();
	}

}
