package no.kristiania.dao;

import no.kristiania.entity.User;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

public class TestData {
    public static DataSource testDataSource() {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:persondb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).
                load().clean();
        Flyway.configure().dataSource(dataSource).
                load().migrate();
        return dataSource;
    }



    private static final Random random = new Random();

    public static String pickOne(String... alternates) {
        return alternates[random.nextInt(alternates.length)];
    }
}
