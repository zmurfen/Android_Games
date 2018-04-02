package com.example.joacim.spaceshooter;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by joacim on 2018-03-30.
 * Database to handle all the users playing this game
 * Stores name, passwords, emails, level and gold.
 */


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "User.db";
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";
    private static final String USER_LEVEL = "level";
    private static final String USER_GOLD = "gold";
    private static final Integer VERSION = 1;


    public DBHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table user " +
                        "(id integer primary key, name text, password text, email text, level text, gold integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void insertUser (String name, String password, String email, int level, int gold) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, name);
            contentValues.put(USER_PASSWORD, password);
            contentValues.put(USER_EMAIL, email);
            contentValues.put(USER_LEVEL, level);
            contentValues.put(USER_GOLD, gold);
            db.insert(USER_TABLE_NAME, null, contentValues);
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(null, db);
        }
    }

    public int numberOfUsers(){
        SQLiteDatabase db = null;
        int rows = 0;
        try {
            db = this.getReadableDatabase();
            rows = (int) DatabaseUtils.queryNumEntries(db, USER_NAME);
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(null, db);
        }
        return rows;
    }

    public boolean updateUser (Integer id, String name, String password, String email, int level, int gold) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, name);
            contentValues.put(USER_PASSWORD, password);
            contentValues.put(USER_EMAIL, email);
            contentValues.put(USER_LEVEL, level);
            contentValues.put(USER_GOLD, gold);
            db.update(USER_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(null, db);
        }
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = null;
        int deleteValue = 0;
        try {
            db = this.getWritableDatabase();
            deleteValue = db.delete("user",
                    "id = ? ",
                    new String[]{Integer.toString(id)});
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(null,db);
        }
        return deleteValue;
    }

    public ArrayList<String> getAllUserNames() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor res = null;
        try {
            db = this.getReadableDatabase();
            res = db.rawQuery("select * from user", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(res.getColumnIndex(USER_NAME)));
                res.moveToNext();
            }
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(res,db);
        }
        return array_list;
    }

    public ArrayList<String> getAllUserEmails() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor res = null;
        try {
            db = this.getReadableDatabase();
            res = db.rawQuery("select * from user", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(res.getColumnIndex(USER_EMAIL)));
                res.moveToNext();
            }
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(res,db);
        }
        return array_list;
    }

    public User getUser(String name) {
        User user = new User();
        SQLiteDatabase db = null;
        Cursor res = null;
        try {
            db = this.getReadableDatabase();
            res = db.rawQuery("select * from user where name = ?", new String[]{name});
            res.moveToFirst();

            if (!res.isAfterLast()) {
                user.setName(res.getString(res.getColumnIndex(USER_NAME)));
                user.setPassword(res.getString(res.getColumnIndex(USER_PASSWORD)));
                user.setEmail(res.getString(res.getColumnIndex(USER_EMAIL)));
                user.setId(res.getInt(res.getColumnIndex(USER_ID)));
                user.setLevel(res.getInt(res.getColumnIndex(USER_LEVEL)));
                user.setGold(res.getInt(res.getColumnIndex(USER_GOLD)));
            } else {
                user = null;
            }
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(res,db);
        }

        return user;
    }

    public User getUser(int id) {
        User user = new User();
        SQLiteDatabase db = null;
        Cursor res = null;

        try {
            db = this.getReadableDatabase();
            res = db.rawQuery("select * from user where id = ?", new String[]{Integer.toString(id)});
            res.moveToFirst();

            if (!res.isAfterLast()) {
                user.setName(res.getString(res.getColumnIndex(USER_NAME)));
                user.setPassword(res.getString(res.getColumnIndex(USER_PASSWORD)));
                user.setEmail(res.getString(res.getColumnIndex(USER_EMAIL)));
                user.setId(res.getInt(res.getColumnIndex(USER_ID)));
                user.setLevel(res.getInt(res.getColumnIndex(USER_LEVEL)));
                user.setGold(res.getInt(res.getColumnIndex(USER_GOLD)));
            } else {
                user = null;
            }
        } catch(SQLException se) {
            Log.w("SQLException", se.fillInStackTrace());
        } finally {
            close(res,db);
        }
        return user;
    }

    private void close(Cursor res, SQLiteDatabase db) {
        if(res != null) {res.close();}
        if(db != null) {db.close();}
    }
}