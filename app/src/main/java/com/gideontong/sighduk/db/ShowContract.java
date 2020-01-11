package com.gideontong.sighduk.db;

import android.provider.BaseColumns;

public class ShowContract {
    public static final String DB_NAME = "com.gideontong.sbhacks2020.db";
    public static final int DB_VERSION = 2;

    public class ShowEntry implements BaseColumns {
        public static final String TABLE = "shows";
        public static final String COL_SHOW_TITLE = "title";
        public static final String COL_SHOW_IMAGE_URL = "imageuri";
    }
}
