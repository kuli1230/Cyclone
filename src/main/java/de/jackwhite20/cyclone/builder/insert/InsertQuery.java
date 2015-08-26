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

package de.jackwhite20.cyclone.builder.insert;

import de.jackwhite20.cyclone.builder.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class InsertQuery implements Query {

    private String table;

    private List<String> values = new ArrayList<>();

    public InsertQuery(String table, List<String> values) {

        this.table = table;
        this.values = values;
    }

    public InsertQuery(Builder builder) {

        this.table = builder.table;
        this.values = builder.values;
    }

    @Override
    public String sql() {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table).append(" VALUES ").append("(");

        for (int i = 0; i < values.size(); i++) {
            if(i < values.size() - 1)
                sb.append("?,");
            else
                sb.append("?");
        }

        return sb.append(")").append(";").toString();
    }

    public PreparedStatement query(Connection connection) {

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

        private List<String> values = new ArrayList<>();

        public Builder into(String table) {

            this.table = table;

            return this;
        }

        public Builder values(String... values) {

            this.values = Arrays.asList(values);

            return this;
        }

        public InsertQuery build() {

            return new InsertQuery(this);
        }
    }
}
