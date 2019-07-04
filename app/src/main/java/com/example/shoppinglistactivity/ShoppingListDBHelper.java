package com.example.shoppinglistactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingListDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shoppingList.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "shoppingList";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public ShoppingListDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ShoppingListTable.ShoppingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";

        Log.d("Woody: ", SQL_CREATE_SHOPPINGLIST_TABLE);

        db.execSQL(SQL_CREATE_SHOPPINGLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingListTable.ShoppingListEntry.TABLE_NAME);
        onCreate(db);

    }
}
