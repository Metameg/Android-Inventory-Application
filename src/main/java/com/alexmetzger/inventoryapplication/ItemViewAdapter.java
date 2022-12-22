package com.alexmetzger.inventoryapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ItemViewAdapter extends ArrayAdapter<Item> {
    // get all items from database and update the Items. Trying to get edit text +1


    // invoke the suitable constructor of the ArrayAdapter class
    public ItemViewAdapter(Context context, ArrayList<Item> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
//      ItemDatabase db = ItemDatabase.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Item currentItem = getItem(position);
        // Set the widgets from that currItem
        Button       removeBtn = (Button) currentItemView.findViewById(R.id.Remove);
        EditText     quantityEdit = (EditText) currentItemView.findViewById(R.id.ItemQuantity);
        ImageButton  plusBtn = (ImageButton) currentItemView.findViewById(R.id.plus);
        ImageButton  minusBtn = (ImageButton) currentItemView.findViewById(R.id.minus);
        TextView     itemId = currentItemView.findViewById(R.id.ItemNo);
        TextView     name = currentItemView.findViewById(R.id.ItemName);
        // Get the singleton database
        ItemDatabase db = ItemDatabase.getInstance(getContext().getApplicationContext());

        assert currentItem != null;
        // Initialize buttons and click listeners
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteItem(currentItem);
                remove(currentItem);
                notifyDataSetChanged();
            }
        });


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currQuantity = currentItem.getQuantity();

                currQuantity += 1;
                currentItem.setQuantity(currQuantity);
                // Update text
                quantityEdit.setText(Integer.toString(currentItem.getQuantity()));
            }
        });


        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currQuantity = currentItem.getQuantity();

                if(currQuantity > 0)
                    currQuantity -= 1;
                else
                    currQuantity = 0;
                currentItem.setQuantity(currQuantity);
                // Update text
                quantityEdit.setText(Integer.toString(currentItem.getQuantity()));

            }
        });

        quantityEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // When focus is lost check that the text field has valid values
                if (!hasFocus) {
                    String editStr = quantityEdit.getText().toString();
                    if (editStr.equals("")) {
                        quantityEdit.setText("0");
                    }
                }

                if (hasFocus) {
                    String editStr = quantityEdit.getText().toString();
                    int editNum = Integer.parseInt(editStr);

                    if (editNum < 0)
                        currentItem.setQuantity(0);
                    else
                        currentItem.setQuantity(editNum);
                }
            }
        });

        // then set the different values
        quantityEdit.setText(Integer.toString(currentItem.getQuantity()));
        itemId.setText(Long.toString(currentItem.getId()));
        name.setText(currentItem.getName());

        // then return the recyclable view
        return currentItemView;
    }

}
