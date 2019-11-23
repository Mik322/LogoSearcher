package streamedobjects;

import java.awt.Point;
import java.util.ArrayList;

public class Result {
	
	private byte[] img;
	private ArrayList<Point[]> points;
	
	public Result(byte[] img, ArrayList<Point[]> points) {
		this.img = img;
		this.points = points;
	}

	public byte[] getImg() {
		return img;
	}

	public ArrayList<Point[]> getPoints() {
		return points;
	}
}
