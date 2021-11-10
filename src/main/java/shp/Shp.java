package shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.ApplicationProperties.getProperty;

public class Shp {

    private final File file;
    private final Map<String, Object> map = new HashMap<>();
    private FeatureSource<SimpleFeatureType, SimpleFeature> source;

    public Shp(String fileName) {
        String pathName = getProperty("shp.directory") + fileName;
        this.file = new File(pathName);
        setMap();
        setSource();
    }

    private void setMap() {
        try {
            map.put("url", file.toURI().toURL());
            // 한글 깨짐 방지
            map.put("charset", "EUC-KR");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 세부 조정할 일 아니면 이해할 필요는 없다.
     */
    private void setSource() {
        DataStore dataStore = null;
        try {
            dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];


            source = dataStore.getFeatureSource(typeName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(dataStore != null)
                dataStore.dispose();
        }
    }

    public FeatureSource<SimpleFeatureType, SimpleFeature> getSource() {
        return source;
    }

    public FeatureCollection<SimpleFeatureType, SimpleFeature> getCollection() {
        Filter filter = Filter.INCLUDE;
        FeatureCollection<SimpleFeatureType, SimpleFeature> features;
        try {
            features = source.getFeatures(filter);
            return features;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
