package shp;

import org.locationtech.jts.geom.Point;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static config.ApplicationProperties.getProperty;

public class ShpService {
    private ArrayList<Shp> shps = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;
    private final ShpRepository shpRepository;


    public ShpService() {
        jdbcTemplate = new JdbcTemplate();
        shpRepository = new ShpRepository();
        findShpFiles();
    }

    /**
     * application.properties 에 지정된
     * directory 내의 모든 shp 파일 탐색 후 이름 저장
     */
    public void findShpFiles() {
        File directory = new File(getProperty("shp.directory"));
        File[] files = directory.listFiles((dir, name)
                -> name.endsWith("shp"));
        for (File file : files) {
            shps.add(new Shp(file.getPath()));
        }
    }

    public void run() throws Exception {
        if(shps.isEmpty())
            return;
        Connection conn = jdbcTemplate.getConnection();
        isTableExist(conn);
        ArrayList<String> columns = jdbcTemplate.getColumnCount(conn, getProperty("shp.table"));
        String insertQuery = jdbcTemplate.createPreStatementInsertSQL(getProperty("shp.table"), columns);
        System.out.println(insertQuery);
        for (Shp shp : shps) {
            shpRepository.save(conn, shp, insertQuery, columns);
        }
    }

    public void isTableExist(Connection conn) throws SQLException {
        if (jdbcTemplate.tableNotExist(conn, getProperty("shp.table"))) {
            shpRepository.createShpTable(shps.get(0));
        }
    }

    public void test() {
        ConvertSRID convertSRID = new ConvertSRID();
        TestDraw testDraw = new TestDraw(shps.get(0).getFile());
        try {
            Point point = convertSRID.convertPoint(29.669756415365768, 137.4406806414657);
            testDraw.displayShapefile();
        } catch(Exception e) {
        }
    }
}
