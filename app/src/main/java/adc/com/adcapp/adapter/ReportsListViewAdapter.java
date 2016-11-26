package adc.com.adcapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import adc.com.adcapp.R;
import adc.com.adcapp.controller.Data;
import adc.com.adcapp.controller.Network;

/**
 * Created by OMIPLEKEVIN on 025 Nov 25, 2016.
 */

public class ReportsListViewAdapter extends BaseAdapter{

    List<Data.Post> postList;
    Context context;

    public ReportsListViewAdapter(Context context, List<Data.Post> posts) {
        this.postList = posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Data.Post getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        final Data.Post post = getItem(i);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_report_item, viewGroup, false);
            holder.staticGoogleMap = (ImageView) convertView.findViewById(R.id.imageLocation);
            holder.capture = (ImageView) convertView.findViewById(R.id.imageCapture);
            holder.placeName = (TextView) convertView.findViewById(R.id.placeName);
            holder.caption = (TextView) convertView.findViewById(R.id.caption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //check LatLng
        if (post.latlng.isEmpty()) {
            holder.staticGoogleMap.setVisibility(View.GONE);
            holder.placeName.setVisibility(View.GONE);
            holder.staticGoogleMap.invalidate();
            holder.placeName.invalidate();
        } else {
            holder.staticGoogleMap.setVisibility(View.VISIBLE);
            holder.staticGoogleMap.invalidate();
            String _staticMapImageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                    + post.latlng.replace(" ", "")
                    + "&zoom=17&size=640x256&markers=color:orange%7C" + post.latlng.replace(" ", "") + "&key="
                    + this.context.getResources().getString(R.string.google_maps_key);
            Log.i(getClass().getSimpleName(), "Picasso loading: " + _staticMapImageUrl);
            Picasso.with(this.context).load(_staticMapImageUrl).placeholder(R.drawable.placeholder).into(holder.staticGoogleMap);

            final ViewHolder _viewHolder = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String locationDetailRequestURL = "http://maps.google.com/maps/api/geocode/json?latlng=" + post.latlng.replace(" ", "");
                    final String locationDetail = Network.NetworkRequest(locationDetailRequestURL, "GET", null);
                    try {
                        JSONArray addresses = new JSONObject(locationDetail).getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
                        final String localName = addresses.getJSONObject(2).getString("long_name");
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                _viewHolder.placeName.setText(localName.toUpperCase(Locale.getDefault()));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //check capture
        if (post.file.isEmpty()) {
            holder.capture.setVisibility(View.GONE);
            holder.capture.invalidate();
        } else {
            holder.capture.setVisibility(View.VISIBLE);
            holder.capture.invalidate();
            Picasso.with(this.context).load(post.file).placeholder(R.drawable.placeholder).into(holder.capture);
        }

        if (holder.staticGoogleMap.getVisibility() == View.GONE && holder.capture.getVisibility() == View.GONE) {
            convertView.findViewById(R.id.mediaHeaders).setVisibility(View.GONE);
            convertView.findViewById(R.id.mediaHeaders).invalidate();
        }

        if (post.caption.isEmpty()) {
            holder.caption.setText("No Caption!");
        } else {
            holder.caption.setText(post.caption);
        }

        return convertView;
    }

    class ViewHolder{
        public ImageView staticGoogleMap;   //static google map requested via URL
        public ImageView capture;           //uploaded image from user
        public TextView placeName;          //place name taken from the LatLng given, empty if no LatLng
        public TextView caption;            //Caption content, mandatory field
    }
}
