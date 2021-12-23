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
        jdbcTemplate = new JdbcTemplate(getProperty("shp.table"));
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

    public void run() throws SQLException {
        // 시작 전 shp 파일들이 있는지 확인
        if (shps.isEmpty()) {
            System.out.println("shp 파일이 없습니다. 경로를 확인해주세요.");
            return;
        }
        // 시작 전 테이블 확인
        try(Connection conn = jdbcTemplate.getConnection()) {
            if(jdbcTemplate.tableNotExist(conn)) {
                jdbcTemplate.createTable(conn);
            }
        }
        // 테이블과 shp 파일 둘 다 확인됐으면 shp 정보 삽입
        try (Connection conn = jdbcTemplate.getConnection()) {
            // 테이블에서 column 목록 가져옴
            ArrayList<String> columns = jdbcTemplate.getColumns(conn);
            shpRepository.save(conn, shps, columns);
            conn.commit();
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
