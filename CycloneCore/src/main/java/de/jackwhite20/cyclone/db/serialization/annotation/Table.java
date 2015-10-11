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
 *
 * The annotation to mark a class as a database table.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * Gets the name from the table.
     *
     * @return the name.
     */
    String name();

    /**
     * Gets options for the table.
     *
     * @return the options.
     */
    Option[] options() default Option.EMPTY;

    /**
     * Represents options that SQL table can have.
     */
    enum Option {
        EMPTY,
        CREATE_IF_NOT_EXISTS;

        /**
         * Checks if the current option is in the given option array.
         *
         * @param options the options.
         * @return true if the the options array contains the current option, otherwise false.
         */
        public boolean isOption(Table.Option[] options) {

            for (int i = 0; i < options.length; i++) {
                if(options[i] == this)
                    return true;
            }

            return false;
        }
    }
}
