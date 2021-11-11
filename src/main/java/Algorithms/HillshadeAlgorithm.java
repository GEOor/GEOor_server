package Algorithms;

import dem.DemInfo;
import sun.SunInfo;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * convert (sunInfo + dem) -> hillshade
 * */
public class HillshadeAlgorithm {
    public ArrayList<ArrayList<Double>> hsConverter(ArrayList<ArrayList<SunInfo>> si, ArrayList<ArrayList<DemInfo>> di){
        ArrayList<ArrayList<Double>> hs = new ArrayList<ArrayList<Double>>();
        //맨 위, 아래 행에 빈 arraylist 하나씩 추가해주기 (배열 크기 맞추기 위함)
        ArrayList<Double> tmp = new ArrayList<Double>(si.get(0).size());
        hs.add(tmp);
        for(int i=1; i<si.size() - 1; i++){
            ArrayList<Double> row = new ArrayList<Double>(si.get(i).size());
            row.add(0.0);
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
                        windows.put((char) ('a' + cnt), di.get(y).get(x).getZ());
                        cnt++;
                    }
                }

                // (2) Zenith_deg = 90 - Altitude
                Double Zenith_deg = 90 - si.get(i).get(j).getAltitude_09();

                // (3) Zenith_rad = Zenith * pi / 180.0
                Double Zenith_rad = (Zenith_deg * Math.PI) / 180.0;

                // (4) Azimuth_math = 360.0 - Azimuth + 90
                Double Azimuth_math = 360.0 - si.get(i).get(j).getAzimuth_09();

                // (5) if Azimuth_math >= 360.0, then: Azimuth_math = Azimuth_math - 360.0
                if(Azimuth_math >= 360.0) Azimuth_math = Azimuth_math - 360.0;

                // (6) Azimuth_rad = Azimuth_math * pi / 180.0
                Double Azimuth_rad = (Azimuth_math * Math.PI) / 180.0;

                // The rate of change in the x direction for cell e is calculated with the following algorithm
                // (7) [dz/dx] = ((c + 2f + i) - (a + 2d + g)) / (8 * cellsize)
                Double derivative_x = ((windows.get('c') + 2 * windows.get('f') + windows.get('i')) -
                (windows.get('a') + 2 * windows.get('d') + windows.get('g'))) / (8 * 5);

                // The rate of change in the y direction for cell 'e' is calculated with the following algorithm
                // (8) [dz/dy] = ((g + 2h + i) - (a + 2b + c)) / (8 * cellsize)
                Double derivative_y = ((windows.get('g') + 2 * windows.get('h') + windows.get('i')) -
                        (windows.get('a') + 2 * windows.get('b') + windows.get('c'))) / (8 * 5);

                // The steepest downhill descent from each cell in the surface is the slope.
                // The algorithm for calculating the slope in radians, incorporating the z-factor, is
                // (9) Slope_rad = ATAN (z_factor * √ ([dz/dx]2 + [dz/dy]2))
                Double Slope_rad = Math.atan(Math.sqrt(derivative_x * derivative_x + derivative_y * derivative_y));

                Double Aspect_rad = 0.0;

                // (10.1) if derivative_x is non-zero
                if(derivative_x != 0.0) {
                    Aspect_rad = Math.atan2(derivative_y, -derivative_x);
                    // (10.2) if Aspect_rad < 0 then Aspect_rad = 2 * pi + Aspect_rad
                    if(Aspect_rad < 0) Aspect_rad = 2 * Math.PI + Aspect_rad;
                }

                // (10.3) if derivative_x is zero
                if(derivative_x == 0.0){
                    if(derivative_y > 0) Aspect_rad = Math.PI / 2;
                    else if(derivative_y < 0) Aspect_rad = 2 * Math.PI - (Math.PI / 2);
                    else Aspect_rad = Azimuth_rad;
                }

                Double hs_cell = 255.0 * ((Math.cos(Zenith_rad) * Math.cos(Slope_rad)) + (Math.sin(Zenith_rad) * Math.sin(Slope_rad)
                        * Math.cos(Azimuth_rad - Aspect_rad)));

                row.add(hs_cell);
            }
            row.add(0.0);
            hs.add(row);
        }
        //맨 위, 아래 행에 빈 arraylist 하나씩 추가해주기 (배열 크기 맞추기 위함)
        ArrayList<Double> tmp2 = new ArrayList<Double>(si.get(0).size());
        hs.add(tmp);
        return hs;
    }
}