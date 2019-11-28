package streamedobjects;

import java.io.Serializable;

public class SearchTask implements Serializable {

	private static final long serialVersionUID = 1L;
	private byte[] img, subimg;
	
	public SearchTask(byte[] img, byte[] subimg) {
		this.img = img;
		this.subimg = subimg;
	}

	public byte[] getImg() {
		return img;
	}

	public byte[] getSubimg() {
		return subimg;
	}
}
