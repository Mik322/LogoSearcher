package client;

import java.util.Observable;

public class Client extends Observable{
	
	void sendImages(Object o) {
		
	}
	
	void serverAnswers() {
		//Recebe os resultados
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		GUI window = new GUI(c);
		c.addObserver(window);
		window.open();
	}
	
}
