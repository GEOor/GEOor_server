package shp;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.util.List;

/**
 * 출력역할만 함
 */
public class ShpMapper {

    private FeatureSource<SimpleFeatureType, SimpleFeature> source;
    private FeatureCollection<SimpleFeatureType, SimpleFeature> collection;

    public ShpMapper(Shp shp) {
        source = shp.getSource();
        collection = shp.getCollection();
    }

    // 맨 위 하나만 출력
    public void printAttributes(double longitude, double latitude) throws Exception {
        // attribute 추출
        List<AttributeDescriptor> Attributes = source.getSchema().getAttributeDescriptors();
        int attributeCount = source.getSchema().getAttributeCount();
        // 테스팅 좌표
        if(longitude < 1) {
            longitude = 126.966666;
            latitude = 37.55;
        }
        Point point = makePointGeometry(longitude, latitude);
        System.out.println("x = " + point.getX() + ", " + "y = " + point.getY());

        try (FeatureIterator<SimpleFeature> features = collection.features()) {
            while(features.hasNext()) {
                SimpleFeature feature = features.next();
                List<Object> values = feature.getAttributes();
                // tuple 추출
                MultiPolygon multiPolygon = (MultiPolygon) feature.getDefaultGeometryProperty().getValue();
                //System.out.println(multiPolygon.getCentroid().getX() + ", " + multiPolygon.getCentroid().getY());
                if(point.contains(multiPolygon)) {
                    for (int i = 0; i < attributeCount; i++) {
                        System.out.println(Attributes.get(i).getLocalName() + " : " + values.get(i));
                    }
                }
            }
        }
    }

    // 좌표계를 변환하고, 그 좌표를 gemotery 타입으로 변환하는 메소드
    public Point makePointGeometry(double longitude, double latitude) throws Exception {
        // reference : http://www.gisdeveloper.co.kr/?p=8942
        // X 좌표가 먼저 오도록 설정. 즉, longitude 먼저 나온다.
        System.setProperty("org.geotools.referencing.forceXY", "true");
        CoordinateReferenceSystem sourceCrs = CRS.decode("EPSG:4326");
        CoordinateReferenceSystem targetCrs = CRS.decode("EPSG:5179");
        MathTransform engine = CRS.findMathTransform(sourceCrs, targetCrs, true);

        DirectPosition2D source = new DirectPosition2D(sourceCrs, longitude, latitude);
        DirectPosition target = new DirectPosition2D(targetCrs);
        engine.transform(source, target);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate coord = new Coordinate(target.getCoordinate()[0], target.getCoordinate()[1]);
        //Coordinate coord = new Coordinate(1115098.00518875, 1964782.4199882038);
        return geometryFactory.createPoint(coord);
    }
}
