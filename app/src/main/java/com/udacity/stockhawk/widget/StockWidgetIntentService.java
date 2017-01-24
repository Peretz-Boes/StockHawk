package com.udacity.stockhawk.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by Peretz on 2016-12-29.
 */
public class StockWidgetIntentService extends IntentService {

    public StockWidgetIntentService() {
        super("StockWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
        int[] stockWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(this,StockWidgetProvider.class));
        Uri stockUri= Contract.Quote.makeUriForStock(Contract.Quote.COLUMN_SYMBOL);
        Cursor cursor=getContentResolver().query(stockUri, Contract.Quote.QUOTE_COLUMNS,null,null,"ASC");
        if (cursor==null){
            return;
        }
        if (!cursor.moveToFirst()){
            cursor.close();
            return;
        }

        String stockSymbol=cursor.getString(Integer.parseInt(Contract.Quote.COLUMN_SYMBOL));
        String stockPrice=cursor.getString(Integer.parseInt(Contract.Quote.COLUMN_PRICE));
        String stockAbsoluteChange=cursor.getString(Integer.parseInt(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
        String stockPercentageChange=cursor.getString(Integer.parseInt(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));
        String stockData=stockSymbol+" "+stockPrice+" "+stockAbsoluteChange+" "+stockPercentageChange;
        String description="desc";
        for (int stockWidgetId:stockWidgetIds){
            int layoutId= R.layout.stock_widget_small;
            RemoteViews remoteViews=new RemoteViews(getPackageName(),layoutId);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
                setRemoteContentDescription(remoteViews,description);
            }
            remoteViews.setTextViewText(R.id.stock_widget,stockData);
            Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
            remoteViews.setOnClickPendingIntent(R.id.stock_widget,pendingIntent);
            appWidgetManager.updateAppWidget(stockWidgetId,remoteViews);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews remoteViews,String description){
        remoteViews.setContentDescription(R.id.stock_widget_icon,description);
    }

}
