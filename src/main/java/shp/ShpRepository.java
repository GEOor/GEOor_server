package shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static config.ApplicationProperties.getProperty;

public class ShpRepository {

    private HashMap<String, String> dbParam = new HashMap<>();

    public ShpRepository() {
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

    /**
     * attribute들이 대문자로 저장되기 때문에 SELECT 쿼리 사용시
     * the_geom을 제외한 모든 테이븡른 따옴표 붙여서 접근해야 함
     * @param shp 스키마의 틀이 되줄 shp 파일
     */
    public void createShpTable(Shp shp) {
        try {
            // set New Schema
            DataStore inputDataStore = shp.getDataStore();
            String typeName = inputDataStore.getTypeNames()[0];
            SimpleFeatureType schema = inputDataStore.getSchema(typeName);
            SimpleFeatureType newSchema = makeNewSchema(schema);
            // create New Schema
            DataStore newDataStore = DataStoreFinder.getDataStore(dbParam);
            newDataStore.createSchema(newSchema);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimpleFeatureType makeNewSchema(SimpleFeatureType schema) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(getProperty("shp.table"));
        builder.setSuperType((SimpleFeatureType) schema.getSuper());
        builder.addAll(schema.getAttributeDescriptors());
        builder.add("hillshade", Integer.class);
        return builder.buildFeatureType();
    }

    public void save(Connection conn, Shp shp, String query, ArrayList<String> columns) {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            int recordCount = shpIterate(pstmt, shp, columns);
            System.out.println(shp.getFile().getName() + " save " + recordCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int shpIterate(PreparedStatement pstmt, Shp shp, ArrayList<String> columns) throws SQLException {
        int recordCount = 0;
        FeatureIterator<SimpleFeature> features = shp.getFeature();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            setObject(pstmt, feature, columns);

            recordCount++;
            if(isBatchMax(recordCount)) {
                // batch 시키면서 나온 결과를 합산
                recordCount = pstmt.executeBatch().length;
            }
        }
        // remain batch execute
        recordCount = pstmt.executeBatch().length;
        pstmt.clearBatch();
        return recordCount;
    }

    private void setObject(PreparedStatement pStmt, SimpleFeature feature, ArrayList<String> columns) throws SQLException {
        int columnCount = columns.size();

        pStmt.setObject(1, feature.getDefaultGeometryProperty().getValue());
        for (int i = 1; i < columnCount; i++) {
            pStmt.setObject(i + 1, feature.getAttribute(columns.get(i)));
        }
        pStmt.addBatch();
    }

    private boolean isBatchMax(int recordCount) {
        int MAX_BATCH = 2048;
        return recordCount == MAX_BATCH;
    }
}