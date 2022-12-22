package com.alexmetzger.inventoryapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.LayoutInflater;
import java.security.Permission;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private EditText mNameEdit;
    private EditText mQuantityEdit;
    private EditText mItemNoEdit;
    private long mLastId;

    private PermissionDialogFragment mDialog;

    private ItemDatabase mItemDb;
    private Item mItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mNameEdit = findViewById(R.id.ItemNameEdit);
        mQuantityEdit = findViewById(R.id.QuantityEdit);
        mItemNoEdit = findViewById(R.id.ItemNoEdit);

        Item lastItem;
        mItemDb = ItemDatabase.getInstance(this);
        // get last item Id of Items db
        List<Item> allItems = mItemDb.getItems(ItemDatabase.ItemSortOrder.UPDATE_ASC);
        if (!allItems.isEmpty())
            lastItem = allItems.subList(allItems.size() - 1, allItems.size()).get(0);
        else
            lastItem = new Item("temp", 00);

        mLastId = lastItem.getId();
        // set the item no in the disabled edit text field
        mItemNoEdit.setText(Long.toString(mLastId + 1));

    }

    // Initialize Buttons
    public void onAddClicked(View view) {

        String nameText = mNameEdit.getText().toString();


        if (mItemDb.verification(nameText)) {

            // if name exists in database, ask to update
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PermissionDialogFragment mDialog = new PermissionDialogFragment();
            mDialog.show(fragmentTransaction, "permission");

            Item item = mItemDb.getItem(nameText);

            // set quantity then update in database
            item.setQuantity(Integer.parseInt(mQuantityEdit.getText().toString()));
            mItemDb.updateItem(item);


        }
        else {
        mItem = new Item();

        mItem.setName(nameText);
        mItem.setQuantity(Integer.parseInt(mQuantityEdit.getText().toString()));
        mItemDb.addItem(mItem);
        // Go back to items screen
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
        }
    }

    public void onCancelClicked(View view) {
        // Go back to items screen
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }
}