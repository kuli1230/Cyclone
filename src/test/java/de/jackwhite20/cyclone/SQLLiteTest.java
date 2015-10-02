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
import de.jackwhite20.cyclone.builder.delete.DeleteQuery;
import de.jackwhite20.cyclone.builder.drop.DropQuery;
import de.jackwhite20.cyclone.builder.insert.InsertQuery;
import de.jackwhite20.cyclone.builder.select.SelectQuery;
import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.DBRow;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by JackWhite20 on 29.09.2015.
 */
public class SQLLiteTest {

    public static void main(String[] args) {

        Cyclone cyclone = new Cyclone(new CycloneSettings.Builder().database("data/test.db").type(Type.SQLITE).build());
        cyclone.connect();

        try {
            cyclone.create(new CreateQuery.Builder().create("test").values("id INTEGER", "name STRING").primaryKey("id").build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            try {
                cyclone.insert(new InsertQuery.Builder().into("test").columns("name").values("Peter" + i).build());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        select(cyclone);

        try {
            cyclone.delete(new DeleteQuery.Builder().from("test").where("id", ">", "4").build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        select(cyclone);

        try {
            cyclone.drop(new DropQuery.Builder().drop("test").build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void select(Cyclone lite) {

        try {
            DBResult result = lite.select(new SelectQuery.Builder().from("test").select("*").build());
            System.out.println("Selected:");

            List<DBRow> row = result.rows();
            for (DBRow dbRow : row) {
                System.out.println(dbRow.get("id") + ":" + dbRow.get("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
