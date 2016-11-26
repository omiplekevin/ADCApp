package adc.com.adcapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

        getActionBar().setDisplayHomeAsUpEnabled(true);
        initializeVariables();
        setupAdapter();
    }

    private void initializeVariables(){
        reportsListView = (ListView) findViewById(R.id.reportsListView);
    }

    private void setupAdapter(){
        List<Data.Post> dummyData = getDummyValues();
        reportsListViewAdapter = new ReportsListViewAdapter(this, dummyData);
        if (reportsListView != null) {
            if (reportsListViewAdapter != null) {
                reportsListView.setAdapter(reportsListViewAdapter);
            } else {
                Log.e(getClass().getSimpleName(), "reportsListViewAdapter is null!");
            }
        } else {
            Log.e(getClass().getSimpleName(), "reportsListView is null!");
        }
    }

    private List<Data.Post> getDummyValues() {
        List<Data.Post> data = new ArrayList<>();

        //plain CAPTION ONLY DATA
        Data.Post d1 = new Data().new Post();
        d1.caption = "Hey! this man stole a kiss from me! ><";
        d1.file = "";
        d1.filetype = "";
        d1.latlng = "";

        //CAPTION + LOCATION
        Data.Post d2 = new Data().new Post();
        d2.caption = "Man fell of the Niagara Falls, LOL!";
        d2.file = "";
        d2.filetype = "";
        d2.latlng = "43.077199,-79.0773087";

        //CAPTION + IMAGE + LOCATION
        Data.Post d3 = new Data().new Post();
        d3.caption = "Serious now, Japan gets deep sink hole! :O";
        d3.file = "http://d.ibtimes.co.uk/en/full/1565205/japan-sinkhole.jpg";
        d3.filetype = "";
        d3.latlng = "33.589956, 130.422422";

        //CAPTION + IMAGE
        Data.Post d4 = new Data().new Post();
        d4.caption = "Attacked by cuteness!";
        d4.file = "https://s-media-cache-ak0.pinimg.com/564x/2e/3a/74/2e3a7432a44f3fc1c746af530f496096.jpg";
        d4.filetype = "";
        d4.latlng = "";

        data.add(d1);
        data.add(d2);
        data.add(d3);
        data.add(d4);

        return data;
    }
}
