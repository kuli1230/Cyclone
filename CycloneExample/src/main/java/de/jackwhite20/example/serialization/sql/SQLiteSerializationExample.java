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

package de.jackwhite20.example.serialization.sql;

import de.jackwhite20.cyclone.Cyclone;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.serialization.Condition;
import de.jackwhite20.cyclone.db.serialization.SerializationManager;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;
import de.jackwhite20.example.Example;
import de.jackwhite20.example.serialization.TestTable;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by JackWhite20 on 12.10.2015.
 *
 * Represents an example usage for SQLite and Cyclones serialization system.
 */
public class SQLiteSerializationExample implements Example {

    /**
     * The Cyclone instance.
     */
    private Cyclone cyclone;

    /**
     * The SerializationManager instance.
     */
    private SerializationManager serializationManager;

    @Override
    public void setup(String host, int port, String user, String password) {

        // How to initialize Cyclone
        // Notice that we need to set SQLite as the type
        cyclone = new Cyclone(new CycloneSettings.Builder()
                .database("data/test.db")
                .poolSize(10)
                .poolName("Cyclone-Test")
                .type(Type.SQLITE)
                .build());

        // Connects the Cyclone instance
        cyclone.connect();

        // Gets the serialization manager to use the annotation API
        serializationManager = cyclone.serializationManager();

        System.out.println("Connected!");
    }

    @Override
    public void execute() {

        // Creates the table from the template class TestTable
        // Notice that all methods that are involved with queries are returning an boolean if it was successful
        serializationManager.create(TestTable.class);

        for (int i = 0; i < 10; i++) {
            // Example of an insert method
            serializationManager.insert(new TestTable(0, "Peter" + i, UUID.randomUUID().toString()));
        }

        selectAll(serializationManager);

        // Example of a delete method with a condition
        // Keep in mind that you can apply more than one condition
        serializationManager.delete(TestTable.class, new Condition("other_id", Condition.Operator.GREATER_EQUAL, "6"));

        selectAll(serializationManager);

        // Example of an update method
        serializationManager.update(new TestTable(4, "RandomName", "RandomUUID"));

        selectAll(serializationManager);

        try {
            // Submits an async operation and waits until the operation is finished
            cyclone.submit(() -> serializationManager.delete(TestTable.class, new Condition("other_id", Condition.Operator.LESS_EQUAL, "3"))).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectAll(serializationManager);

        // Executes the drop operation async
        cyclone.dispatch(() -> serializationManager.drop(TestTable.class));

        // Without lambda it looks like this
/*      cyclone.dispatch(new DispatcherConsumer() {
            @Override
            public void execute() {
                serializationManager.drop(TestTable.class);
            }
        });*/

        try {
            // Waits a bit to make sure that the async drop operation is completed
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Closes the connection pool and the async dispatcher
        cyclone.close();

        new File("data/test.db").delete();
        new File("data").delete();
    }

    private void selectAll(SerializationManager serializationManager) {

        System.out.println("Selecting..");

        // Selects everything and prints it out
        List<TestTable> result = serializationManager.select(TestTable.class);
        result.forEach(System.out::println);

        // Other example of a select method from the serialization manager
        //serializationManager.select(TestTable.class, new Condition("other_id", Condition.Operator.GREATER_EQUAL, "4"), new Condition("other_name", Condition.Operator.EQUAL, "Jack"));

        // Other example of a select method with just one order condition
        //serializationManager.select(TestTable.class, new Order("other_id", Order.Type.ASC));
    }
}
