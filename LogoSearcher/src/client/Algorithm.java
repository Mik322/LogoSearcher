package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Algorithm {

	public static boolean procuraSub(BufferedImage imagem, BufferedImage subimagem) {

		for (int i = 0; i != imagem.getWidth(); i++) {
			for (int j = 0; j != imagem.getHeight(); j++) {
				int a = i, b = j;
				boolean igual;
				while (igual = true) {
					for (int x = a; x != subimagem.getWidth(); x++) {
						for (int y = b; y != subimagem.getHeight(); y++) {
							if (imagem.getRGB(x, y) != subimagem.getRGB(a, b)) {
								igual = false;
							}
							a++;
							b++;
						}
					}

				}
				if (igual != false)
					return true;

			}
		}
		return false;
	}

	public static ArrayList<Integer[]> procura(byte[] img, byte[] subimg) {
		BufferedImage imagem = convertToImage(img);
		BufferedImage subimagem = convertToImage(subimg);
		return null;
	}

	private static BufferedImage convertToImage(byte[] img) {
		try {
			InputStream in = new ByteArrayInputStream(img);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "png", new File("imagens/out.png"));
			return bImageFromConvert;
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			BufferedImage imagem = ImageIO.read(new File("out/image1_1.png"));
			BufferedImage subimagem = ImageIO.read(new File("Superman.png"));
			procuraSub(imagem, subimagem);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
