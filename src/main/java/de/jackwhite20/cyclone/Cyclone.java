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

package de.jackwhite20.cyclone;

import de.jackwhite20.cyclone.builder.create.CreateQuery;
import de.jackwhite20.cyclone.builder.drop.DropQuery;
import de.jackwhite20.cyclone.db.DBConnection;
import de.jackwhite20.cyclone.db.settings.DBConnectionSettings;

import java.sql.SQLException;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class Cyclone {

    private DBConnection connection;

    public Cyclone() {

    }

    public void connect(DBConnectionSettings settings) {

        connection = new DBConnection(settings);
    }

    public void create(CreateQuery query) throws SQLException {

        connection.getConnection().prepareStatement(query.toString()).execute();
    }

    public void drop(DropQuery query) throws SQLException {

        connection.getConnection().prepareStatement(query.toString()).execute();
    }
}
