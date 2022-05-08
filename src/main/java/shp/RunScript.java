package shp;

import static config.ApplicationProperties.getProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RunScript {

    private final BufferedReader reader;
    private final String tableName;

    public RunScript(String tableName) throws FileNotFoundException {
        File file = new File(getProperty("ddl.base") + getProperty("ddl.road"));
        this.reader = new BufferedReader(new FileReader(file));
        this.tableName = tableName;
    }

    public void createTable(Connection conn) {
        String query = createQuery();
        try (Statement st = conn.createStatement()) {
            st.execute(query);
            conn.commit();
        } catch (SQLException e) {
            System.err.println("ddl 파일을 읽는데 오류가 발생했습니다. 파일을 확인해 주세요.");
            e.printStackTrace();
        }
    }

    private String createQuery() {
        try {
            return readFile();
        } catch (IOException e) {
            System.err.println("ddl 파일을 읽는데 오류가 발생했습니다. 파일을 확인해 주세요.");
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    private String readFile() throws IOException {
        StringBuilder query = new StringBuilder();
        String line;
        for (int i = 0; (line = reader.readLine()) != null; i++) {
            // road.sql 첫 줄은 게속 바위어야 해, 값을 따로 넣는다.
            if (i == 0) {
                query.append(" CREATE TABLE ");
                query.append(this.tableName);
                query.append(" (");
                continue;
            }
            query.append(line);
        }
        return query.toString();
    }

    private void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("ddl 파일을 닫는데 문제가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
