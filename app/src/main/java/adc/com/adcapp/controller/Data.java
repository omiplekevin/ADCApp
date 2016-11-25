package adc.com.adcapp.controller;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

public class Data {
    public Post[] post;
    public boolean success;
    public String[] errors;

    public Data(){

    }

    public class Post{
        public String file;
        public String caption;
        public String filetype;
        public String latlng;
    }

    public static void getData(){
        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> map = new HashMap<>();
                return Network.NetworkRequest("", "", map);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Data data = new Gson().fromJson(s, Data.class);
                User.getInstance().callback.data(data.post, data.success, data.errors);
            }
        }.execute();
    }

    public static void postData(Post post){
        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> map = new HashMap<>();
                return Network.NetworkRequest("", "", map);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Data data = new Gson().fromJson(s, Data.class);
                User.getInstance().callback.data(data.post, data.success, data.errors);
            }
        }.execute();
    }
}
