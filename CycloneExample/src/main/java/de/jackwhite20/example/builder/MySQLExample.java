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
import de.jackwhite20.cyclone.db.Function;
import de.jackwhite20.cyclone.db.serialization.Condition;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;
import de.jackwhite20.cyclone.query.core.*;
import de.jackwhite20.example.Example;

import java.util.UUID;

/**
 * Created by JackWhite20 on 11.10.2015.
 *
 * Represents an example usage for MySQL and Cyclones builder system.
 */
public class MySQLExample implements Example {

    /**
     * The Cyclone instance.
     */
    private Cyclone cyclone;

    @Override
    public void setup(String host, int port, String user, String password) {

        // The default Cyclone database type is MYSQL so you don't need to set it especially
        cyclone = new Cyclone(new CycloneSettings.Builder()
                .host(host)
                .port(port)
                .user(user)
                .password(password)
                .database("test")
                .poolSize(10)
                .poolName("Cyclone-Test")
                .build());

        cyclone.connect();

        System.out.println("Connected!");
    }

    @Override
    public void execute() {

        cyclone.execute(new CreateQuery.Builder()
                .create("test")
                .primaryKey("id")
                .value("id", "int", "auto_increment")
                .value("name", "varchar(255)")
                .value("uuid", "varchar(255)")
                .build());

        for (int i = 0; i < 2; i++) {
            cyclone.execute(new InsertQuery.Builder()
                    .into("test")
                    .values("0", "Jack", UUID.randomUUID().toString())
                    .build());
        }

        selectAll(cyclone);

        System.out.println("Changing...");

        cyclone.execute(new UpdateQuery.Builder()
                .update("test")
                .set("name", "Jacky")
                .set("uuid", "0000")
                .where(new Condition("id", Condition.Operator.EQUAL, "1"))
                .build());

        selectAll(cyclone);

        System.out.println("Deleting...");

        // Sync
        cyclone.execute(new DeleteQuery.Builder().from("test").where(new Condition("id", Condition.Operator.EQUAL, "1")).build());

        // Async
        //cyclone.dispatch(() -> cyclone.execute(new DeleteQuery.Builder().from("test").where(new Condition("id", Condition.Operator.EQUAL, "1")).build()));

        // Function example
        DBResult result = cyclone.query(new SelectQuery.Builder().select("*").from("test").function(new Function(Function.Type.AVG, "id", "average")).build());
        System.out.println("Average from 'id': " + result.row(0).get("average"));

        // SQL JOIN usage example
        //cyclone.query(new SelectQuery.Builder().select("*").from("test").join(new Join("test_other", "test_other.id", "test.id")).build());

        selectAll(cyclone);

        cyclone.execute(new DropQuery.Builder()
                .drop("test")
                .build());

        cyclone.close();
    }

    private void selectAll(Cyclone cyclone) {

        DBResult result = cyclone.query(new SelectQuery.Builder()
                .select("*")
                .from("test")
                .build());

        for (DBRow row : result.rows()) {
            int id = row.get("id");
            String customName = row.get("name");
            String uuid = row.get("uuid");
            System.out.println(id + " : " + customName + " : " + uuid);
        }
    }
}
