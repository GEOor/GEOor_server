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
    private HashMap<String, String> dbParam = new HashMap<>();

    public ShpRepository(Shp shp) throws IOException {
        this.shp = shp;
        dbParam.put(PostgisNGDataStoreFactory.DBTYPE.key, getProperty("db.type"));
        dbParam.put(PostgisNGDataStoreFactory.HOST.key, getProperty("db.host"));
        dbParam.put(PostgisNGDataStoreFactory.PORT.key, getProperty("db.port"));
        dbParam.put(PostgisNGDataStoreFactory.SCHEMA.key, getProperty("db.schema"));
        dbParam.put(PostgisNGDataStoreFactory.DATABASE.key, getProperty("db.database"));
        dbParam.put(PostgisNGDataStoreFactory.USER.key, getProperty("db.user"));
        dbParam.put(PostgisNGDataStoreFactory.PASSWD.key, "1");
    }

    // https://stackoverflow.com/questions/54545780/shape-file-or-geojson-to-database/54556180#54556180
    public void save() throws IOException {
        // Read
        DataStore inputDataStore = DataStoreFinder.getDataStore(
                Collections.singletonMap("url", URLs.fileToUrl(shp.getFile())));

        String inputTypeName = inputDataStore.getTypeNames()[0];
        SimpleFeatureType inputType = inputDataStore.getSchema(inputTypeName);

        FeatureSource<SimpleFeatureType, SimpleFeature>
                source = inputDataStore.getFeatureSource(inputTypeName);

        FeatureCollection<SimpleFeatureType, SimpleFeature>
                inputFeatureCollection = source.getFeatures();

        DataStore newDataStore = DataStoreFinder.getDataStore(dbParam);


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
