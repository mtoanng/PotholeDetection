package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class LoadingDialog {

        Context context;
        Dialog dialog;

        public LoadingDialog(Context context){
            this.context=context;
        }
        public void ShowDialog(String title){
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView titleView = dialog.findViewById(R.id.textView12);
            titleView.setText(title);
            dialog.create();
            dialog.show();
        }
        public void HideDialog(){
            dialog.dismiss();
        }

    }
