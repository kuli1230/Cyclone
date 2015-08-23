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

package de.jackwhite20.cyclone.builder.select;

import de.jackwhite20.cyclone.builder.insert.InsertQuery;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class SelectQuery {

    private String select;

    private String table;

    private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

    private String orderBy = null;

    private String limit = null;

    public SelectQuery(String select, String table, LinkedHashMap<String, String> wheres, String orderBy, String limit) {

        this.select = select;
        this.table = table;
        this.wheres = wheres;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public SelectQuery(Builder builder) {

        this.select = builder.select;
        this.table = builder.table;
        this.wheres = builder.wheres;
        this.orderBy = builder.orderBy;
        this.limit = builder.limit;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(select).append(" FROM ").append(table);

        //TODO: Improve
        if(wheres.size() > 0) {
            sb.append(" WHERE ");
            int pos = 0;
            for (Map.Entry<String, String> entry : wheres.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(((wheres.size() > 1 && pos < wheres.size() - 1) ? " AND " : ""));
                pos++;
            }
        }

        if(orderBy != null) {
            sb.append(" ORDER BY ").append(orderBy);
        }

        if(limit != null) {
            sb.append(" LIMIT ").append(limit);
        }

        sb.append(";");

        return sb.toString();
    }

    public static class Builder {

        private String select;

        private String table;

        private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

        private String orderBy = null;

        private String limit = null;

        public Builder select(String select) {

            this.select = select;

            return this;
        }

        public Builder from(String table) {

            this.table = table;

            return this;
        }

        public Builder where(String where, String value) {

            this.wheres.put(where, value);

            return this;
        }

        public Builder orderBy(String orderBy) {

            this.orderBy = orderBy;

            return this;
        }

        public Builder limit(String limit) {

            this.limit = limit;

            return this;
        }

        public Builder limit(int limit) {

            this.limit = "" + limit;

            return this;
        }

        public SelectQuery build() {

            return new SelectQuery(this);
        }
    }
}
