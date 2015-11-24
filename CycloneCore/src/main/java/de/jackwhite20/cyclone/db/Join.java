package de.jackwhite20.cyclone.db;

/**
 * Created by JackWhite20 on 13.11.2015.
 *
 * Represents a SQL JOIN statement with the table to join on, the column and a value or other column.
 */
public class Join {

    private String joinTable;

    private String onColumn;

    private String onValue;

    /**
     * Creates a new SQL JOIN statement with the given values.
     *
     * @param joinTable the table.
     * @param onColumn the column.
     * @param onValue the value.
     */
    public Join(String joinTable, String onColumn, String onValue) {

        this.joinTable = joinTable;
        this.onColumn = onColumn;
        this.onValue = onValue;
    }

    /**
     * Gets the table.
     *
     * @return the table.
     */
    public String joinTable() {

        return joinTable;
    }

    /**
     * Gets the column.
     *
     * @return the column.
     */
    public String onColumn() {

        return onColumn;
    }

    /**
     * Gets the value or other column.
     *
     * @return the value/column.
     */
    public String onValue() {

        return onValue;
    }
}
