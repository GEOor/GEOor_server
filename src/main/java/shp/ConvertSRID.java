package shp;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class ConvertSRID {
    private CoordinateReferenceSystem sourceCrs;
    private CoordinateReferenceSystem targetCrs;
    private MathTransform engine;

    public ConvertSRID() {
        try {
            // reference : http://www.gisdeveloper.co.kr/?p=8942
            sourceCrs = CRS.decode("EPSG:4326");
            targetCrs = CRS.decode("EPSG:5179");
            engine = CRS.findMathTransform(sourceCrs, targetCrs, true);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        // X 좌표가 먼저 오도록 설정. 즉, longitude 먼저 나온다.
        System.setProperty("org.geotools.referencing.forceXY", "true");
    }

    public Point convertPoint(double longitude, double latitude) throws TransformException {
        DirectPosition2D source = new DirectPosition2D(sourceCrs, longitude, latitude);
        DirectPosition target = new DirectPosition2D(targetCrs);
        engine.transform(source, target);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate coord = new Coordinate(target.getCoordinate()[0], target.getCoordinate()[1]);
        return geometryFactory.createPoint(coord);
    }

    public Point revertPoint(double longitude, double latitude) throws TransformException {
        DirectPosition2D source = new DirectPosition2D(targetCrs, longitude, latitude);
        DirectPosition target = new DirectPosition2D(sourceCrs);
        engine.transform(source, target);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate coord = new Coordinate(target.getCoordinate()[0], target.getCoordinate()[1]);
        return geometryFactory.createPoint(coord);
    }
}
