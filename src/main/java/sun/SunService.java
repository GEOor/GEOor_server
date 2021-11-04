package sun;

import dem.DemController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class SunService {

    SunController sc;
    public void run(){
        sc = new SunController();
    }

    /*
     * Request Parameter (순서대로)
     * 날짜, 위도, 경도, 10진수 여부
     * 자세한 설명은 getSunInfoXml 내부 참조
     * */
    public void set() throws IOException, ParserConfigurationException, SAXException {
        sc.setSunInfo("20150101", "12759", "3754", "N");
    }

    public ArrayList<ArrayList<SunInfo>> get(){
        return sc.getSunInfo();
    }

    //테스트 코드
    public void test1(){
        ArrayList<ArrayList<SunInfo>> si = sc.getSunInfo();
        System.out.println(si.toString());
    }
}
