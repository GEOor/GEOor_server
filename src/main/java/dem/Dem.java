package dem;

import java.io.*;
import java.util.ArrayList;

public class Dem {
	
	private File file;
	private ArrayList<Integer> arrX = new ArrayList<Integer>();
	private ArrayList<Integer> arrY = new ArrayList<Integer>();
	private ArrayList<Double> arrZ = new ArrayList<Double>();
	private ArrayList<ArrayList<DemInfo>> demInfo = new ArrayList<ArrayList<DemInfo>>();
	
	public Dem(String pathName){
		// 파일 객체 생성
		// 나중에 refactoring 시 DEM 파일만 모아놓는 폴더 + fileName으로 pathName만들기
		this.file = new File(pathName);
		setArr();
		toDemInfo();
	}
	
	private void setArr(){
		try{
			//입력 스트림 생성
			FileReader file_reader = new FileReader(file);
			String temp = "";
			int cur = 0;
			int flag = 0;
			while((cur = file_reader.read()) != -1){
				char c = (char)cur;
				if(('0' <= c && c <= '9') || c == '.') {
					temp += c;
				}
				else{
					if(temp.equals("")) {
						continue;
					}
					if(flag == 0) {
						arrX.add(Integer.parseInt(temp));
					}
					else if(flag == 1){
						arrY.add(Integer.parseInt(temp));
					}
					else {
						arrZ.add(Double.parseDouble(temp));
					}
					flag++;
					flag %= 3;
					temp = "";
				}
			}
			file_reader.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch(IOException e){
			e.getStackTrace();
		}
	}
	
	private void toDemInfo(){
		// DEM 파일 = 253 * 314 형태임
		for(int i=0; i<253; i++){
			ArrayList<DemInfo> demInfos = new ArrayList<>();
			for(int j=0; j<314; j++){
				int x = arrX.get(314*i + j);
				int y = arrY.get(314*i + j);
				double z = arrZ.get(314*i + j);
				demInfos.add(new DemInfo(x, y, z));
			}
			demInfo.add(demInfos);
		}
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
}
