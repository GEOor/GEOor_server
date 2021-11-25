package sun;

public class SunInfo {

    //x, y좌표
    private int x, y;
    //위도, 경도
    private Double latitude, longitude;
    //시각
    private Integer time;
    //방위각
    private Double azimuth;
    //고도
    private Double altitude;
    //적경
    private Double ascension;
    //적위
    private Double declination;

    public SunInfo(int x, int y, Double latitude, Double longitude, Integer time, Double azimuth, Double altitude, Double ascension, Double declination) {
        this.x = x;
        this.y = y;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.azimuth = azimuth;
        this.altitude = altitude;
        this.ascension = ascension;
        this.declination = declination;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getAscension() {
        return ascension;
    }

    public void setAscension(Double ascension) {
        this.ascension = ascension;
    }

    public Double getDeclination() {
        return declination;
    }

    public void setDeclination(Double declination) {
        this.declination = declination;
    }

    @Override
    public String toString() {
        return "SunInfo{" +
                "x=" + x +
                ", y=" + y +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", time=" + time +
                ", azimuth=" + azimuth +
                ", altitude=" + altitude +
                ", ascension=" + ascension +
                ", declination=" + declination +
                '}';
    }
}
