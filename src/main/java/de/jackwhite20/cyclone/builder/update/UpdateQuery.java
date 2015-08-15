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

package de.jackwhite20.cyclone.builder.update;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class UpdateQuery {

    private String table;

    private LinkedHashMap<String, String> values = new LinkedHashMap<>();

    private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

    private void addValues(String value, String equals) {

        values.put(value, equals);
    }

    private void addWheres(String where, String equals) {

        wheres.put(where, equals);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(table);

        //TODO: Improve
        int i = 0;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if(i == 0)
                sb.append(" SET ");

            sb.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'").append((i < values.size() - 1) ? "," : "");

            i++;
        }

        i = 0;
        for (Map.Entry<String, String> entry : wheres.entrySet()) {
            if(i == 0)
                sb.append(" WHERE ");

            sb.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'").append((i < wheres.size() - 1) ? " AND " : "");

            i++;
        }

        sb.append(";");

        return sb.toString();
    }

    public static class UpdateQueryBuilder {

        private UpdateQuery updateQuery = new UpdateQuery();

        public UpdateQueryBuilder update(String table) {

            updateQuery.table = table;

            return this;
        }

        public UpdateQueryBuilder set(String value, String newValue) {

            updateQuery.addValues(value, newValue);

            return this;
        }

        public UpdateQueryBuilder where(String where, String value) {

            updateQuery.addWheres(where, value);

            return this;
        }

        public UpdateQuery build() {

            return updateQuery;
        }
    }
}
