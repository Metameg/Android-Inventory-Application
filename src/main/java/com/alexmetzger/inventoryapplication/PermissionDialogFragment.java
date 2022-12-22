package com.alexmetzger.inventoryapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class PermissionDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Item Already Found")
                        .setMessage(R.string.quantity_alert_message);

        // Click Listener for positive
        builder.setPositiveButton(R.string.Update_bold, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //do stuff onclick of POSITIVE
                Toast.makeText(getContext().getApplicationContext(),
                          "Quantity Updated!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });

        // Click Listener for negative
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //do stuff onclick of NEGATIVE
                Toast.makeText(getContext().getApplicationContext(),
                         "Re-enter name", Toast.LENGTH_LONG).show();
//                processingAlert = false;
            }
        });
//        builder.setTitle("Item Already Found");
//        builder.setMessage(R.string.quantity_alert_message);
//        builder.setPositiveButton("UPDATE", null);
//        builder.setNegativeButton("Cancel", null);


        return builder.create();
    }
}
