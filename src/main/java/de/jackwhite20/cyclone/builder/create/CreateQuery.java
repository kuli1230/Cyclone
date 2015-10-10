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

package de.jackwhite20.cyclone.builder.create;

import de.jackwhite20.cyclone.builder.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class CreateQuery implements Query {

    private String table;

    private boolean createNotExists = true;

    private List<String> values = new ArrayList<>();

    private HashMap<String, List<String>> options = new HashMap<>();

    private String primaryKey = null;

    public CreateQuery(String table, boolean createNotExists, List<String> values, HashMap<String, List<String>> options, String primaryKey) {

        this.table = table;
        this.createNotExists = createNotExists;
        this.values = values;
        this.options = options;
        this.primaryKey = primaryKey;
    }

    public CreateQuery(Builder builder) {

        this.table = builder.table;
        this.createNotExists = builder.createNotExists;
        this.primaryKey = builder.primaryKey;
        this.values = builder.values;
        this.options = builder.options;
    }

    @Override
    public String sql() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append((createNotExists) ? "IF NOT EXISTS " : "").append(table).append(" (");

        for (int i = 0; i < values.size(); i++) {
            if(i < values.size() - 1)
                sb.append(values.get(i)).append(" ").append(String.join(" ", options.get(values.get(i)))).append(",");
            else
                sb.append(values.get(i)).append(" ").append(String.join(" ", options.get(values.get(i))));
        }

        if(primaryKey != null) {
            sb.append(",PRIMARY KEY (");
            sb.append(primaryKey);
            sb.append(")");
        }

        sb.append(")").append(";");

        return sb.toString();
    }

    @Override
    public PreparedStatement query(Connection connection) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }

    public static class Builder {

        private String table;

        private boolean createNotExists = true;

        private List<String> values = new ArrayList<>();

        private HashMap<String, List<String>> options = new HashMap<>();

        private String primaryKey = null;

        public Builder create(String table) {

            this.table = table;

            return this;
        }

        public Builder ifNotExists(boolean value) {

            this.createNotExists = value;

            return this;
        }

        public Builder primaryKey(String column) {

            this.primaryKey = column;

            return this;
        }

        public Builder value(String value, String... options) {

            values.add(value);

            this.options.put(value, Arrays.asList(options));

            return this;
        }

        public CreateQuery build() {

            return new CreateQuery(this);
        }
    }
}
