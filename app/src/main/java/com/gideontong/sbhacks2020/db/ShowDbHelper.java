package com.gideontong.sbhacks2020.db;

import android.content.Context;

public class ShowDbHelper {
    public TaskDbHelper(Context context) {
        super(context, ShowContract.DB_NAME, null, ShowContract.DB_VERSION);
    }
}
