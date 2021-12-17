import Algorithms.Hillshade;
import Algorithms.HillshadeAlgorithm;
import Algorithms.TransformCoordinate;
import config.ApplicationProperties;
import dem.DemInfo;
import dem.DemService;
import sun.SunService;
import java.util.ArrayList;

public class main {

    // 멤버변수가 직접 쓰이지는 않지만 프로퍼티 값을 쓰기 위해 한번 호출은 시켜야 한다.
    private static final ApplicationProperties applicationProperties = new ApplicationProperties();

    private static DemService dem = new DemService();
    private static SunService sun = new SunService();
    private static TransformCoordinate tf;

    public static void main(String[] args){

        /**
            dem api 호출 부분
            반환 값 : 253 x 314 모양의 2차원 ArrayList
         */
        dem.run();
        ArrayList<ArrayList<DemInfo>> demArr = dem.getDEM();

        /**
            2021-11-22
            작성자 : 천수환
            내용 : dem 좌표를 이용해 태양고도각 추출
            사용 방법 : 이미 구한 demArr, 몇등분 할 지 정하는 변수 k, 시각 t 를 넣고 SunService의 run 함수 사용
            작동 원리
            1) 2차원 dem ArrayList를 k * k등분, k^2개의 사각형으로 나눈다.
            2) 각각의 사각형의 중심 dem 값을 TransformCoordinate를 이용해 위,경도로 변환
            3) 변환된 위,경도를 크롤러를 이용해 고도각으로 변환
            4) 변환된 고도각을 똑같이 2차원 SunInfo ArrayList의 사각형에 위치한 곳으로 모두 채운다.
         */
        int k = 2; //k등분으로 태양고도각 크롤링 (크롤링 횟수 조절을 위함)
        int t = 9; //태양고도각 시각 (0 ~ 23 사이 정수만 가능함)
        sun.run(demArr, k, t);

        /**
            2021-11-22
            작성자 : 천수환
            내용 : HillshadeAlgorithm 사용 부분
            사용 방법
            1) HillshadeAlgorithm 클래스 선언 (hs)
            2) hsConverter 함수에 태양 고도각, dem값을 넣는다. (둘다 2차원 ArrayList)
            3) 리턴된 값은 해당 dem 격자의 음영기복도(hillshade)값이 된다. * 테두리 제외
         */
        HillshadeAlgorithm hs = new HillshadeAlgorithm();
        ArrayList<ArrayList<Hillshade>> hsArr = hs.hsConverter(sun.get(), dem.getDEM());
        System.out.println(hsArr.toString()); // 테스트 코드
    }
}