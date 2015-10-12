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

package de.jackwhite20.example.builder;

import de.jackwhite20.cyclone.Cyclone;
import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.DBRow;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;
import de.jackwhite20.cyclone.query.core.*;
import de.jackwhite20.example.Example;

import java.io.File;
import java.util.List;

/**
 * Created by JackWhite20 on 12.10.2015.
 *
 * Represents an example usage for SQLite and Cyclones builder system.
 */
public class SQLiteExample implements Example {

    /**
     * The Cyclone instance.
     */
    private Cyclone cyclone;

    @Override
    public void setup(String host, int port, String user, String password) {

        // Important when you want to use SQLite is that you need to specify the type
        // The other thing to remember is tha the database name is now the path to the .db file
        cyclone = new Cyclone(new CycloneSettings.Builder()
                .database("data/test.db")
                .type(Type.SQ_LITE)
                .build());

        cyclone.connect();
    }

    @Override
    public void execute() {

        System.out.println("Creating table..");
        cyclone.execute(new CreateQuery.Builder().create("test").value("id", "INTEGER").value("name", "STRING").primaryKey("id").build());

        System.out.println("Inserting data..");
        for (int i = 0; i < 6; i++) {
            cyclone.execute(new InsertQuery.Builder().into("test").columns("name").values("Peter" + i).build());
        }

        select(cyclone);

        System.out.println("Deleting data..");
        cyclone.execute(new DeleteQuery.Builder().from("test").where("id", ">", "4").build());

        select(cyclone);

        System.out.println("Dropping table..");
        cyclone.execute(new DropQuery.Builder().drop("test").build());

        cyclone.close();

        new File("data/test.db").delete();
        new File("data").delete();
    }

    private void select(Cyclone lite) {

        DBResult result = lite.query(new SelectQuery.Builder().from("test").select("*").build());
        System.out.println("Selecting..");

        List<DBRow> rows = result.rows();
        for (DBRow dbRow : rows) {
            System.out.println(dbRow.get("id") + ":" + dbRow.get("name"));
        }
    }
}
