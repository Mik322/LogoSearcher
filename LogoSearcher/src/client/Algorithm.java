package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Algorithm {

	private static boolean procuraSub(BufferedImage imagem, BufferedImage subimagem, int startingX, int startingY) {
		boolean result = true;
		
		while(result) {
			for (int i=0;i!=subimagem.getHeight();i++) {
				for (int j=0;j!=subimagem.getWidth();j++) {
					result = (imagem.getRGB(i+startingX, j+startingY) == subimagem.getRGB(i, j));
				}
			}
		}
		
		return result;
	}

	public static ArrayList<Integer[]> procura(byte[] img, byte[] subimg) {
		BufferedImage imagem = convertToImage(img);
		BufferedImage subimagem = convertToImage(subimg);
		ArrayList<Integer[]> results = new ArrayList<>();
		
		Integer cordinates[] = new Integer[2];
		for (int i=0; i!=imagem.getHeight()-subimagem.getHeight(); i++) {
			for (int j=0; j!=imagem.getWidth()-subimagem.getWidth(); j++) {
				if (imagem.getRGB(i, j)==subimagem.getRGB(0, 0) && procuraSub(imagem,subimagem,i,j)) {
					cordinates[0] = i;
					cordinates[1] = j;
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
			ImageIO.write(bImageFromConvert, "png", new File("imagens/out.png"));
			return bImageFromConvert;
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		/*try {
			BufferedImage imagem = ImageIO.read(new File("out/image1_1.png"));
			BufferedImage subimagem = ImageIO.read(new File("Superman.png"));
			procuraSub(imagem, subimagem);
		} catch (IOException e) {

			e.printStackTrace();
		}*/

	}
}
