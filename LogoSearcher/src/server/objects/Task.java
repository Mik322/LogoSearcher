package server.objects;

import server.dealwith.DealWithClient;

public class Task{

	private byte[]img;
	private byte[]subimg;
	private int index;
	
	private DealWithClient dwc;
	private String type;
	
	public Task(byte[]img, byte[]subimg, DealWithClient dwc, String type, int index){
		this.img = img;
		this.subimg = subimg;
		this.dwc = dwc;
		this.type = type;
		this.index = index;
	}

	public byte[] getImg() {
		return img;
	}

	public byte[] getSubimg() {
		return subimg;
	}

	public DealWithClient getDwc() {
		return dwc;
	}

	public String getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}
	
}
