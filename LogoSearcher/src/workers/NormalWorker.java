package workers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class NormalWorker extends Worker {
	
	NormalWorker(String socket, int PORTO, String typeOfWorker){
		super(socket, PORTO, typeOfWorker);
	}

	private static boolean procuraSub(BufferedImage imagem, BufferedImage subimagem, int sX, int sY) {
		boolean result = true;
		for (int i = 0; i != subimagem.getWidth(); i++) {
			for (int j = 0; j != subimagem.getHeight(); j++) {
				result = (imagem.getRGB(i + sX, j + sY) == subimagem.getRGB(i, j));
				if (!result) {
					return result;
				}
			}
		}
		return result;
	}

	public ArrayList<Point[]> procura(byte[] img, byte[] subimg) {
		BufferedImage imagem = convertToImage(img);
		BufferedImage subimagem = convertToImage(subimg);
		ArrayList<Point[]> results = new ArrayList<>();
		for (int i = 0; i != imagem.getWidth() - subimagem.getWidth(); i++) {
			for (int j = 0; j != imagem.getHeight() - subimagem.getHeight(); j++) {
				if (imagem.getRGB(i, j) == subimagem.getRGB(0, 0) && procuraSub(imagem, subimagem, i, j)) {
					Point cordinates[] = new Point[2];
					cordinates[0].x = i;
					cordinates[0].y = j;
					cordinates[1].x = i + subimagem.getWidth();
					cordinates[1].y = j + subimagem.getHeight();		
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
			return bImageFromConvert;
		} catch (Exception e) {
		}
		return null;
	}
	
}
