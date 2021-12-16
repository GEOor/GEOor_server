package shp;

import static config.ApplicationProperties.getProperty;

public class ShpMain {

    public void run() {
        try {
            Shp shp = new Shp(getProperty("shp.fileName"));
            ShpRepository shpRepository = new ShpRepository(shp);
            shpRepository.save();
//            ShpConvert shpConvert = new ShpConvert(shp);
//            shpConvert.printAttributes(0, 0);
//            TestDraw testDraw = new TestDraw(shp.getFile());
//            testDraw.displayShapefile();
        } catch(Exception e) {

        }
    }
}
