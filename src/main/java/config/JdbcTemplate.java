package config;

import java.sql.*;

public class JdbcTemplate {
    private final String connectUrl;
    private final String user;
    private final String password;

    public JdbcTemplate() {
        connectUrl = "jdbc:postgresql://localhost:5432/geor";
        user = "postgres";
        password = "1";
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
}
