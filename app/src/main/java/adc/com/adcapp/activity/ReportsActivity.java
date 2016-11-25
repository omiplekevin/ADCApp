package adc.com.adcapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adc.com.adcapp.R;
import adc.com.adcapp.adapter.ReportsListViewAdapter;
import adc.com.adcapp.controller.Data;

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
        List<Data.Post> dummyData = getDummyValues();
        if (reportsListView != null) {

        }
    }

    private List<Data.Post> getDummyValues() {
        List<Data.Post> data = new ArrayList<>();

        Data.Post d1 = new Data().new Post();
        d1.caption = "Hey! this man stole a kiss from me! ><";
        d1.file = "";
        d1.filetype = "";
        d1.latlng = "";

        Data.Post d2 = new Data().new Post();
        d2.caption = "Man fell of the Niagara Falls, LOL!";
        d2.file = "";
        d2.filetype = "";
        d2.latlng = "43.077199,-79.0773087";

        Data.Post d3 = new Data().new Post();
        d2.caption = "Serious now, Japan gets deep sink hole! :O";
        d2.file = "";
        d2.filetype = "";
        d2.latlng = "33.589956, 130.422422";

        data.add(d1);
        data.add(d2);
        data.add(d3);

        return data;
    }
}
