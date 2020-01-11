package com.gideontong.sbhacks2020.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShowDbHelper extends SQLiteOpenHelper {
    public ShowDbHelper(Context context) {
        super(context, ShowContract.DB_NAME, null, ShowContract.DB_VERSION);
    }
}
