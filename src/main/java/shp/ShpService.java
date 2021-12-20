package shp;

import org.locationtech.jts.geom.Point;
import static config.ApplicationProperties.getProperty;

public class ShpService {
    private final Shp shp;
    private final ConvertSRID convertSRID;

    public ShpService() {
        shp = new Shp(getProperty("shp.fileName"));
        convertSRID = new ConvertSRID();
    }

    public void run() {
        try {
            ShpRepository shpRepository = new ShpRepository(shp);
            //shpRepository.save();
            Point point = convertSRID.convertPoint(29.669756415365768, 137.4406806414657);
            System.out.println(point.toString());
            shpRepository.find(point);
//            TestDraw testDraw = new TestDraw(shp.getFile());
//            testDraw.displayShapefile();
        } catch(Exception e) {

        }
    }
}
