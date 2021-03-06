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

package de.jackwhite20.cyclone.db.serialization.exception;

/**
 * Created by JackWhite20 on 10.10.2015.
 *
 * Represents an exception that gets threw when a serialization error occurs.
 */
public class SQLSerializationException extends RuntimeException {

    static final long serialVersionUID = -1654564534348664454L;

    /**
     * Creates an SQLSerializationException instance with the given message.
     *
     * @param message the message.
     */
    public SQLSerializationException(String message) {

        super(message);
    }
}
