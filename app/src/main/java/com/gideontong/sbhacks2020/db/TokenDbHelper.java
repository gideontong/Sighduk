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
    }
}
