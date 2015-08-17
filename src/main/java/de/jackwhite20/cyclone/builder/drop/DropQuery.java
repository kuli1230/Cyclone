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

package de.jackwhite20.cyclone.builder.drop;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class DropQuery {

    private String table;

    public DropQuery(String table) {

        this.table = table;
    }

    public DropQuery(Builder builder) {

        this.table = builder.table;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("DROP TABLE ").append(table).append(";");

        return sb.toString();
    }

    public static class Builder {

        private String table;

        public Builder drop(String table) {

            this.table = table;

            return this;
        }

        public DropQuery build() {

            return new DropQuery(this);
        }
    }
}
