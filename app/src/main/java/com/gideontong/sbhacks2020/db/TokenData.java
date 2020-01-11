package com.gideontong.sbhacks2020.db;

import android.provider.BaseColumns;

public class TokenData {
    public static final String DB_NAME = "com.gideontong.sbhacks2020.db";
    public static final int DB_VERSION = 1;

    public class TokenEntry implements BaseColumns {
        public static final String TABLE = "tokens";
        public static final String COL_TOKEN_TITLE = "key";
    }
}
