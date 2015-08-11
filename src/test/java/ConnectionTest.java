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

import de.jackwhite20.cyclone.Cyclone;
import de.jackwhite20.cyclone.builder.create.CreateQuery;
import de.jackwhite20.cyclone.builder.drop.DropQuery;
import de.jackwhite20.cyclone.builder.insert.InsertQuery;
import de.jackwhite20.cyclone.db.settings.DBConnectionSettings;

import java.sql.SQLException;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class ConnectionTest {

    public static void main(String[] args) {

        Cyclone cyclone = new Cyclone();

        cyclone.connect(new DBConnectionSettings.SettingsBuilder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .build());

        try {
            cyclone.create(new CreateQuery.CreateQueryBuilder()
                    .create("test")
                    .ifNotExists(true)
                    .primaryKey("id")
                    .values("id int auto_increment", "name varchar(255)", "uuid varchar(255)")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < 50; i++) {
                cyclone.insert(new InsertQuery.InsertQueryBuilder()
                        .into("test")
                        .values("0", "Jack", "000000-000000-000000")
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            cyclone.drop(new DropQuery.DropQueryBuilder().drop("test").build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
