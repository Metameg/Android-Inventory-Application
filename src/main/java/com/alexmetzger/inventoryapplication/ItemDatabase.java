package com.alexmetzger.inventoryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;


public class ItemDatabase extends SQLiteOpenHelper {
    private static ItemDatabase sItemDatabase;

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "items.db";

    public enum ItemSortOrder { ALPHABETIC, UPDATE_QTY, UPDATE_ASC };

    public static ItemDatabase getInstance(Context context) {
        if (sItemDatabase == null) {
            sItemDatabase = new ItemDatabase(context);
        }
        return sItemDatabase;
    }


    private ItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class ItemTable {
        private static final String TABLE = "items";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_QTY = "quantity";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create items table
        db.execSQL("create table " + ItemTable.TABLE + " (" +
                ItemTable.COL_ID + " integer primary key autoincrement, " +
                ItemTable.COL_NAME + ", " +
                ItemTable.COL_QTY + " )");


        // Add some items

        String[] items = {"Boots", "Hats"};
        for (String it: items) {

            Item item = new Item(it, 1);
            ContentValues values = new ContentValues();
            values.put(ItemTable.COL_NAME, item.getName());
            values.put(ItemTable.COL_QTY, item.getQuantity());
            db.insert(ItemTable.TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ItemTable.TABLE);
        onCreate(db);
    }


    public List<Item> getItems(ItemSortOrder order) {
        List<Item> items = new ArrayList<Item>();

        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        switch (order) {
            case ALPHABETIC:
                orderBy = ItemTable.COL_NAME + " collate nocase";
                break;
            case UPDATE_QTY:
                orderBy = ItemTable.COL_QTY + " desc";
                break;
            default:
                orderBy = ItemTable.COL_ID + " asc";
                break;
        }

        String sql = "select * from " + ItemTable.TABLE + " order by " + orderBy;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getLong(0));
                item.setName(cursor.getString(1));
                item.setQuantity(cursor.getInt(2));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return items;
    }

    public void addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemTable.COL_NAME, item.getName());
        values.put(ItemTable.COL_QTY, item.getQuantity());
        long id = db.insert(ItemTable.TABLE, null, values);
        item.setId(id);
    }

    public void updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemTable.COL_ID, item.getId());
        values.put(ItemTable.COL_NAME, item.getName());
        values.put(ItemTable.COL_QTY, item.getQuantity());
        db.update(ItemTable.TABLE, values,
                ItemTable.COL_NAME + " = ?", new String[] { item.getName() });
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ItemTable.TABLE,
                ItemTable.COL_NAME + " = ?", new String[] { item.getName() });
    }

    // Get Item by Id
    public Item getItem(long itemId) {
        Item item = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + ItemTable.TABLE +
                " where " + ItemTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { Long.toString(itemId) });

        if (cursor.moveToFirst()) {
            item = new Item();
            item.setId(cursor.getLong(0));
            item.setName(cursor.getString(1));
            item.setQuantity(cursor.getInt(2));
        }
        cursor.close();

        return item;
    }
    // Get Item by Name
    public Item getItem(String _name) {
        Item item = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + ItemTable.TABLE +
                " where " + ItemTable.COL_NAME + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { _name });

        if (cursor.moveToFirst()) {
            item = new Item();
            item.setId(cursor.getLong(0));
            item.setName(cursor.getString(1));
            item.setQuantity(cursor.getInt(2));
        }
        cursor.close();

        return item;
    }

    public boolean verification(String _name) {

        SQLiteDatabase db = this.getReadableDatabase();

        // Query name from database
        Cursor cursor = db.rawQuery("SELECT 1 FROM "+ItemTable.TABLE+" WHERE "+ItemTable.COL_NAME +
                                "=?", new String[] {_name});
        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }
}
