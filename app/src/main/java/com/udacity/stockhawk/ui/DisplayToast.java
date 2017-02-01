package com.udacity.stockhawk.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Peretz on 2017-01-31.
 */

public class DisplayToast implements Runnable {

    private final Context context;
    int toastResourceId;

    public DisplayToast(Context context, int text) {
        this.context = context;
        toastResourceId=text;
    }

    @Override
    public void run() {
        Toast.makeText(context,toastResourceId,Toast.LENGTH_LONG).show();
    }
}
