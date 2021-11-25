package Algorithms;

public class Hillshade {

    //x,y 좌표 값
    private int x;
    private int y;

    //위도, 경도
    private Double latitude, longitude;

    //hillshade 값
    private Double hillshade;

    public Hillshade(int x, int y, Double latitude, Double longitude, Double hillshade) {
        this.x = x;
        this.y = y;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hillshade = hillshade;
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

    public Double getHillshade() {
        return hillshade;
    }

    public void setHillshade(Double hillshade) {
        this.hillshade = hillshade;
    }

    @Override
    public String toString() {
        return "Hillshade{" +
                "x=" + x +
                ", y=" + y +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", hillshade=" + hillshade +
                '}';
    }
}
