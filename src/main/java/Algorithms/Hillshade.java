package Algorithms;

public class Hillshade {

    //hillshade 값
    private Double hillshade;

    //x,y 좌표 값
    private int x;
    private int y;

    public Hillshade(Double hillshade, int x, int y) {
        this.hillshade = hillshade;
        this.x = x;
        this.y = y;
    }

    public Double getHillshade() {
        return hillshade;
    }

    public void setHillshade(Double hillshade) {
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
}
