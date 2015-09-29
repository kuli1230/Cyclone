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

package de.jackwhite20.cyclone.db.settings;

import de.jackwhite20.cyclone.db.Type;

/**
 * Created by JackWhite20 on 11.08.2015.
 */
public class CycloneSettings {

    private String host = "localhost";

    private int port = 3306;

    private String user = "root";

    private String password = "";

    private String database = "db";

    private int poolSize = 10;

    private Type type = Type.MY_SQL;

    private String poolName;

    public CycloneSettings() {

    }

    public CycloneSettings(String host, int port, String user, String password, String database, int poolSize, Type type, String poolName) {

        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.poolSize = poolSize;
        this.type = type;
        this.poolName = poolName;
    }

    public String getHost() {

        return host;
    }

    public int getPort() {

        return port;
    }

    public String getUser() {

        return user;
    }

    public String getPassword() {

        return password;
    }

    public String getDatabase() {

        return database;
    }

    public int getPoolSize() {

        return poolSize;
    }

    public Type getType() {

        return type;
    }

    public String getPoolName() {

        return poolName;
    }

    public static class Builder {

        private CycloneSettings settings = new CycloneSettings();

        public Builder host(String host) {

            settings.host = host;

            return this;
        }

        public Builder port(int port) {

            settings.port = port;

            return this;
        }

        public Builder user(String user) {

            settings.user = user;

            return this;
        }

        public Builder password(String password) {

            settings.password = password;

            return this;
        }

        public Builder database(String database) {

            settings.database = database;

            return this;
        }

        public Builder poolSize(int size) {

            settings.poolSize = size;

            return this;
        }

        public Builder type(Type type) {

            settings.type = type;

            return this;
        }

        public Builder poolName(String name) {

            settings.poolName = name;

            return this;
        }

        public CycloneSettings build() {

            return settings;
        }
    }
}
