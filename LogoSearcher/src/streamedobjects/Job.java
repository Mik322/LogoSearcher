package streamedobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Job implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<byte[]> imgs;
	private byte[] subimg;
	private List<String> types;

	public Job(ArrayList<byte[]> imgs, byte[] subimg, List<String> types) {
		this.imgs = imgs;
		this.subimg = subimg;
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}

	public byte[] getSubimg() {
		return subimg;
	}

	public ArrayList<byte[]> getImgs() {
		return imgs;
	}
}
