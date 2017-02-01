package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.DisplayToast;

import timber.log.Timber;

/**
 * Created by Peretz on 2016-12-26.
 */
public class QuoteIntentService extends IntentService {

    Handler handler;

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
        handler=new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Timber.d("Intent handled");
            QuoteSyncJob.getQuotes(getApplicationContext());
        }catch (NullPointerException exception){
            handler.post(new DisplayToast(this, R.string.invalid_stock));
        }
    }
}
