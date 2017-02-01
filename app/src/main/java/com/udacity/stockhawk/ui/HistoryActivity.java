package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HistoryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,StockAdapter.StockAdapterOnClickHandler {

    private static final int STOCK_HISTORY_LOADER=0;
    StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        stockAdapter=new StockAdapter(this,this);
        getLoaderManager().initLoader(STOCK_HISTORY_LOADER,null,this);
        LineChart stockHistory=(LineChart) findViewById(R.id.stock_history_line_chart);
        ArrayList<String> stockTradingDates=new ArrayList<>();
        ArrayList<Entry> stockPrices=new ArrayList<>();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(this, Contract.Quote.URI,Contract.Quote.QUOTE_COLUMNS,null,null,Contract.Quote.COLUMN_HISTORY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), R.string.no_stock_history_available,Toast.LENGTH_LONG).show();
        }
        stockAdapter.setCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        stockAdapter.setCursor(null);
    }

    @Override
    public void onClick(String symbol) {

    }
}
