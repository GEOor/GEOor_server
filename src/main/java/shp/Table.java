package shp;

import static config.ApplicationProperties.getProperty;

import config.JdbcTemplate;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final String shpTypeName;
    private JdbcTemplate jdbcTemplate;

    public Table(String shpTypeName) {
        this.shpTypeName = shpTypeName;
        this.jdbcTemplate = new JdbcTemplate();
    }

    public void init()  {
        try(Connection conn = jdbcTemplate.getConnection()) {
            List<String> TableNames = createTableNames();
            for (String tableName : TableNames) {
                if (find(conn.getMetaData(), tableName)) {
                    System.out.printf("%s은 이미 존재하는 테이블입니다. 관련 테이블을 지우고 다시 실행해주세요.\n", tableName);
                    return;
                }
                create(conn, tableName);
                insert(conn, tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean find(DatabaseMetaData dbm, String tableName) {
        try(ResultSet tables = dbm.getTables(null, null, tableName, null)) {
            if(tables.next())
                return true;
        } catch(SQLException e) {
            System.err.println("테이블 정보를 가져오는 과정에서 문제가 발생했습니다.");
            e.printStackTrace();
        }
        return false;
    }

    private void create(Connection conn, String tableName) {
        try {
            RunScript runScript = new RunScript(tableName);
            runScript.createTable(conn);
        } catch (FileNotFoundException e) {
            System.err.println("ddl 파일을 찾을 수 없습니다. 경로와 이름을 확인해 주세요.");
            e.printStackTrace();
        }
    }

    private void insert(Connection conn, String tableName) {
        FindShp findShp = new FindShp(getProperty("type.road"));
        SaveShp saveShp = new SaveShp();
        saveShp.save(conn, findShp.getShpList(), tableName);
    }

    private List<String> createTableNames() {
        List<String> shpTypeNames = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
            String formatData = now.plusDays(i).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            shpTypeNames.add(shpTypeName + "_" + formatData);
        }
        return shpTypeNames;
    }
}
