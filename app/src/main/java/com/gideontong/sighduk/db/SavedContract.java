package com.gideontong.sighduk.db;

import android.provider.BaseColumns;

public class SavedContract {
    public static final String DB_NAME = "com.gideontong.sighduk.db.saved";
    public static final int DB_VERSION = 1;

    public class SavedEntry implements BaseColumns {
        public static final String TABLE = "shows";
        public static final String COL_SHOW_TITLE = "title";
        public static final String COL_SHOW_IMAGE_URL = "imageuri";
    }
}
