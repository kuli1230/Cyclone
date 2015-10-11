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
import java.util.Arrays;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class InsertQuery implements Query {

    private String table;

    private List<String> columns;

    private List<String> values;

    public InsertQuery(String table, List<String> columns, List<String> values) {

        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    public InsertQuery(Builder builder) {

        this.table = builder.table;
        this.columns = builder.columns;
        this.values = builder.values;
    }

    @Override
    public String sql() {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table);

        if(columns.size() > 0) {
            sb.append(" (");
            for (int i = 0; i < columns.size(); i++) {
                if(i < columns.size() - 1)
                    sb.append(columns.get(i)).append(",");
                else
                    sb.append(columns.get(i));
            }
            sb.append(")");
        }

        sb.append(" VALUES ").append("(");

        for (int i = 0; i < values.size(); i++) {
            if(i < values.size() - 1)
                sb.append("?,");
            else
                sb.append("?");
        }

        return sb.append(")").append(";").toString();
    }

    public PreparedStatement prepareStatement(Connection connection) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql());
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }

    public static class Builder {

        private String table;

        private List<String> columns = new ArrayList<>();

        private List<String> values = new ArrayList<>();

        public Builder into(String table) {

            this.table = table;

            return this;
        }

        public Builder columns(String... columns) {

            this.columns.addAll(Arrays.asList(columns));

            return this;
        }

        public Builder column(String name) {

            this.columns.add(name);

            return this;
        }

        public Builder values(String... values) {

            this.values.addAll(Arrays.asList(values));

            return this;
        }

        public Builder value(String value) {

            this.values.add(value);

            return this;
        }

        public InsertQuery build() {

            return new InsertQuery(this);
        }
    }
}
