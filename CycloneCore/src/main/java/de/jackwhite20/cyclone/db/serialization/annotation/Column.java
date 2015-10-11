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

package de.jackwhite20.cyclone.db.serialization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JackWhite20 on 10.10.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String name() default "";

    Option[] options() default Option.EMPTY;

    enum Option {
        EMPTY(""),
        NOT_NULL("NOT NULL"),
        AUTO_INCREMENT("AUTO_INCREMENT"),
        UNIQUE("UNIQUE"),
        PRIMARY_KEY("PRIMARY_KEY");

        private String sql;

        Option(String sql) {

            this.sql = sql;
        }

        public boolean isOption(Column.Option[] options) {

            for (int i = 0; i < options.length; i++) {
                if(options[i] == this)
                    return true;
            }

            return false;
        }

        public String sql() {

            return sql;
        }
    }

    enum Type {
        INTEGER("INTEGER", int.class, Integer.class),
        STRING("VARCHAR(255)", String.class),
        BOOLEAN("BOOLEAN", boolean.class, Boolean.class),
        LONG("BIGINT", Long.class, long.class),
        DOUBLE("DOUBLE", double.class, Double.class),
        FLOAT("FLOAT", Float.class, float.class),
        BYTE("TINYINT", Byte.class, byte.class),
        SHORT("SMALLINT", Short.class, short.class),
        CHAR("CHAR", char.class),
        UNKNOWN("UNKNOWN");

        private String sqlName;

        private Class<?>[] javaClasses;

        Type(String sqlName, Class<?>... javaClasses) {

            this.sqlName = sqlName;
            this.javaClasses = javaClasses;
        }

        public static Type typeFromClass(Class<?> clazz) {

            for(Type type : Type.values()) {
                for(Class<?> javaClass : type.javaClasses) {
                    if(javaClass == clazz) {
                        return type;
                    }
                }
            }

            return Type.UNKNOWN;
        }

        public String sqlName() {

            return sqlName;
        }

        public Class<?>[] javaClasses() {

            return javaClasses;
        }
    }
}