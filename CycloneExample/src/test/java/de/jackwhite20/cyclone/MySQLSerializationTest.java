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

import de.jackwhite20.cyclone.db.serialization.Order;
import de.jackwhite20.cyclone.db.serialization.SerializationManager;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.util.List;

/**
 * Created by JackWhite20 on 11.10.2015.
 */
public class MySQLSerializationTest {

    public static void main(String[] args) {

        Cyclone cyclone = new Cyclone(new CycloneSettings.Builder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .poolSize(10)
                .poolName("Cyclone-Test")
                .build());

        cyclone.connect();

        //String sql = cyclone.getSerializationManager().create(TestTable.class).sql();
        //System.out.println("Create: " + sql);

/*        try {
            cyclone.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        //String select = cyclone.getSerializationManager().select(TestTable.class, new Condition("id", Condition.Operator.GREATER, "0")).sql();
        //System.out.println("Select: " + select);
/*        cyclone.getSerializationManager().create(TestTable.class, new CycloneConsumer<Boolean>() {
            @Override
            public void accept(Boolean type) {
                System.out.println("Accpeted: " + type);
            }
        });*/


/*            DBResult result = cyclone.select(cyclone.getSerializationManager().select(TestTable.class, new Order("id", Order.Type.ASC)*//*, new Condition("id", Condition.Operator.GREATER, "1")*//*));
            for (DBRow row : result.rows()) {
                System.out.println(row.get("id") + " : " + row.get("custom_name") + " : " + row.get("custom_uuid"));
            }
            result.close();*/

        select(cyclone.serializationManager());
        //select(cyclone.getSerializationManager());

        //cyclone.getSerializationManager().update(new TestTable(1, "LOOOL", "1000"));

/*        for (int i = 0; i < 500; i++) {
            cyclone.dispatch(() -> cyclone.getSerializationManager().insert(new TestTable(0, "Test" + new Random().nextInt(1464664), UUID.randomUUID().toString())));
            //cyclone.getSerializationManager().insert(new TestTable(0, "Test" + new Random().nextInt(1464664), UUID.randomUUID().toString()));
        }*/

/*        System.out.println("Executing:");
        Future<?> future = cyclone.submit(() -> cyclone.getSerializationManager().delete(TestTable.class, new Condition("id", Condition.Operator.GREATER, "6")));

        System.out.println("Waiting for finishing..");
        try {
            future.get();
            System.out.println("GOT");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        //cyclone.getSerializationManager().delete(TestTable.class, new Condition("id", Condition.Operator.GREATER, "6"));

        //cyclone.dispatch(() -> cyclone.getSerializationManager().update(new TestTable(1, "LOOOL", "1000"), new Condition("custom_name", Condition.Operator.EQUAL, "Peter")));

/*        System.out.println("Inserting:");
        TestTable testTable = new TestTable(0, "Olaf", "5");
        cyclone.getSerializationManager().insert(testTable);*/

        //System.out.println("Deleting:");
        //cyclone.getSerializationManager().delete(TestTable.class, new Condition("custom_name", Condition.Operator.EQUAL, "Olaf"));

        select(cyclone.serializationManager());

        //cyclone.getSerializationManager().select(TestTable.class, LImit 2, custom_uuid = 0); new Operator(rowName, operator, value)

        System.out.println("Waiting...");
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Closing..");

        cyclone.close();
    }

    public static void select(SerializationManager serializationManager) {

        System.out.println("Selecting:");
        List<TestTable> results = serializationManager.select(TestTable.class, -1, new Order("id", Order.Type.ASC));
        results.forEach(System.out::println);
    }
}
