package com.example.android.stockhawk.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Peretz on 2016-08-10.
 */
@Database(version=QuoteDatabase.VERSION)
public class QuoteDatabase {
    private QuoteDatabase(){}

    public static final int VERSION = 7;

    @Table(QuoteColumns.class) public static final String QUOTES = "quotes";
}
