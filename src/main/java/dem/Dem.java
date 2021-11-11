package dem;

import java.io.File;
import java.util.ArrayList;

public class Dem {
	
	private File file;
	private ArrayList<Integer> arrX = new ArrayList<Integer>();
	private ArrayList<Integer> arrY = new ArrayList<Integer>();
	private ArrayList<Double> arrZ = new ArrayList<Double>();
	private ArrayList<ArrayList<DemInfo>> demInfo = new ArrayList<ArrayList<DemInfo>>();
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public ArrayList<Integer> getArrX() {
		return arrX;
	}
	
	public void setArrX(ArrayList<Integer> arrX) {
		this.arrX = arrX;
	}
	
	public ArrayList<Integer> getArrY() {
		return arrY;
	}
	
	public void setArrY(ArrayList<Integer> arrY) {
		this.arrY = arrY;
	}
	
	public ArrayList<Double> getArrZ() {
		return arrZ;
	}
	
	public void setArrZ(ArrayList<Double> arrZ) {
		this.arrZ = arrZ;
	}
	
	public ArrayList<ArrayList<DemInfo>> getDemInfo() {
		return demInfo;
	}
	
	public void setDemInfo(ArrayList<ArrayList<DemInfo>> demInfo) {
		this.demInfo = demInfo;
	}
}
