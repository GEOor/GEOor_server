package sun;

public class SunInfo {

    //날짜
    String locdate;
    //지역
    String location;
    //경도
    Double longitude;
    //경도(10진수)
    Double longitudeNum;
    //위도
    Double latitude;
    //위도(10진수)
    Double latitudeNum;
    //9시 방위각
    Double azimuth_09;
    //9시 고도
    Double altitude_09;
    //12시 방위각
    Double azimuth_12;
    //12시 고도
    Double altitude_12;
    //15시 방위각
    Double azimuth_15;
    //15시 고도
    Double altitude_15;
    //18시 방위각
    Double azimuth_18;
    //18시 고도
    Double altitude_18;
    //남중 고도
    Double altitudeMeridian;

    /* 고정 변수 */
    //페이지당 항목수
    Integer numOfRows = 10;
    //페이지
    Integer pageNo = 1;

    @Override
    public String toString() {
        return "SunInfo{" +
                "locdate='" + locdate + '\'' +
                ", location='" + location + '\'' +
                ", longitude=" + longitude +
                ", longitudeNum=" + longitudeNum +
                ", latitude=" + latitude +
                ", latitudeNum=" + latitudeNum +
                ", azimuth_09=" + azimuth_09 +
                ", altitude_09=" + altitude_09 +
                ", azimuth_12=" + azimuth_12 +
                ", altitude_12=" + altitude_12 +
                ", azimuth_15=" + azimuth_15 +
                ", altitude_15=" + altitude_15 +
                ", azimuth_18=" + azimuth_18 +
                ", altitude_18=" + altitude_18 +
                ", altitudeMeridian=" + altitudeMeridian +
                ", numOfRows=" + numOfRows +
                ", pageNo=" + pageNo +
                '}';
    }

    /* Constructor */

    public SunInfo(String locdate, String location, Double longitude, Double longitudeNum, Double latitude, Double latitudeNum, Double azimuth_09, Double altitude_09, Double azimuth_12, Double altitude_12, Double azimuth_15, Double altitude_15, Double azimuth_18, Double altitude_18, Double altitudeMeridian) {
        this.locdate = locdate;
        this.location = location;
        this.longitude = longitude;
        this.longitudeNum = longitudeNum;
        this.latitude = latitude;
        this.latitudeNum = latitudeNum;
        this.azimuth_09 = azimuth_09;
        this.altitude_09 = altitude_09;
        this.azimuth_12 = azimuth_12;
        this.altitude_12 = altitude_12;
        this.azimuth_15 = azimuth_15;
        this.altitude_15 = altitude_15;
        this.azimuth_18 = azimuth_18;
        this.altitude_18 = altitude_18;
        this.altitudeMeridian = altitudeMeridian;
    }

    /* Getter */

    public String getLocdate() {
        return locdate;
    }

    public String getLocation() {
        return location;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLongitudeNum() {
        return longitudeNum;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLatitudeNum() {
        return latitudeNum;
    }

    public Double getAzimuth_09() {
        return azimuth_09;
    }

    public Double getAltitude_09() {
        return altitude_09;
    }

    public Double getAzimuth_12() {
        return azimuth_12;
    }

    public Double getAltitude_12() {
        return altitude_12;
    }

    public Double getAzimuth_15() {
        return azimuth_15;
    }

    public Double getAltitude_15() {
        return altitude_15;
    }

    public Double getAzimuth_18() {
        return azimuth_18;
    }

    public Double getAltitude_18() {
        return altitude_18;
    }

    public Double getAltitudeMeridian() {
        return altitudeMeridian;
    }
}
