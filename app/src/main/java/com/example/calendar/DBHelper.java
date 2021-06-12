package com.example.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public void insertUserBySQL(String title, int start_hour,int start_min,int end_hour,int end_min,String place, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s) VALUES (NULL, '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.Title_NAME,
                    UserContract.Users.KEY_S_HOUR,
                    UserContract.Users.KEY_S_MIN,
                    UserContract.Users.KEY_E_HOUR,
                    UserContract.Users.KEY_E_MIN,
                    UserContract.Users.KEY_PLACE,
                    UserContract.Users.KEY_MEMO,
                    title, start_hour, start_min, end_hour, end_min,place,memo);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }
}

//public Cursor getAllUsersBySQL() {
//    String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
//    return getReadableDatabase().rawQuery(sql,null);
//}


//    public void deleteUserBySQL(String _id) {
//        try {
//            String sql = String.format (
//                    "DELETE FROM %s WHERE %s = %s",
//                    UserContract.Users.TABLE_NAME,
//                    UserContract.Users._ID,
//                    _id);
//            getWritableDatabase().execSQL(sql);
//        } catch (SQLException e) {
//            Log.e(TAG,"Error in deleting recodes");
//        }
//    }
