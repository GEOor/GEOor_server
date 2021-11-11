package shp;

import static config.ApplicationProperties.getProperty;

public class ShpMain {

    public void run() throws Exception {
        Shp shp = new Shp(getProperty("shp.fileName"));
        ShpMapper shpMapper = new ShpMapper(shp);
        shpMapper.printAttributes(0, 0);
    }
}
