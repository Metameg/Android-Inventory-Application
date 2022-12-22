package com.alexmetzger.inventoryapplication;

public class Item {
    private long mId;
    private String mName;
    private int mQuantity;

    public Item() {}

    public Item(String name, int quantity) {

        mName = name;
        mQuantity = quantity;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
}