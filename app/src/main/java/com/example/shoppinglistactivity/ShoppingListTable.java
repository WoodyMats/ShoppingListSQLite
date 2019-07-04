package com.example.shoppinglistactivity;

import android.provider.BaseColumns;

public class ShoppingListTable {
    private ShoppingListTable(){}

    public static final class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shoppingList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
