package shp;

import static config.ApplicationProperties.getProperty;

public class ShpService {

    public void run() {
        try {
            Shp shp = new Shp(getProperty("shp.fileName"));
            //        ShpConvert shpConvert = new ShpConvert(shp);
            //        shpConvert.printAttributes(0, 0);
            //        TestDraw testDraw = new TestDraw(shp.getFile());
            //        testDraw.displayShapefile();
            ShpRepository shpRepository = new ShpRepository(shp);
            shpRepository.save();
        } catch(Exception e) {

        }
    }
}
