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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class SelectQuery {

    private String select;

    private String table;

    private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

    private void addWheres(String where, String equals) {

        wheres.put(where, equals);
    }

    private String orderBy = null;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(select).append(" FROM ").append(table).append(" ");

        //TODO: Improve
        if(wheres.size() > 0) {
            sb.append("WHERE ");
            int pos = 0;
            for (Map.Entry<String, String> entry : wheres.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(((wheres.size() > 1 && pos < wheres.size() - 1) ? " AND " : ""));
                pos++;
            }
        }

        if(orderBy != null) {
            sb.append(" ORDER BY ").append(orderBy);
        }

        sb.append(";");

        return sb.toString();
    }

    public static class SelectQueryBuilder {

        private SelectQuery selectQuery = new SelectQuery();

        public SelectQueryBuilder select(String select) {

            selectQuery.select = select;

            return this;
        }

        public SelectQueryBuilder from(String table) {

            selectQuery.table = table;

            return this;
        }

        public SelectQueryBuilder where(String where, String value) {

            selectQuery.addWheres(where, value);

            return this;
        }

        public SelectQueryBuilder orderBy(String orderBy) {

            selectQuery.orderBy = orderBy;

            return this;
        }

        public SelectQuery build() {

            return selectQuery;
        }

    }
}
