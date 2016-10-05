package com.example.android.stockhawk.data;

import android.renderscript.Element;

/**
 * Created by Peretz on 2016-08-10.
 */
public class QuoteColumns {
    @DataType(Element.DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = "_id";
    @DataType(Element.DataType.Type.TEXT) @NotNull
    public static final String SYMBOL = "symbol";
    @DataType(Element.DataType.Type.TEXT) @NotNull
    public static final String PERCENT_CHANGE = "percent_change";
    @DataType(Element.DataType.Type.TEXT) @NotNull
    public static final String CHANGE = "change";
    @DataType(Element.DataType.Type.TEXT) @NotNull
    public static final String BIDPRICE = "bid_price";
    @DataType(Element.DataType.Type.TEXT)
    public static final String CREATED = "created";
    @DataType(Element.DataType.Type.INTEGER) @NotNull
    public static final String ISUP = "is_up";
    @DataType(Element.DataType.Type.INTEGER) @NotNull
    public static final String ISCURRENT = "is_current";
}
