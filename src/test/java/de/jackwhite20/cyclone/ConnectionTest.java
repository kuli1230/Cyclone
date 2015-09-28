package de.jackwhite20.cyclone;/*
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

import de.jackwhite20.cyclone.builder.create.CreateQuery;
import de.jackwhite20.cyclone.builder.delete.DeleteQuery;
import de.jackwhite20.cyclone.builder.drop.DropQuery;
import de.jackwhite20.cyclone.builder.insert.InsertQuery;
import de.jackwhite20.cyclone.builder.select.SelectQuery;
import de.jackwhite20.cyclone.builder.update.UpdateQuery;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class ConnectionTest {

    public static void main(String[] args) {

        Cyclone cyclone = new Cyclone(new CycloneSettings.Builder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .poolSize(10)
                .build());

        cyclone.connect();

        System.out.println("Connected!");

        try {
            cyclone.create(new CreateQuery.Builder()
                    .create("test")
                    .primaryKey("id")
                    .values("id int auto_increment", "name varchar(255)", "uuid varchar(255)")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 2; i++) {
                cyclone.insert(new InsertQuery.Builder()
                        .into("test")
                        .values("0", "Jack", "000000-000000-000000")
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectAll(cyclone);

        System.out.println("Changing...");

        try {
            cyclone.update(new UpdateQuery.Builder()
                    .update("test")
                    .set("name", "Jacky")
                    .set("uuid", "0000")
                    .where("id", "1")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectAll(cyclone);

        System.out.println("Deleting...");

        try {
            cyclone.delete(new DeleteQuery.Builder().from("test").where("id", "1").build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectAll(cyclone);

        try {
            cyclone.drop(new DropQuery.Builder()
                    .drop("test")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Finished!");
    }

    private static void selectAll(Cyclone cyclone) {

/*        try {

            List<Long> times = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                long start = System.currentTimeMillis();

                DBResult dbResult = cyclone.select(new SelectQuery.Builder()
                        .select("*")
                        .from("test")
                        .build());

                for (DBRow row : dbResult.rows()) {
                    System.out.println("ID: " + row.get("id") + " Name: " + row.get("name") + " UUID: " + row.get("uuid"));
                }
                dbResult.close();

                times.add(System.currentTimeMillis() - start);
            }

            long total = 0;
            for (Long time : times) {
                total += time;
            }
            System.out.println("Average: " + total / times.size());


*//*            ResultSet set = dbResult.resultSet();
            while (set.next()) {
                System.out.println("ID: " + set.getInt("id") +
                        " Name: " + set.getString("name") +
                        " UUID: " + set.getString("uuid"));
            }*//*

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            List<TestTable> result = cyclone.select(new SelectQuery.Builder()
                    .select("*")
                    .from("test")
                    .limit(2)
                    .build(), TestTable.class);
            result.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
