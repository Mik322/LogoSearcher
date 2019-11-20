package server.jobs;

import java.io.ObjectOutputStream;

public class Job {
	
	private byte[] img;
	private byte[] subimg;
	private ObjectOutputStream client;

	public Job(byte[] img, byte[] subimg, ObjectOutputStream client) {
		this.img = img;
		this.subimg = subimg;
		this.client = client;
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
