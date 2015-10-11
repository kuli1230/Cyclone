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

package de.jackwhite20.cyclone.db.serialization;

/**
 * Created by JackWhite20 on 11.10.2015.
 */
public class Condition {

    private final String column;

    private final Operator operator;

    private final String value;

    public Condition(String column, Operator operator, String value) {

        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public String column() {

        return column;
    }

    public Operator operator() {

        return operator;
    }

    public String value() {

        return value;
    }

    public enum Operator {

        EQUAL("="),
        GREATER(">"),
        LESS("<"),
        NOT_EQUAL("!="),
        GREATER_EQUAL(">="),
        LESS_EQUAL("<=");

        private String sql;

        Operator(String sql) {

            this.sql = sql;
        }

        public String sql() {

            return sql;
        }
    }
}
