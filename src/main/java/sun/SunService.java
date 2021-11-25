package sun;

import dem.DemController;
import dem.DemInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class SunService {

    private SunController sc = new SunController();

    public ArrayList<ArrayList<SunInfo>> get(){
        return sc.getSunInfo();
    }

    public void run(ArrayList<ArrayList<DemInfo>> dem, int num, int time){
        sc.cutSquare(dem, num, time);
    }
}
