package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.utils.Formatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.utils.Formatter.getDollarFormat;

public class HistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh_detail)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error_detail)
    TextView error;
    @BindView(R.id.text_view_name)
    TextView nameView;
    @BindView(R.id.text_view_price)
    TextView priceView;
    @BindView(R.id.text_view_change)
    TextView changeView;
    @BindView(R.id.chart)
    LineChart lineChartView;
    public static final String EXTRA_SYMBOL="EXTRA_SYMBOL";
    private static final int STOCK_LOADER=1;
    private static final String LOG_TAG=HistoryActivity.class.getSimpleName();
    private String symbol;
    private boolean chartAnimated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        symbol = getIntent().getExtras().getString(EXTRA_SYMBOL);
        toolbar.setTitle(symbol);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportLoaderManager().initLoader(STOCK_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.Quote.URI, Contract.Quote.QUOTE_COLUMNS, Contract.Quote.COLUMN_SYMBOL + "=?", new String[] {symbol}, Contract.Quote.COLUMN_SYMBOL);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {
            data.moveToFirst();
            updateStockInfo("Stock Name "+symbol, data.getFloat(Contract.Quote.POSITION_PRICE), data.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE), data.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE));
            updateStockGraph(data.getString(Contract.Quote.POSITION_HISTORY));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void updateStockGraph(String historyBuilder) {
        List<Entry> entries = new ArrayList<>();
        String[] lines = historyBuilder.split("\\n");
        int linesLength = lines.length;
        final String[] dates = new String[linesLength];
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < linesLength; i++){
            String[] dateAndPrice = lines[linesLength - i - 1].split(",");
            calendar.setTimeInMillis(Long.valueOf(dateAndPrice[0]));
            dates[i] = formatter.format(calendar.getTime());
            entries.add(new Entry(i, Float.valueOf(dateAndPrice[1])));
        }
        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.chart_label));
        XAxis xAxis = lineChartView.getXAxis();
        dataSet.setValueTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        LineData lineData = new LineData(dataSet);
        xAxis.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        lineChartView.getAxisLeft().setTextColor(
                ContextCompat.getColor(getApplicationContext(), R.color.white));
        lineChartView.getAxisRight().setTextColor(
                ContextCompat.getColor(getApplicationContext(), R.color.white));
        lineChartView.getLegend().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dates[(int) value];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }

        });

        Description description = new Description();
        description.setText(getString(R.string.chart_description));
        description.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        lineChartView.setDescription(description);
        lineChartView.setData(lineData);
        if (!chartAnimated) {
            lineChartView.animateX(2000);
            chartAnimated = true;
        }
    }

    private void updateStockInfo(String name,float price,float change,float percentage){
        nameView.setText(name);
        priceView.setText(getDollarFormat(price));
        if (change>0){
            changeView.setBackgroundResource(R.drawable.percent_change_pill_green);
        }else {
            changeView.setBackgroundResource(R.drawable.percent_change_pill_red);
        }
        changeView.setText(Formatter.getDollarFormatWithPlus(change)+" "+Formatter.getPercentageFormat(percentage));
    }

}
