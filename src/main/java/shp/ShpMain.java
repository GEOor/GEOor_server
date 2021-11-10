package shp;

import org.opengis.referencing.FactoryException;

public class ShpMain {

    public void run() throws Exception {
        Shp shp = new Shp("/NF_A_A01000_A_A01000_000000.shp");
        ShpMapper shpMapper = new ShpMapper(shp);
        shpMapper.printAttributes(0, 0);
    }
}
