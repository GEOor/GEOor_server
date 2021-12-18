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
            shpRepository.save();
            // Point point = convertSRID.convertPoint(29.672782728513347, 137.43395804125578);
            // shpRepository.find(point);
        } catch(Exception e) {

        }
    }
}
