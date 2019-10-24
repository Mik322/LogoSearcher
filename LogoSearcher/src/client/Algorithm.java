package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Algorithm {

	public boolean procura(BufferedImage imagem, BufferedImage subimagem) {
		
		for(int i=0; i!= imagem.getWidth();i++) {
			for(int j=0; j!=imagem.getHeight();j++) {
				int a=i, b=j;
				boolean igual;
				while(igual=true) {
					for(int x=a;x!=subimagem.getWidth();x++) {
						for(int y=b;y!=subimagem.getHeight();y++) {
							if (imagem.getRGB(x,y)!=subimagem.getRGB(a,b)) {
								igual=false;
							}
							a++;
							b++;
						} 
					}
					
				}
				if(igual!=false)
					return true;
				
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Algorithm a = new Algorithm();
		 try {
			BufferedImage imagem = ImageIO.read(new File("C:\\Users\\amges\\Downloads\\img\\img\\out\\image1_1"));
			BufferedImage subimagem = ImageIO.read(new File("image1_1"));
		 } catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
  }