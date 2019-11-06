package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Algorithm {

	private static boolean procuraSub(BufferedImage imagem, BufferedImage subimagem, int sX, int sY) {
		boolean result = true;
		//System.out.println("procuraSub foi chamado");
		for (int i = 0; i != subimagem.getWidth(); i++) {
			for (int j = 0; j != subimagem.getHeight(); j++) {
				result = (imagem.getRGB(i + sX, j + sY) == subimagem.getRGB(i, j));
				if (!result) {
					return result;
				}
			}
		}
		System.out.println(result);
		return result;
	}

	public static ArrayList<int[]> procura(byte[] img, byte[] subimg) {
		System.out.println("Cheguei ao procura");
		BufferedImage imagem = convertToImage(img);
		BufferedImage subimagem = convertToImage(subimg);
		ArrayList<int[]> results = new ArrayList<>();
		System.out.println(results);
		int cordinates[] = new int[2];
		for (int i = 0; i != imagem.getWidth() - subimagem.getWidth(); i++) {
			for (int j = 0; j != imagem.getHeight() - subimagem.getHeight(); j++) {
				// System.out.println(imagem.getRGB(i, j)+" " +i+" "+j+" "+ subimagem.getRGB(0,
				// 0));
				if (imagem.getRGB(i, j) == subimagem.getRGB(0, 0) && procuraSub(imagem, subimagem, i, j)) {
					cordinates[0] = i;
					cordinates[1] = j;
					System.out.println(cordinates.toString());
					results.add(cordinates);
				}
			}
		}

		return results;
	}

	private static BufferedImage convertToImage(byte[] img) {
		try {
			InputStream in = new ByteArrayInputStream(img);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			// ImageIO.write(bImageFromConvert, "png", new File("imagens/out.png"));
			return bImageFromConvert;
		} catch (Exception e) {
		}
		return null;
	}
}
