package streamedobjects;

public class Task {

	private byte[]img;
	private byte[]subimg;
	
	Task(byte[]img, byte[]subimg){
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
