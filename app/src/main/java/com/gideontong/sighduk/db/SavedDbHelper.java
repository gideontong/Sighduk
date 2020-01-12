package com.gideontong.sighduk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedDbHelper extends SQLiteOpenHelper {
    public SavedDbHelper(Context context) {
        super(context, SavedContract.DB_NAME, null, ShowContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + SavedContract.SavedEntry.TABLE + " ( " +
                SavedContract.SavedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SavedContract.SavedEntry.COL_SHOW_TITLE + " TEXT NOT NULL, "  +
                SavedContract.SavedEntry.COL_SHOW_IMAGE_URL + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }
}
