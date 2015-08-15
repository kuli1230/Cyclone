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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class CreateQuery {

    private String table;

    private boolean createNotExists = true;

    private List<String> values = new ArrayList<>();

    private String primaryKey = null;

    public CreateQuery(String table, boolean createNotExists, List<String> values, String primaryKey) {

        this.table = table;
        this.createNotExists = createNotExists;
        this.values = values;
        this.primaryKey = primaryKey;
    }

    public CreateQuery(CreateQueryBuilder builder) {

        this.table = builder.table;
        this.createNotExists = builder.createNotExists;
        this.primaryKey = builder.primaryKey;
        this.values = builder.values;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append((createNotExists) ? "IF NOT EXISTS " : "").append(table).append(" (");

        for (int i = 0; i < values.size(); i++) {
            if(i < values.size() - 1)
                sb.append(values.get(i)).append(",");
            else
                sb.append(values.get(i));
        }

        if(primaryKey != null) {
            sb.append(",PRIMARY KEY (");
            sb.append(primaryKey);
            sb.append(")");
        }

        sb.append(")").append(";");

        return sb.toString();
    }

    public static class CreateQueryBuilder {

        private String table;

        private boolean createNotExists;

        private List<String> values = new ArrayList<>();

        private String primaryKey = null;

        public CreateQueryBuilder create(String table) {

            this.table = table;

            return this;
        }

        public CreateQueryBuilder ifNotExists(boolean value) {

            this.createNotExists = value;

            return this;
        }

        public CreateQueryBuilder primaryKey(String column) {

            this.primaryKey = column;

            return this;
        }

        public CreateQueryBuilder values(String... values) {

            for (String value : values) {
                this.values.add(value);
            }

            return this;
        }

        public CreateQuery build() {

            return new CreateQuery(this);
        }
    }
}
