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

package de.jackwhite20.cyclone.builder.select;

import de.jackwhite20.cyclone.db.serialization.Condition;
import de.jackwhite20.cyclone.query.core.SelectQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by JackWhite20 on 13.08.2015.
 */
public class SelectQueryTest {

    @Test
    public void testSelectQueryAll() {

        String expected = "SELECT * FROM test;";
        String actual = new SelectQuery.Builder()
                .select("*")
                .from("test")
                .build().sql();

        assertEquals(expected, actual);
    }

    @Test
    public void testSelectQueryName() {

        String expected = "SELECT name FROM test;";
        String actual = new SelectQuery.Builder()
                .select("name")
                .from("test")
                .build().sql();

        assertEquals(expected, actual);
    }

    @Test
    public void testSelectQueryLimit() {

        String expected = "SELECT name FROM test LIMIT 2;";
        String actual = new SelectQuery.Builder()
                .select("name")
                .from("test")
                .limit(2)
                .build().sql();

        assertEquals(expected, actual);
    }

    @Test
    public void testSelectQueryOperator() {

        String expected = "SELECT name FROM test WHERE id>=?;";
        String actual = new SelectQuery.Builder()
                .select("name")
                .from("test")
                .where(new Condition("id", Condition.Operator.GREATER_EQUAL, "1"))
                .build().sql();

        assertEquals(expected, actual);
    }
}
