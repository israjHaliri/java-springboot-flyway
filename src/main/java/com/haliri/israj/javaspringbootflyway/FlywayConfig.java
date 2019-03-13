package com.haliri.israj.javaspringbootflyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class FlywayConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @PostConstruct
    public void migrateFlyway() throws SQLException {
        if (env.getProperty("spring.flyway.enabled").equals("false")) {
            System.out.println("FLYWAY DISABLED");

            return;
        }

        System.out.println("RUNNING FLYWAY "+ dataSource.getConnection().getSchema());

        Flyway flyway = Flyway.configure().dataSource(dataSource).target(MigrationVersion.LATEST).load();
        System.out.println("flyway migrations : "+ Arrays.asList(flyway.getConfiguration().getLocations()));

        /**
         * use this if you want clear the migration cache
         * flyway.clean();
         */

        flyway.migrate();
    }
}
