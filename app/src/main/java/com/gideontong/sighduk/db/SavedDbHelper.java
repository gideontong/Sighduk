package com.gideontong.sighduk.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedDbHelper extends SQLiteOpenHelper {
    public SavedDbHelper(Context context) {
        super(context, SavedContract.DB_NAME, null, ShowContract.DB_VERSION);
    }
}
