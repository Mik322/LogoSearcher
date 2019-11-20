package server.jobs;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Job {
	
	private byte[] img;
	private byte[] subimg;
	private ObjectOutputStream client;
	private ArrayList<String> types;

	public Job(byte[] img, byte[] subimg, ObjectOutputStream client, ArrayList<String> types) {
		this.img = img;
		this.subimg = subimg;
		this.client = client;
		this.types = types;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public byte[] getImg() {
		return img;
	}

	public byte[] getSubimg() {
		return subimg;
	}

	public ObjectOutputStream getClient() {
		return client;
	}

}
