import config.ApplicationProperties;
import dem.DemController;
import dem.DemInfo;
import dem.DemService;
import org.xml.sax.SAXException;
import sun.SunService;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class main {

    // 이 멤버변수가 직접 쓰이지는 않지만 프로퍼티 값을 쓰기 위해 한번 호출은 시켜야 한다.
    private static final ApplicationProperties applicationProperties = new ApplicationProperties();

    private static DemService dem = new DemService();
    private static SunService sun = new SunService();

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        //ShpMain shpMain = new ShpMain();
        //shpMain.run();

        dem.run();

        ArrayList<ArrayList<DemInfo>> arr = dem.getDEM();
        System.out.println(arr.get(0).get(1).getX()); // 테스트 코드
    

        sun.run();
        sun.set();
        sun.get();
        sun.test1();
    }
}
