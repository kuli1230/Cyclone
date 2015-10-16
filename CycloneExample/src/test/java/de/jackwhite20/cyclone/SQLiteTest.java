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

import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.DBRow;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;
import de.jackwhite20.cyclone.query.core.*;

import java.util.List;

/**
 * Created by JackWhite20 on 29.09.2015.
 */
public class SQLiteTest {

    public static void main(String[] args) {

        Cyclone cyclone = new Cyclone(new CycloneSettings.Builder().database("data/test.db").type(Type.SQLITE).build());
        cyclone.connect();

        cyclone.execute(new CreateQuery.Builder().create("test").value("id", "INTEGER").value("name", "STRING").primaryKey("id").build());

        for (int i = 0; i < 6; i++) {
            cyclone.execute(new InsertQuery.Builder().into("test").columns("name").values("Peter" + i).build());
        }

        select(cyclone);

        cyclone.execute(new DeleteQuery.Builder().from("test").where("id", ">", "4").build());

        select(cyclone);

        cyclone.execute(new DropQuery.Builder().drop("test").build());
    }

    private static void select(Cyclone lite) {

        DBResult result = lite.query(new SelectQuery.Builder().from("test").select("*").build());
        System.out.println("Selected:");

        List<DBRow> row = result.rows();
        for (DBRow dbRow : row) {
            System.out.println(dbRow.get("id") + ":" + dbRow.get("name"));
        }
    }
}
