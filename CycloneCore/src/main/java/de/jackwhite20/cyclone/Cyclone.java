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

import de.jackwhite20.cyclone.async.CycloneDispatcher;
import de.jackwhite20.cyclone.async.DispatcherConsumer;
import de.jackwhite20.cyclone.query.CustomQuery;
import de.jackwhite20.cyclone.query.Query;
import de.jackwhite20.cyclone.db.CycloneConnection;
import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.serialization.SerializationManager;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Future;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class Cyclone {

    /**
     * The settings.
     */
    private final CycloneSettings settings;

    /**
     * The connection.
     */
    private CycloneConnection connection;

    /**
     * The serialization manager for working with classes.
     */
    private final SerializationManager serializationManager;

    /**
     * The dispatcher for async executions.
     */
    private final CycloneDispatcher cycloneDispatcher;

    /**
     * Create a new Cyclone object.
     *
     * @param settings the connection settings.
     */
    public Cyclone(CycloneSettings settings) {

        this.settings = settings;
        this.serializationManager = new SerializationManager(this);
        this.cycloneDispatcher = new CycloneDispatcher();
    }

    /**
     * Connects to the mysql server.
     */
    public void connect() {

        connection = new CycloneConnection(settings);
    }

    /**
     * Closes Cyclone and the async dispatcher.
     */
    public void close() {

        cycloneDispatcher.close();
        connection.close();
    }

    /**
     * Dispatches an operation async with a callback.
     *
     * @param consumer the DispatcherConsumer callback.
     */
    public void dispatch(DispatcherConsumer consumer) {

        cycloneDispatcher.dispatch(consumer);
    }

    /**
     * Submits an operation async with a callback and gets the operation future.
     *
     * @param consumer the DispatcherConsumer callback.
     * @return the Future of the operation.
     */
    public Future<?> submit(DispatcherConsumer consumer) {

        return cycloneDispatcher.submit(consumer);
    }

    /**
     * Executes a SQL raw query.
     *
     * @param query the SQL raw query.
     */
    public boolean execute(String query) {

        return execute(new CustomQuery(query));
    }

    /**
     * Executes a Query as prepared SQL statement.
     *
     * @param query the Query.
     */
    public boolean execute(Query query) {

        try (Connection con = connection.getConnection()) {
            PreparedStatement preparedStatement = query.prepareStatement(con);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            if(settings.printExceptions())
                e.printStackTrace();

            return false;
        }
    }

    /**
     * Queries a prepared statement and returns the result as DBResult.
     *
     * @param query the query.
     * @return the result as DBResult.
     */
    public DBResult query(Query query) {

        try (Connection con = connection.getConnection()) {
            PreparedStatement preparedStatement = query.prepareStatement(con);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new DBResult(resultSet, preparedStatement);
        } catch (SQLException e) {
            if(settings.printExceptions())
                e.printStackTrace();

            return null;
        }
    }

    /**
     * Queries an SQL string query and returns the result as DBResult.
     *
     * @param query the SQL string query.
     * @return the result as DBResult.
     */
    public DBResult query(String query) {

        return query(new CustomQuery(query));
    }

    public Type type() {

        return settings.type();
    }

    /**
     * Gets the serialization manager.
     *
     * @return the SerializationManager.
     */
    public SerializationManager serializationManager() {

        return serializationManager;
    }
}
