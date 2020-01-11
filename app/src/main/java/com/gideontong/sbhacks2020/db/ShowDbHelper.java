package com.gideontong.sbhacks2020.db;

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
                ShowContract.ShowEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }
}
