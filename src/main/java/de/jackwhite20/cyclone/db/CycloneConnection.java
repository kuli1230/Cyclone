package de.jackwhite20.cyclone.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by JackWhite20 on 29.09.2015.
 */
public class CycloneConnection {

    private Type type;

    private CycloneSettings settings;

    private HikariDataSource dataSource;

    public CycloneConnection(CycloneSettings settings) {

        this.type = settings.getType();
        this.settings = settings;

        setup();
    }

    private void setup() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(type.getDriver());
        if(type == Type.MY_SQL) {
            config.setJdbcUrl("jdbc:mysql://" + settings.getHost() + ":" + settings.getPort() + "/" + settings.getDatabase());
        } else if(type == Type.SQL_LITE) {
            config.setJdbcUrl("jdbc:sqlite:" + settings.getDatabase());
            // Is fixing an error for sqllite
            config.setConnectionTestQuery("/* ping */");

            String path = settings.getDatabase().substring(0, settings.getDatabase().lastIndexOf("/"));

            File pathFile = new File(path);
            if(!pathFile.exists()) {
                if (!new File(path).mkdirs()) {
                    System.err.println("Error while creating path to database file! " + path);
                }
            }
        }
        config.setUsername(settings.getUser());
        config.setPassword(settings.getPassword());
        config.setPoolName(settings.getPoolName());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(settings.getPoolSize());

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }
}
