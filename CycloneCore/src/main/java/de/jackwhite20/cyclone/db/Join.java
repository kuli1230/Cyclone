package de.jackwhite20.cyclone.db;

/**
 * Created by JackWhite20 on 13.11.2015.
 */
public class Join {

    private String joinTable;

    private String onColumn;

    private String onValue;

    public Join(String joinTable, String onColumn, String onValue) {

        this.joinTable = joinTable;
        this.onColumn = onColumn;
        this.onValue = onValue;
    }

    public String joinTable() {

        return joinTable;
    }

    public String onColumn() {

        return onColumn;
    }

    public String onValue() {

        return onValue;
    }
}
