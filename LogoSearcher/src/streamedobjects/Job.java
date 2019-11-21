package streamedobjects;

import java.util.ArrayList;

public class Job {
	
	private ArrayList<byte[]> imgs;
	private byte[] subimg;
	private ArrayList<String> types;

	public Job(ArrayList<byte[]> imgs, byte[] subimg, ArrayList<String> types) {
		this.imgs = imgs;
		this.subimg = subimg;
		this.types = types;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public byte[] getSubimg() {
		return subimg;
	}

	public ArrayList<byte[]> getImgs() {
		return imgs;
	}
}
