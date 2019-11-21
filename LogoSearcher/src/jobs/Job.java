package jobs;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Job {
	
	private ArrayList<byte[]> imgs;
	private byte[] subimg;
	private ObjectOutputStream client;
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

	public ObjectOutputStream getClient() {
		return client;
	}

	public ArrayList<byte[]> getImgs() {
		return imgs;
	}

	public void setClient(ObjectOutputStream client) {
		this.client = client;
	}

}
