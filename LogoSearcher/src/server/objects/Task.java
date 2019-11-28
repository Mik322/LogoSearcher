package server.objects;

import server.dealwith.DealWithClient;

public class Task{

	private byte[]img;
	private byte[]subimg;
	
	private DealWithClient dwc;
	private String type;
	
	public Task(byte[]img, byte[]subimg, DealWithClient dwc, String type){
		this.img = img;
		this.subimg = subimg;
		this.dwc = dwc;
		this.type = type;
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
	
}
