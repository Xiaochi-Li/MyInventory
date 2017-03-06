package com.example.android.myinventory.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.myinventory.Data.ProductContract.ProductEntry;

import static com.example.android.myinventory.Data.ProductContract.ProductEntry.TABLE_NAME;

public class ProductSQLightOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ProductSQLightOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                        ProductEntry.COLUMN_PRODUCT_PIRCE + " INTEGER NOT NULL DEFAULT 0, " +
                        ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                        ProductEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT NOT NULL, "+
                        ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL + " TEXT NOT NULL, " +
                        ProductEntry.COLUMN_PRODUCT_IMAGE + " BLOB)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
