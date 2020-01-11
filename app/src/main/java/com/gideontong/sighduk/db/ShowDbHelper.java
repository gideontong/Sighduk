package com.gideontong.sighduk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShowDbHelper extends SQLiteOpenHelper {
    public ShowDbHelper(Context context) {
        super(context, ShowContract.DB_NAME, null, ShowContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + ShowContract.ShowEntry.TABLE + " ( " +
                ShowContract.ShowEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShowContract.ShowEntry.COL_SHOW_TITLE + " TEXT NOT NULL, " +
                ShowContract.ShowEntry.COL_SHOW_IMAGE_URL + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.ShowEntry.TABLE);
        onCreate(db);
    }
}
