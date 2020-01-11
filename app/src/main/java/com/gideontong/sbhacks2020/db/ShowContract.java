package com.gideontong.sbhacks2020.db;

import android.provider.BaseColumns;

public class ShowContract {
    public static final String DB_NAME = "com.gideontong.sbhacks2020.db";
    public static final int DB_VERSION = 1;

    public class ShowEntry implements BaseColumns {
        public static final String TABLE = "shows";
        public static final String COL_TASK_TITLE = "title";
    }
}
