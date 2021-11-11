package Algorithms;

import sun.SunInfo;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * convert (sunInfo + dem) -> hillshade
 * */
public class Hillshade {
    public ArrayList<ArrayList<Double>> hsConverter(ArrayList<ArrayList<SunInfo>> si, ArrayList<ArrayList<Double>> zValues){
        ArrayList<ArrayList<Double>> hs = new ArrayList<ArrayList<Double>>();
        for(int i=1; i<si.size() - 1; i++){
            for(int j=1; j<si.get(i).size() - 1; j++){


                /*
                3x3 window
                (a, b, c)
                (d, e, f) ---> e = which the aspect is being calculated
                (g, h, i)
                 */
                HashMap<Character, Double>windows = new HashMap<Character, Double>();
                int cnt = 0;
                for(int y=i-1; y<=i+1; y++){
                    for(int x=j-1; x<=j+1; x++){
                        windows.put((char) ('a' + cnt), zValues.get(y).get(x));
                        cnt++;
                    }
                }

                Double Zenith_deg = 90 - si.get(i).get(j).getAltitude_09();
                Double Zenith_rad = (Zenith_deg * Math.PI) / 180.0;

                Double Aznimuth_math = 360.0 - si.get(i).get(j).getAzimuth_09();

            }
        }
        return hs;
    }
}