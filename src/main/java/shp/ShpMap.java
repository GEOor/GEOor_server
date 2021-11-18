package shp;

import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;

import java.io.File;
import java.io.IOException;

public class ShpMap {
    private final File file;
    private JMapFrame mapFrame;

    public ShpMap(Shp shp) {
        this.file = shp.getFile();
    }

    public void createMap() throws IOException {
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        FeatureSource featureSource = store.getFeatureSource();

        MapContent map = new MapContent();
        map.setTitle("test");

        // Create Basic Style
        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        // Add Feature
        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);
        mapSetting(map);
    }

    private void mapSetting(MapContent map) {
        mapFrame = new JMapFrame(map);
        mapFrame.enableToolBar(true);
        mapFrame.enableStatusBar(true);
        mapFrame.setSize(600, 600);
        mapFrame.setVisible(true);
    }
}
