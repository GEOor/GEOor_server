package shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static config.ApplicationProperties.getProperty;

public class ShpRepository {

    private Shp shp;
    private HashMap<String, String> dbParam = new HashMap<>();

    public ShpRepository(Shp shp) {
        this.shp = shp;
        setDbParam();
    }

    private void setDbParam() {
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
        // set New Schema
        DataStore inputDataStore = shp.getDataStore();
        String typeName = inputDataStore.getTypeNames()[0];
        SimpleFeatureType schema = inputDataStore.getSchema(typeName);
        SimpleFeatureType newSchema = makeNewSchema(schema);
        // add hillshade Attribute
        List<SimpleFeature> features = setHillShadeAttribute(inputDataStore, newSchema, typeName);
        // write
        DataStore newDataStore = DataStoreFinder.getDataStore(dbParam);
        newDataStore.createSchema(newSchema);
        SimpleFeatureStore store = (SimpleFeatureStore) newDataStore.getFeatureSource(typeName);
        store.addFeatures(DataUtilities.collection(features));
        // tidy up
        newDataStore.dispose();
        inputDataStore.dispose();
    }

    private SimpleFeatureType makeNewSchema(SimpleFeatureType schema) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(schema.getName());
        builder.setSuperType((SimpleFeatureType) schema.getSuper());
        builder.addAll(schema.getAttributeDescriptors());
        builder.add("hillshade", Integer.class);
        return builder.buildFeatureType();
    }

    private List<SimpleFeature> setHillShadeAttribute(DataStore inputDataStore, SimpleFeatureType newSchema, String typeName) {
        List<SimpleFeature> features = new ArrayList<>();
        try (SimpleFeatureIterator itr = inputDataStore.getFeatureSource(typeName).getFeatures().features()) {
            while (itr.hasNext()) {
                SimpleFeature f = itr.next();
                SimpleFeature f2 = DataUtilities.reType(newSchema, f);
                f2.setAttribute("hillshade", 0);
                features.add(f2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return features;
    }

    public void find(Point point) {
        String Query = "CONTAINS (the_geom, " + point.toString() + ")";
        try {
            Filter filter = CQL.toFilter(Query);
            DataStore dataStore = DataStoreFinder.getDataStore(dbParam);
            String typeName = dataStore.getTypeNames()[0];
            SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
            SimpleFeatureCollection result = source.getFeatures(filter);
            printResult(result.features());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 확인용
    public void printResult(SimpleFeatureIterator iterator) {
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
            List<Object> values = feature.getAttributes();
            for (Object value : values) {
                System.out.println(value);
            }
        }
    }
}