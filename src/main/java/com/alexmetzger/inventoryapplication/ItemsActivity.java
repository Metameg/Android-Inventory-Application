package com.alexmetzger.inventoryapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Intent;
import android.os.Bundle;


import android.view.View;

import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    private ItemDatabase mItemDb;
    private List<Item> mItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        mItemDb = ItemDatabase.getInstance(getApplicationContext());

        ListView itemsListView = findViewById(R.id.listView);

        mItemList = ItemDatabase.getInstance(this).getItems(ItemDatabase.ItemSortOrder.UPDATE_ASC);

        // Convert itemList to arraylist so ItemViewAdapter can be initialized
        ArrayList<Item> itemsArray = new ArrayList<Item>(mItemList);
        ItemViewAdapter itemsArrayAdapter = new ItemViewAdapter(this, itemsArray);

//     set the numbersViewAdapter for ListView
        itemsListView.setAdapter(itemsArrayAdapter);

    }


    public void onAddButtonClicked(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }



}



