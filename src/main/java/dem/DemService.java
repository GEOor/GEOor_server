package dem;

import java.util.ArrayList;

public class DemService {
	
	private String demFilePath = "src/main/resources/test.xyz";
	private DemController dem;
	
	public void run(){
		dem = new DemController(demFilePath);
	}
	
	public ArrayList<ArrayList<DemInfo>> getDEM(){
		return dem.getDem().getDemInfo();
	}
	
}
