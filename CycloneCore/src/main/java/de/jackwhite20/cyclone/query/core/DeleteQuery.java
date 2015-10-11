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

package de.jackwhite20.cyclone.query.core;

import de.jackwhite20.cyclone.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JackWhite20 on 28.09.2015.
 *
 * Represents a SQL DELETE query.
 */
public class DeleteQuery implements Query {

    private String table;

    private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

    private List<String> whereOperators = new ArrayList<>();

    public DeleteQuery(String table, LinkedHashMap<String, String> wheres, List<String> whereOperators) {

        this.table = table;
        this.wheres = wheres;
        this.whereOperators = whereOperators;
    }

    public DeleteQuery(Builder builder) {

        this.table = builder.table;
        this.wheres = builder.wheres;
        this.whereOperators = builder.whereOperators;
    }

    @Override
    public String sql() {

        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ").append(table);

        //TODO: Improve
        if(wheres.size() > 0) {
            sb.append(" WHERE ");

            int pos = 0;
            for (Map.Entry<String, String> entry : wheres.entrySet()) {
                sb.append(entry.getKey()).append(whereOperators.get(pos)).append("?").append(((wheres.size() > 1 && pos < wheres.size() - 1) ? " AND " : ""));
                pos++;
            }
        }

        sb.append(";");

        return sb.toString();
    }

    @Override
    public PreparedStatement prepareStatement(Connection connection) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql());
            List<String> wheresList = new ArrayList<>(wheres.values());
            for (int i = 0; i < wheresList.size(); i++) {
                preparedStatement.setObject(i + 1, wheresList.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }

    /**
     * Represents the builder for an delete query.
     */
    public static class Builder {

        private String table;

        private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

        private List<String> whereOperators = new ArrayList<>();

        /**
         * Sets the table name from which table data will be deleted.
         *
         * @param table the table name.
         * @return the builder.
         */
        public Builder from(String table) {

            this.table = table;

            return this;
        }

        /**
         * Adds a where clause with the given parameters.
         *
         * @param where the where column name.
         * @param operator the operator.
         * @param value the value.
         * @return the builder.
         */
        public Builder where(String where, String operator, String value) {

            this.wheres.put(where, value);
            this.whereOperators.add(operator);

            return this;
        }

        /**
         *
         * Adds a where clause with the given parameters and the operator '=' as default.
         *
         * @param where the where column name.
         * @param value the value.
         * @return the builder.
         */
        public Builder where(String where, String value) {

            return where(where, "=", value);
        }

        /**
         * Gets the finished DeleteQuery.
         *
         * @return the DeleteQuery.
         */
        public DeleteQuery build() {

            return new DeleteQuery(this);
        }
    }
}
