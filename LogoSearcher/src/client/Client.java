package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import workers.Algorithm;

public class Client extends Observable {
	private static String OUTPUT_NAME = "OUT";
	private File file;
	
	public void sendImages(File[] imagensDir, BufferedImage subimagem) {
		byte[] subimg = convertToByteArray(subimagem);
		int n = 1;
		
		for (File f:imagensDir) {
			try {
				BufferedImage imagem = ImageIO.read(f);
				byte[] img = convertToByteArray(imagem);
				ArrayList<int[]> results = Algorithm.procura(img, subimg);
				if (results.size()!=0) {
					drawImage(results, imagem, subimagem, n);
					n++;
				}
			} catch (Exception e) {}
		}
		file = new File(OUTPUT_NAME);
		setChanged();
		notifyObservers(file);
	}

	private void drawImage(ArrayList<int[]> results, BufferedImage imagem, BufferedImage subimagem, int n) {
		for (int[] i: results) {
			Graphics2D g2d = imagem.createGraphics();
			g2d.setColor(Color.RED);
			g2d.drawRect(i[0], i[1], subimagem.getWidth(), subimagem.getHeight());
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

	public static void main(String[] args) {
		Client c = new Client();
		GUI window = new GUI(c);
		c.addObserver(window);
		window.open();
	}

}