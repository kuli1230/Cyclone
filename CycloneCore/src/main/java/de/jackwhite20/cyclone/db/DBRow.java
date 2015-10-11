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

package de.jackwhite20.cyclone.db;

import java.util.HashMap;

/**
 * Created by JackWhite20 on 23.08.2015.
 *
 * Represents a table row and it's columns.
 */
public class DBRow {

    /**
     * All values from the row in key-value format.
     */
    private HashMap<String, Object> values = new HashMap<>();

    /**
     * Adds a key and the corresponding value to the hashmap.
     *
     * @param key the key.
     * @param value the value.
     */
    public void add(String key, Object value) {

        values.put(key, value);
    }

    /**
     * Gets the value from the given key.
     *
     * @param key the key.
     * @return the value from the key.
     */
    public <T> T get(String key) {

        return (T) values.get(key);
    }

    /**
     * Gets an Object with the given key.
     *
     * @param key the key.
     * @return the Object.
     */
    public Object getObject(String key) {

        return values.get(key);
    }

    /**
     * Checks if the row has the key as column.
     *
     * @param key the key.
     * @return true if it contains the key otherwise false.
     */
    public boolean hasKey(String key) {

        return values.containsKey(key);
    }
}
