package dem;

import java.io.*;
import java.util.ArrayList;

public class DemController {
	
	private Dem dem = new Dem();
	
	public Dem getDem() {
		return dem;
	}
	
	public DemController(String pathName){
		// 파일 객체 생성
		dem.setFile(new File(pathName));
		setArr();
		toDemInfo();
	}
	
	private void setArr(){
		try{
			//입력 스트림 생성
			FileReader file_reader = new FileReader(dem.getFile());
			String temp = "";
			int cur = 0;
			int flag = 0;
			ArrayList<Integer> arrX = new ArrayList<Integer>();
			ArrayList<Integer> arrY = new ArrayList<Integer>();
			ArrayList<Double> arrZ = new ArrayList<Double>();
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
			dem.setArrX(arrX);
			dem.setArrY(arrY);
			dem.setArrZ(arrZ);
			file_reader.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch(IOException e){
			e.getStackTrace();
		}
	}
	
	/**
	x축 = 동, 서 방향을 결정 => 열
	y축 = 북, 남 방향을 결정 => 행

	따라서
	열이 증가함 -> 2차원 평면 상 지도가 오른쪽으로 감 (x 좌표 증가)
	행이 증가함 -> 2차원 평면 상 지도가 아래로 내려감 (y 좌표 감소)
	 **/
	private void toDemInfo(){
		ArrayList<Integer> arrX = dem.getArrX();
		ArrayList<Integer> arrY = dem.getArrY();
		ArrayList<Double> arrZ = dem.getArrZ();
		ArrayList<ArrayList<DemInfo>> demInfo = new ArrayList<ArrayList<DemInfo>>();
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
		dem.setDemInfo(demInfo);
	}
	
}
