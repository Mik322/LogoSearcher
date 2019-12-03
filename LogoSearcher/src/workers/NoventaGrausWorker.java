package workers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

	public class NoventaGrausWorker extends Worker{
		NoventaGrausWorker(String socket, int PORTO, String typeOfWorker){
		super(socket, PORTO, typeOfWorker);
		runWorker();
	}

	
	private static boolean procuraSub(BufferedImage imagem, BufferedImage subimagem, int sX, int sY) {
		boolean result = true;
		for (int i = 0; i != subimagem.getHeight(); i++) {
			for (int j = 0; j != subimagem.getWidth(); j++) {
				result = (imagem.getRGB(sX+j, sY-i) == subimagem.getRGB(i, j));
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
		for (int i = 0; i != imagem.getWidth() - subimagem.getHeight(); i++) {
			for (int j = subimagem.getWidth(); j != imagem.getHeight(); j++) {
				if (imagem.getRGB(i, j) == subimagem.getRGB(0, 0) && procuraSub(imagem, subimagem, i, j)) {
					Point cordinates[] = new Point[2];
					cordinates[0] = new Point(i, j-subimagem.getWidth());
					cordinates[1] = new Point(i+subimagem.getHeight(), j);
					results.add(cordinates);
				}
			}
		}
		return results;
	}

}
