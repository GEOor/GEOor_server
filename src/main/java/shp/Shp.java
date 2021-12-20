package shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static config.ApplicationProperties.getProperty;

public class Shp {
    private File file;
    private DataStore dataStore;
    private FeatureSource<SimpleFeatureType, SimpleFeature> source;

    public Shp(String fileName) {
        String pathName = getProperty("shp.directory") + fileName;
        file = new File(pathName);
        setDataStore(file);
        setSource();
    }

    private void setDataStore(File shpFile) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("url", shpFile.toURI().toURL());
            map.put("charset", "EUC-KR");
            dataStore = DataStoreFinder.getDataStore(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSource() {
        try {
            source = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public FeatureSource<SimpleFeatureType, SimpleFeature> getSource() {
        return source;
    }

    public File getFile() {
        return file;
    }
}
