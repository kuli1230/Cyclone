/*
 * Copyright (c) 2015 "JackWhite20"
 *
 * This file is part of Cyclone.
 *
 * Cyclone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jackwhite20.cyclone.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class DBConnection {

    private CycloneSettings settings;

    private HikariDataSource source;

    public DBConnection(CycloneSettings settings) {

        this.settings = settings;

        init();
    }

    private void init() {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(uri());
        hikariConfig.setUsername(settings.getUser());
        hikariConfig.setPassword(settings.getPassword());
        hikariConfig.setPoolName("Cyclone");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setMaximumPoolSize(settings.getPoolSize());
        //hikariConfig.setConnectionTestQuery("/* ping */");

        this.source = new HikariDataSource(hikariConfig);
    }

    private String uri() {

        return "jdbc:mysql://" + settings.getHost() + ":" + settings.getPort() + "/" + settings.getDatabase();
    }

    public Connection getConnection() throws SQLException {

        return source.getConnection();
    }
}
