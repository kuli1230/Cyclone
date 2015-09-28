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

package de.jackwhite20.cyclone.builder.delete;

import de.jackwhite20.cyclone.builder.Query;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackWhite20 on 28.09.2015.
 */
public class DeleteQuery implements Query {

    private String table;

    private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

    public DeleteQuery(String table, LinkedHashMap<String, String> wheres) {

        this.table = table;
        this.wheres = wheres;
    }

    public DeleteQuery(Builder builder) {

        this.table = builder.table;
        this.wheres = builder.wheres;
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
                sb.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'").append(((wheres.size() > 1 && pos < wheres.size() - 1) ? " AND " : ""));
                pos++;
            }
        }

        sb.append(";");

        return sb.toString();
    }

    public static class Builder {

        private String table;

        private LinkedHashMap<String, String> wheres = new LinkedHashMap<>();

        public Builder from(String table) {

            this.table = table;

            return this;
        }

        public Builder where(String where, String value) {

            this.wheres.put(where, value);

            return this;
        }

        public DeleteQuery build() {

            return new DeleteQuery(this);
        }
    }
}
