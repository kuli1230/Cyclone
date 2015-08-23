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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class DBResult {

    /**
     * The connection to the mysql server.
     */
    private Connection connection;

    /**
     * The results from a query.
     */
    private ResultSet resultSet;

    /**
     * List of all rows.
     */
    private List<DBRow> rows = new ArrayList<>();

    public DBResult(Connection connection, ResultSet resultSet) {

        this.connection = connection;
        this.resultSet = resultSet;

        ResultSetMetaData resultSetMetaData = null;
        try {
            resultSetMetaData = this.resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            List<String> columns = new ArrayList<>(columnCount);
            columnCount += 1;
            for (int i = 1; i < columnCount; i++) {
                columns.add(resultSetMetaData.getColumnName(i));
            }

            while (this.resultSet.next()) {
                DBRow row = new DBRow();

                for (String column : columns) {
                    row.add(column, this.resultSet.getObject(column));
                }

                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection and the result set.
     */
    public void close() {

        try {
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the result set.
     *
     * @return the result set object.
     */
    public ResultSet resultSet() {

        return resultSet;
    }

    /**
     * Gets the connection.
     *
     * @return the connection object.
     */
    public Connection connection() {

        return connection;
    }

    /**
     * Gets all rows from the query.
     *
     * @return a list with all rows as DBRow objects.
     */
    public List<DBRow> rows() {

        return rows;
    }
}
