package com.example.calendar;

import android.provider.BaseColumns;

public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " integer";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String Title_NAME="Title";
        public static final String KEY_S_HOUR = "start_hour";
        public static final String KEY_S_MIN = "start_min";
        public static final String KEY_E_HOUR = "end_hour";
        public static final String KEY_E_MIN = "end_min";
        public static final String KEY_PLACE = "place";
        public static final String KEY_MEMO = "memo";



        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                Title_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_S_HOUR + INT_TYPE + COMMA_SEP +
                KEY_S_MIN + INT_TYPE + COMMA_SEP +
                KEY_E_HOUR + INT_TYPE + COMMA_SEP +
                KEY_E_MIN + INT_TYPE + COMMA_SEP +
                KEY_PLACE + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
