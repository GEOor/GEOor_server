import Algorithms.HillshadeAlgorithm;
import Algorithms.TransformCoordinate;
import config.ApplicationProperties;
import crawling.Crawler;
import dem.DemInfo;
import dem.DemService;
import shp.ShpMain;
import sun.SunService;
import java.util.ArrayList;

public class main {

    // 이 멤버변수가 직접 쓰이지는 않지만 프로퍼티 값을 쓰기 위해 한번 호출은 시켜야 한다.
    private static final ApplicationProperties applicationProperties = new ApplicationProperties();

    private static DemService dem = new DemService();
    private static SunService sun = new SunService();
    private static TransformCoordinate tf;

    public static void main(String[] args) throws Exception {
//        ShpMain shpMain = new ShpMain();
//        shpMain.run();

        /*dem.run();

        ArrayList<ArrayList<DemInfo>> arr = dem.getDEM();
        System.out.println(arr.get(0).get(1).getX()); // 테스트 코드
        sun.run();
        sun.set();
        sun.get();
        sun.test1();
        dem.run();
        Crawler crawler = new Crawler();
        crawler.run();

        HillshadeAlgorithm hs = new HillshadeAlgorithm();
        ArrayList<ArrayList<Double>> hsArr = hs.hsConverter(sun.get(), dem.getDEM());
        System.out.println(hsArr.toString()); // 테스트 코드*/
    
        tf = new TransformCoordinate(574118, 354211);
        System.out.println(tf.getLatitude());
        System.out.println(tf.getLongitude());
        System.out.println(tf.toString());
    }
}