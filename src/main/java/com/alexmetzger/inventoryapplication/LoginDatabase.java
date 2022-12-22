package com.alexmetzger.inventoryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginDatabase extends SQLiteOpenHelper {

    private static LoginDatabase sLoginDatabase;
    private List<Login> mLogins;

    private static final String DB_NAME = "logins.db";
    private static final int VERSION = 1;


    private LoginDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static LoginDatabase getInstance(Context context) {
        if (sLoginDatabase == null) {
            sLoginDatabase = new LoginDatabase(context);
        }
        return sLoginDatabase;
    }

    private static final class Table {
        private static final String TABLE = "logins";
        private static final String COL_ID = "_id";
        private static final String COL_USER = "username";
        private static final String COL_PASSWORD = "password";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create credentials table
        db.execSQL("create table " + Table.TABLE + "(" +
                 Table.COL_ID + " integer primary key autoincrement, " +
                 Table.COL_USER + ", " +
                 Table.COL_PASSWORD + ")");

        // populate table with admin credential
        Login cred = new Login("admin", "pwd");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.COL_USER, cred.getUsername());
        contentValues.put(Table.COL_PASSWORD, cred.getPassword());
        db.insert(Table.TABLE, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table.TABLE);
        onCreate(db);
    }

    public List<Login> getLogins() {

        ArrayList<Login> mLogins = new ArrayList<Login>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + Table.TABLE + " order by " + Table.COL_ID + " asc" ;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Login login = new Login();
                login.setId(cursor.getLong(0));
                login.setUsername(cursor.getString(1));
                login.setPassword(cursor.getString(2));

                mLogins.add(login);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return mLogins;
    }

    public void addLogin(Login login) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.COL_USER, login.getUsername());
        values.put(Table.COL_PASSWORD, login.getPassword());
        long id = db.insert(Table.TABLE, null, values);
        login.setId(id);
    }

    // get login by id
    public Login getLogin(long _id) {
        Login login = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + Table.TABLE + " where " +
                Table.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {Long.toString(_id)});

        if (cursor.moveToFirst()) {
            login = new Login();
            login.setId(cursor.getLong(0));
            login.setUsername(cursor.getString(1));
            login.setPassword(cursor.getString(2));
        }
        cursor.close();

        return login;
    }

    public boolean verification(String _username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + Table.TABLE + " where "
                + Table.COL_USER + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {_username});

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).equals(password))
                    return true;
            } while (cursor.moveToNext());
        }
        return false;
    }


}
