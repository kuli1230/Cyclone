package de.jackwhite20.cyclone.db;

/**
 * Created by JackWhite20 on 23.11.2015.
 */
public class Function {

    private Type type;

    private String column;

    private String as;

    public Function(Type type, String column, String as) {

        this.type = type;
        this.column = column;
        this.as = as;
    }

    public Function(Type type, String column) {

        this(type, column, null);
    }

    public Type type() {

        return type;
    }

    public String column() {

        return column;
    }

    public String as() {

        return as;
    }

    public enum Type {

        SUM("SUM"), AVG("AVG"), MIN("MIN"), MAX("MAX"), COUNT("COUNT");

        private String sql;

        Type(String sql) {

            this.sql = sql;
        }

        public String sql() {

            return sql;
        }
    }
}
