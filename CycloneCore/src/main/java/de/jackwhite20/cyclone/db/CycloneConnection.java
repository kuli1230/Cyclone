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

        this.type = settings.type();
        this.settings = settings;

        setup();
    }

    private void setup() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(type.getDriver());
        if(type == Type.MYSQL) {
            config.setJdbcUrl("jdbc:mysql://" + settings.host() + ":" + settings.port() + "/" + settings.database());
        } else if(type == Type.SQLITE) {
            config.setJdbcUrl("jdbc:sqlite:" + settings.database());
            // Is fixing an error for sqllite
            config.setConnectionTestQuery("/* ping */");

            String path = settings.database().substring(0, settings.database().lastIndexOf("/"));

            File pathFile = new File(path);
            if(!pathFile.exists()) {
                if (!new File(path).mkdirs()) {
                    System.err.println("Error while creating path to database file! " + path);
                }
            }
        }
        config.setUsername(settings.user());
        config.setPassword(settings.password());
        config.setPoolName(settings.poolName());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(settings.poolSize());

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }

    public void close() {

        dataSource.close();
    }
}
