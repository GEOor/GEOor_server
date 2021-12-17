package shp;

import static config.ApplicationProperties.getProperty;

public class ShpMain {

    public void run() throws Exception {
        Shp shp = new Shp(getProperty("shp.fileName"));
//        ShpConvert shpConvert = new ShpConvert(shp);
//        shpConvert.printAttributes(0, 0);
        TestDraw testDraw = new TestDraw(shp.getFile());
        testDraw.displayShapefile();
    }
}
