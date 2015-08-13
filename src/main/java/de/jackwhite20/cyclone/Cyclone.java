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

package de.jackwhite20.cyclone;

import de.jackwhite20.cyclone.builder.create.CreateQuery;
import de.jackwhite20.cyclone.builder.drop.DropQuery;
import de.jackwhite20.cyclone.builder.insert.InsertQuery;
import de.jackwhite20.cyclone.builder.select.SelectQuery;
import de.jackwhite20.cyclone.builder.update.UpdateQuery;
import de.jackwhite20.cyclone.db.DBConnection;
import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.settings.DBConnectionSettings;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class Cyclone {

    private DBConnectionSettings settings;

    private DBConnection connection;

    public Cyclone(DBConnectionSettings settings) {
        this.settings = settings;
    }

    public void connect() {

        connection = new DBConnection(settings);
    }

    public void create(CreateQuery query) throws SQLException {

        try (Connection con = connection.getConnection()) {
            con.createStatement().execute(query.toString());
        }
    }

    public void drop(DropQuery query) throws SQLException {

        try (Connection con = connection.getConnection()) {
            con.createStatement().execute(query.toString());
        }
    }

    public void insert(InsertQuery query) throws SQLException {

        try (Connection con = connection.getConnection()) {
            con.createStatement().execute(query.toString());
        }
    }

    public DBResult select(SelectQuery query) throws SQLException {

        Connection con = connection.getConnection();
        ResultSet resultSet = con.createStatement().executeQuery(query.toString());

        return new DBResult(con, resultSet);
    }

    public <T> List<T> selectCustom(SelectQuery query, Class<T> clazz) throws SQLException {

        List<T> results = new ArrayList<>();

        Connection con = null;
        try {
            con = connection.getConnection();
            ResultSet resultSet = con.createStatement().executeQuery(query.toString());
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();

            List<String> columns = new ArrayList<>(columnCount);
            for (int i = 1; i < columnCount + 1; i++) {
                columns.add(resultSetMetaData.getColumnName(i));
            }

            while (resultSet.next()) {
                T typeClass = clazz.newInstance();

                for (String column : columns) {
                    Field f = typeClass.getClass().getDeclaredField(column);
                    f.setAccessible(true);
                    f.set(typeClass, resultSet.getObject(column));
                    f.setAccessible(false);
                }

                results.add(typeClass);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            if(con != null)
                con.close();
        }

        return results;
    }

    public void update(UpdateQuery query) throws SQLException {

        try (Connection con = connection.getConnection()) {
            con.createStatement().execute(query.toString());
        }
    }
}
