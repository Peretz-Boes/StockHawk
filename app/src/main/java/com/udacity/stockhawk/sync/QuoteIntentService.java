package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

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
        QuoteSyncJob.getQuotes(getApplicationContext());
    }
}
