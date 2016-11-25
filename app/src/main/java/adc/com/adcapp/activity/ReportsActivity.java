package adc.com.adcapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import adc.com.adcapp.R;
import adc.com.adcapp.adapter.ReportsListViewAdapter;

public class ReportsActivity extends Activity {

    private ListView reportsListView;
    private ReportsListViewAdapter reportsListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initializeVariables();
        setupAdapter();
    }

    private void initializeVariables(){
        reportsListView = (ListView) findViewById(R.id.reportsListView);
    }

    private void setupAdapter(){
        reportsListViewAdapter = new ReportsListViewAdapter();

        if (reportsListView != null) {

        }
    }
}
