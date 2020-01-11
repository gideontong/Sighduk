package com.gideontong.sbhacks2020.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TokenDbHelper extends SQLiteOpenHelper {
    public TokenDbHelper(Context context) {
        super(context, TokenData.DB_NAME, null, TokenData.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TokenData.TokenEntry.TABLE + " ( " +
                TokenData.TokenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TokenData.TokenEntry.COL_TOKEN_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TokenData.TokenEntry.TABLE);
        onCreate(db);
    }
}
