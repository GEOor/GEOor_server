package shp;


import config.JdbcTemplate;

public class ShpMain {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public static void main(String[] args) throws Exception {
        Table table = new Table("road");
        table.init(jdbcTemplate.getConnection());
    }
}
