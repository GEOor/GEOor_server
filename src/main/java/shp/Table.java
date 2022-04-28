package shp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public void init(Connection conn) throws SQLException {
        if (find(conn.getMetaData())) {
            System.out.printf("%s은 이미 존재하는 테이블입니다.\n", tableName);
            return;
        }
        create(conn);
        insert(conn);
    }

    private boolean find(DatabaseMetaData dbm) {
        try(ResultSet tables = dbm.getTables(null, null, tableName, null)) {
            if(tables.next())
                return true;
        } catch(SQLException e) {
            System.err.println("테이블 정보를 가져오는 과정에서 문제가 발생했습니다.");
            e.printStackTrace();
        }
        return false;
    }

    private void create(Connection conn) {
        String basePath = "src/main/resources/ddl/";
        String name = tableName + ".sql";
        try {
            RunScript runScript = new RunScript(basePath, name);
            runScript.createSQL(conn);
        } catch (FileNotFoundException e) {
            System.err.println("ddl 파일을 찾을 수 없습니다. 경로와 이름을 확인해 주세요.");
            e.printStackTrace();
        }
    }

    private void insert(Connection conn) {
        SaveShp saveShp = new SaveShp();
        saveShp.save(conn, getShp());
    }

    private List<Shp> getShp() {
        List<Shp> shpList = new ArrayList<>();
        File[] shpFiles = findShp();
        for (File file : shpFiles) {
            try {
                shpList.add(new Shp(file));
            } catch (IOException e) {
                System.err.printf("%s 식별 과정에 오류가 발생했습니다.\n", file.getName());
                e.printStackTrace();
            }
        }
        return shpList;
    }
    
    public File[] findShp() {
        String base = "./data/";
        String extension = "shp";
        File directory = new File(base);
        return directory.listFiles((dir, name) -> name.endsWith(extension));
    }
}
