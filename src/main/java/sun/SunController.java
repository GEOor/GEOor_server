package sun;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;

import dem.DemInfo;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class SunController {

    ArrayList<ArrayList<SunInfo>> si = new ArrayList<ArrayList<SunInfo>>();

    //api 호출 함수 (String 형태의 xml 반환)
    public void setSunInfo(String locdate, String latitude, String longitude, String dnYn) throws IOException, ParserConfigurationException, SAXException {

        final String serviceKey = "mfnlHHjaj4zfqSUXTbi%2FoYlQEkYX%2Bk3COHTUxCBNqBKPeIQw7GbQNNUp6H5bLRkAOgZrhXQXeVHLf1PPTQfT3Q%3D%3D";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SrAltudeInfoService/getLCSrAltudeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("locdate","UTF-8") + "=" + URLEncoder.encode(locdate, "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8")); /*위도*/
        urlBuilder.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")); /*경도*/
        urlBuilder.append("&" + URLEncoder.encode("dnYn","UTF-8") + "=" + URLEncoder.encode(dnYn, "UTF-8")); /*실수형태(129.1257996, 35.3694613 등)이면 y, 도와 분 형태(127도 05분) 형태이면 n을 넘겨 주시기 바랍니다. 바꾸어 넣게 되면 동서남북의 끝 지역이 조회됨*/
        urlBuilder.append("&" + URLEncoder.encode("totalCount","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /**/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //connection 옵션 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        //conn.getResponseCode() 함수를 통해 요청 전송 (반환값은 상태 코드)
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String xml;
        String line;
        StringBuilder resultLine =new StringBuilder();
        while ((line = rd.readLine()) != null) {
            resultLine.append(line);
        }
        rd.close();
        conn.disconnect();

        xml = resultLine.toString();

        for(int i=0; i<253; i++){
            ArrayList<SunInfo> sunInfos = new ArrayList<>();
            for(int j=0; j<314; j++){
                sunInfos.add(makeSunInfo(xml));
            }
            si.add(sunInfos);
        }
    }

    public ArrayList<ArrayList<SunInfo>> getSunInfo() {return si;}

    private SunInfo makeSunInfo(String xml) throws ParserConfigurationException, IOException, SAXException {
        // xml을 파싱해주는 객체를 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        // xml 문자열은 InputStream으로 변환
        InputStream is =new ByteArrayInputStream(xml.getBytes());
        // 파싱 시작
        Document doc = documentBuilder.parse(is);
        // 최상위 노드 찾기
        Element element = doc.getDocumentElement();


        String locdate = element.getElementsByTagName("locdate").item(0).getFirstChild().getNodeValue();
        String location = element.getElementsByTagName("location").item(0).getFirstChild().getNodeValue();
        Double longitude = Double.valueOf(element.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
        Double longitudeNum = Double.valueOf(element.getElementsByTagName("longitudeNum").item(0).getFirstChild().getNodeValue());
        Double latitude = Double.valueOf(element.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
        Double latitudeNum = Double.valueOf(element.getElementsByTagName("latitudeNum").item(0).getFirstChild().getNodeValue());
        Double azimuth_09 = Double.valueOf(degreeToStr(element.getElementsByTagName("azimuth_09").item(0).getFirstChild().getNodeValue()));
        Double altitude_09 = Double.valueOf(degreeToStr(element.getElementsByTagName("altitude_09").item(0).getFirstChild().getNodeValue()));
        Double azimuth_12 = Double.valueOf(degreeToStr(element.getElementsByTagName("azimuth_12").item(0).getFirstChild().getNodeValue()));
        Double altitude_12 = Double.valueOf(degreeToStr(element.getElementsByTagName("altitude_12").item(0).getFirstChild().getNodeValue()));
        Double azimuth_15 = Double.valueOf(degreeToStr(element.getElementsByTagName("azimuth_15").item(0).getFirstChild().getNodeValue()));
        Double altitude_15 = Double.valueOf(degreeToStr(element.getElementsByTagName("altitude_15").item(0).getFirstChild().getNodeValue()));
        Double azimuth_18 = Double.valueOf(degreeToStr(element.getElementsByTagName("azimuth_18").item(0).getFirstChild().getNodeValue()));
        Double altitude_18 = Double.valueOf(degreeToStr(element.getElementsByTagName("altitude_18").item(0).getFirstChild().getNodeValue()));
        Double altitudeMeridian = Double.valueOf(degreeToStr(element.getElementsByTagName("altitudeMeridian").item(0).getFirstChild().getNodeValue()));

        SunInfo ret = new SunInfo(locdate, location, longitude, longitudeNum,
                latitude, latitudeNum, azimuth_09, altitude_09, azimuth_12,
                altitude_12, azimuth_15, altitude_15, azimuth_18, altitude_18, altitudeMeridian);

        return ret;
    }

    private String degreeToStr(String deg){
        String ret = "";
        for(int i=0; i<deg.length(); i++){
            if(i == 0 && deg.charAt(0) == '-') ret += '-';
            else if('0' <= deg.charAt(i) && deg.charAt(i) <= '9') ret += deg.charAt(i);
            else if(deg.charAt(i) == '˚') ret += '.';
        }
        return ret;
    }
}
