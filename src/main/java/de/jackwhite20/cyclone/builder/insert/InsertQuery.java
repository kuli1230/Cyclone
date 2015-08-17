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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class InsertQuery {

    private String table;

    private List<String> values = new ArrayList<>();

    private void addValue(String value) {

        values.add(value);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table).append(" VALUES ").append("(");

        for (int i = 0; i < values.size(); i++) {
            if(i < values.size() - 1)
                sb.append("'").append(values.get(i)).append("'").append(",");
            else
                sb.append("'").append(values.get(i)).append("'");
        }

        sb.append(")").append(";");

        return sb.toString();
    }

    public static class Builder {

        private InsertQuery insertQuery = new InsertQuery();

        public Builder into(String table) {

            insertQuery.table = table;

            return this;
        }

        public Builder values(String... values) {

            for (String value : values) {
                insertQuery.addValue(value);
            }

            return this;
        }

        public InsertQuery build() {

            return insertQuery;
        }
    }
}
