package shp;


import config.JdbcTemplate;

import config.ApplicationProperties;

public class ShpMain {

    private static final ApplicationProperties applicationProperties = new ApplicationProperties();

    public static void main(String[] args) {
        Table table = new Table(getProperty("type.road"));
        table.init();
    }
}
