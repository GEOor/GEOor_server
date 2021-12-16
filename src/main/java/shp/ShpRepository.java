package shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.postgis.PostgisNGJNDIDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.CRS;
import org.geotools.util.URLs;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static config.ApplicationProperties.getProperty;

public class ShpRepository {

    private final Shp shp;
    private final String tableName = "geor";
    private HashMap<String, String> dbParam = new HashMap<>();

    public ShpRepository(Shp shp) throws IOException {
        this.shp = shp;
    }

    public void save() throws IOException {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put(PostgisNGDataStoreFactory.DBTYPE.key, PostgisNGDataStoreFactory.DBTYPE.sample);
        outParams.put(PostgisNGDataStoreFactory.USER.key, "postgres");
        outParams.put(PostgisNGDataStoreFactory.PASSWD.key, "1");
        outParams.put(PostgisNGDataStoreFactory.HOST.key, "localhost");
        outParams.put(PostgisNGDataStoreFactory.PORT.key, 5432);
        outParams.put(PostgisNGDataStoreFactory.DATABASE.key, "geor");
        outParams.put(PostgisNGDataStoreFactory.SCHEMA.key, "public");

        // Read
        DataStore inputDataStore = DataStoreFinder.getDataStore(
                Collections.singletonMap("url", URLs.fileToUrl(shp.getFile())));

        String inputTypeName = inputDataStore.getTypeNames()[0];
        SimpleFeatureType inputType = inputDataStore.getSchema(inputTypeName);

        FeatureSource<SimpleFeatureType, SimpleFeature>
                source = inputDataStore.getFeatureSource(inputTypeName);

        FeatureCollection<SimpleFeatureType, SimpleFeature>
                inputFeatureCollection = source.getFeatures();

        DataStore newDataStore = DataStoreFinder.getDataStore(outParams);


        String typeName = inputTypeName;

        newDataStore.createSchema(inputType);
        SimpleFeatureStore featureStore = (SimpleFeatureStore) newDataStore.getFeatureSource(typeName);

        // write results
        featureStore.addFeatures(source.getFeatures(/*filter*/));
        //tidy up
        inputDataStore.dispose();
        newDataStore.dispose();
        newDataStore.createSchema(inputType);
        String typeName1 = newDataStore.getTypeNames()[0];

        SimpleFeatureStore featureStore1 = (SimpleFeatureStore) newDataStore.getFeatureSource(typeName1);

        featureStore1.addFeatures(inputFeatureCollection);

        inputDataStore.dispose();
        newDataStore.dispose();
    }

}
