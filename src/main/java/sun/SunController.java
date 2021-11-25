package sun;

import java.util.*;
import Algorithms.TransformCoordinate;
import crawling.Crawler;
import dem.DemInfo;

public class SunController {

    private ArrayList<ArrayList<SunInfo>> sunInfoArr = new ArrayList<ArrayList<SunInfo>>();

    private Crawler crawler = new Crawler();

    public ArrayList<ArrayList<SunInfo>> getSunInfo() {return sunInfoArr;}


    /**
     2021-11-25
     작성자 : 천수환
     내용 : 2차원 dem ArrayList를 k * k등분, k^2개의 사각형으로 나누기
     작동 원리
     1) 2차원 SunInfo ArrayList를 초기화
     2) 등분하는 위치 선정
     3) 변환된 위,경도를 크롤러를 이용해 고도각으로 변환
     4) 변환된 고도각을 똑같이 2차원 SunInfo ArrayList의 사각형에 위치한 곳으로 모두 채운다.
     */

    public void cutSquare(ArrayList<ArrayList<DemInfo>> dem, int num, int time){

        for(int i=0; i<253; i++){
            ArrayList<SunInfo> resize = new ArrayList<SunInfo>();
            for(int j=0; j<314; j++){
                SunInfo resize_si = new SunInfo(0, 0, 0D, 0D, 0,0D,0D,0D,0D);
                resize.add(resize_si);
            }
            sunInfoArr.add(resize);
        }

        TransformCoordinate tf;

        int unit_x = (253 / num), unit_y = (314 / num); //num등분 단위
        int pre_x = 0, pre_y = 0; //이전 index

        for(int i=0; i<num; i++){
            for(int j=0; j<num; j++){

                int index_x = (i * unit_x + (i + 1) * unit_x) / 2;
                int index_y = (j * unit_y + (j + 1) * unit_y) / 2;
                int dem_x = dem.get(index_x).get(index_y).getX();
                int dem_y = dem.get(index_x).get(index_y).getY();

                tf = new TransformCoordinate(dem_x, dem_y);
                crawler.run(tf.getLatitude(), tf.getLongitude(), dem_x, dem_y);
                ArrayList<SunInfo> si = crawler.get();

                index_x = 253 / (i + 1); index_y = 314 / (j + 1);
                if(i == num - 1) index_x = 253;
                if(j == num - 1) index_y = 314;

                for(int x=pre_x; x<index_x; x++){
                    for(int y=pre_y; y<index_y;y++){
                        tf = new TransformCoordinate(dem.get(x).get(y).getX(), dem.get(x).get(y).getY());
                        SunInfo tmp = new SunInfo(dem.get(x).get(y).getX(), dem.get(x).get(y).getY(),
                                tf.getLatitude(), tf.getLongitude(), time, si.get(time).getAzimuth(),
                                si.get(time).getAltitude(), si.get(time).getAscension(), si.get(time).getDeclination());

                        sunInfoArr.get(x).set(y, tmp);
                    }
                }

                pre_x = index_x; pre_y = index_y;
            }
        }

    }
}
