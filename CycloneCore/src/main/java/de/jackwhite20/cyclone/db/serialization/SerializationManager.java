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

package de.jackwhite20.cyclone.db.serialization;

import de.jackwhite20.cyclone.Cyclone;
import de.jackwhite20.cyclone.query.core.CreateQuery;
import de.jackwhite20.cyclone.query.core.DeleteQuery;
import de.jackwhite20.cyclone.query.core.DropQuery;
import de.jackwhite20.cyclone.query.core.InsertQuery;
import de.jackwhite20.cyclone.query.core.SelectQuery;
import de.jackwhite20.cyclone.query.core.UpdateQuery;
import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.DBRow;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.serialization.annotation.Column;
import de.jackwhite20.cyclone.db.serialization.annotation.Table;
import de.jackwhite20.cyclone.db.serialization.exception.SQLSerializationException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JackWhite20 on 11.10.2015.
 */
public class SerializationManager {

    private final Cyclone cyclone;

    public SerializationManager(Cyclone cyclone) {

        this.cyclone = cyclone;
    }

    public boolean create(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);
        Table.Option[] options = table.options();

        CreateQuery.Builder builder = new CreateQuery.Builder();
        builder.create(table.name());

        if(Table.Option.CREATE_IF_NOT_EXISTS.isOption(options))
            builder.ifNotExists(true);

        List<String> primaryKeys = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                Column.Option[] rowOptions = column.options();
                String name = column.name().equals("") ? field.getName() : column.name();

                Column.Type type = Column.Type.typeFromClass(field.getType());

                if (type == Column.Type.UNKNOWN)
                    continue;

                if (Column.Option.PRIMARY_KEY.isOption(rowOptions))
                    primaryKeys.add(name);

                List<String> rowOptionStrings = new ArrayList<>();
                rowOptionStrings.add(type.sqlName());
                for (int i = 0; i < rowOptions.length; i++) {
                    Column.Option option = rowOptions[i];
                    if (option == Column.Option.PRIMARY_KEY)
                        continue;

                    if(cyclone.type() == Type.SQLITE && option == Column.Option.AUTO_INCREMENT)
                        continue;

                    rowOptionStrings.add(option.sql());
                }

                builder.value(name, rowOptionStrings.toArray(new String[rowOptionStrings.size()]));
            }
        }

        for (String primaryKey : primaryKeys) {
            builder.primaryKey(primaryKey);
        }

        return cyclone.execute(builder.build());
    }

    public <T> List<T> select(Class<T> clazz, int limit, Order order, Condition... conditions) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);

        SelectQuery.Builder builder = new SelectQuery.Builder();

        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String name = column.name().equals("") ? field.getName() : column.name();
                builder.select(name);
            }
        }

        builder.from(table.name());

        if(limit > 0)
            builder.limit(limit);

        for (Condition condition : conditions) {
            builder.where(condition.column(), condition.operator().sql(), condition.value());
        }

        if(order != null)
            builder.orderBy(order.row() + " " + order.type());

        DBResult result = cyclone.query(builder.build());

        List<T> resultList = new ArrayList<>();

        try {
            for (DBRow row : result.rows()) {
                T classInstance = clazz.newInstance();

                for (Field field : classInstance.getClass().getDeclaredFields()) {
                    if(field.isAnnotationPresent(Column.class)) {
                        if(Modifier.isFinal(field.getModifiers()))
                            continue;

                        Column column = field.getAnnotation(Column.class);
                        String name = column.name().equals("") ? field.getName() : column.name();

                        if(!row.hasKey(name))
                            continue;

                        field.setAccessible(true);
                        field.set(classInstance, row.getObject(name));
                        field.setAccessible(false);
                    }
                }

                resultList.add(classInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return resultList;
    }

    public <T> List<T> select(Class<T> clazz, Order order, Condition... conditions) {

        return select(clazz, -1, order, conditions);
    }

    public <T> List<T> select(Class<T> clazz, int limit, Condition... conditions) {

        return select(clazz, limit, null, conditions);
    }

    public <T> List<T> select(Class<T> clazz, Condition... conditions) {

        return select(clazz, -1, null, conditions);
    }

    public boolean drop(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);

        return cyclone.execute(new DropQuery.Builder().drop(table.name()).build());
    }

    public boolean insert(Object object) {

        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);

        InsertQuery.Builder builder = new InsertQuery.Builder();
        builder.into(table.name());

        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String name = column.name().equals("") ? field.getName() : column.name();

                    if(cyclone.type() == Type.SQLITE && Column.Option.AUTO_INCREMENT.isOption(column.options()))
                        continue;

                    builder.column(name);
                    field.setAccessible(true);
                    builder.value(field.get(object).toString());
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return cyclone.execute(builder.build());
    }

    public boolean delete(Class<?> clazz, Condition... conditions) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);

        DeleteQuery.Builder builder = new DeleteQuery.Builder();
        builder.from(table.name());

        for (Condition condition : conditions) {
            builder.where(condition.column(), condition.operator().sql(), condition.value());
        }

        return cyclone.execute(builder.build());
    }

    public boolean update(Object object, String[] setColumns, Condition... conditions) {

        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new SQLSerializationException("Table annotation is not present!");
        }

        Table table = clazz.getAnnotation(Table.class);

        UpdateQuery.Builder builder = new UpdateQuery.Builder();
        builder.update(table.name());

        HashMap<String, String> primaryKeys = new HashMap<>();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                if(field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    Column.Option[] columnOptions = column.options();
                    String name = column.name().equals("") ? field.getName() : column.name();

                    if(Column.Option.PRIMARY_KEY.isOption(columnOptions)) {
                        field.setAccessible(true);
                        primaryKeys.put(name, field.get(object).toString());
                        field.setAccessible(false);
                        continue;
                    }

                    if(setColumns != null) {
                        boolean contains = false;

                        for (int i = 0; i < setColumns.length; i++) {
                            if (setColumns[i].equals(name)) {
                                contains = true;
                            }
                        }

                        if(!contains)
                            continue;
                    }

                    field.setAccessible(true);
                    builder.set(name, field.get(object).toString());
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if(conditions.length == 0) {
            for (Map.Entry<String, String> entry : primaryKeys.entrySet()) {
                builder.where(entry.getKey(), entry.getValue());
            }
        }else {
            for (Condition condition : conditions) {
                builder.where(condition.column(), condition.operator().sql(), condition.value());
            }
        }

        return cyclone.execute(builder.build());
    }

    public boolean update(Object object, Condition... conditions) {

        return update(object, null, conditions);
    }
}
