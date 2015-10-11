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

import de.jackwhite20.cyclone.db.serialization.annotation.Column;
import de.jackwhite20.cyclone.db.serialization.annotation.Table;

/**
 * Created by JackWhite20 on 13.08.2015.
 */
@Table(name = "test", options = { Table.Option.CREATE_IF_NOT_EXISTS })
public class TestTable {

    @Column(options = { Column.Option.UNIQUE, Column.Option.PRIMARY_KEY, Column.Option.AUTO_INCREMENT })
    private int id;

    @Column(name = "custom_name", options = { Column.Option.NOT_NULL })
    private String name;

    @Column(name = "custom_uuid")
    private String uuid;

    public TestTable() {

    }

    public TestTable(int id, String name, String uuid) {

        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "TestTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
