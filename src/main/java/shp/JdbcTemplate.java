package shp;

import java.sql.*;
import java.util.ArrayList;

public class JdbcTemplate {
    private final String connectUrl = "jdbc:postgresql://localhost:5432/geor";
    private final String user = "postgres";
    private final String password = "1";

    public JdbcTemplate() {
        setClass();
    }

    public void setClass() {

    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(connectUrl, user, password);
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * db에 테이블이 있는지 확인
     * @return false : table 있음
     *         true  : table 없음
     */
    public boolean tableNotExist(Connection conn, String tableName) throws SQLException {
        try(ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            if (!rs.next()) {
                System.out.println(tableName + " table이 이 없습니다. table을 생성합니다...");
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getColumnCount(Connection conn, String tableName) {
        ArrayList<String> columns = new ArrayList<>();
        String Query = "select * from public." + tableName;
        try(PreparedStatement pstmt = conn.prepareStatement(Query)) {
            ResultSetMetaData meta = pstmt.getMetaData();
            for (int i=2; i <= meta.getColumnCount(); i++)
            {
                System.out.println("Column name: " + meta.getColumnName(i) + ", data type: " + meta.getColumnTypeName(i));
                columns.add(meta.getColumnName(i));
            }
            System.out.println(columns.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    public String createPreStatementInsertSQL(String tableName, ArrayList<String> columns) {
        StringBuilder query = new StringBuilder("insert into public.");
        query.append(tableName + " values (");
        for (int i = 0; i < columns.size() - 1; i++) {
            query.append("?, ");
        }
        query.append("?);");
        return query.toString();
    }

    public void test() {
        try (Connection connection = DriverManager.getConnection(connectUrl, user, password)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from public.test");

            while (rs.next()) {
                String version = rs.getString("RBID");
                System.out.println(version);
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
