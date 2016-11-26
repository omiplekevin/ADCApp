package adc.com.adcapp.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import adc.com.adcapp.BuildConfig;

public class User {
    private static volatile User instance;
    public String username;
    public String name;
    public String password;
    public boolean success = false;
    public String timestamp;
    public String[] errors;

    public OnEvent callback;

    public User(){

    }

    public interface OnEvent{
        void session(Boolean isLoggedIn);
        void register(Boolean isSuccess, String[] errorMessage);
        void data(Data.Post[] post, boolean isSuccess, String[] errorMessage);
    }

    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public void register(){
        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... voids) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                map.put("name", name);
                return Network.NetworkRequest("", "POST", map);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (callback != null){
                    User user = new Gson().fromJson(s, User.class);
                    callback.register(success, user.errors);
                }
            }
        }.execute();
    }

    public void login(){
        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... voids) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return Network.NetworkRequest(BuildConfig.login, "POST", map);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(getClass().getSimpleName(), s);
                if (callback != null){
                    User user = new Gson().fromJson(s, User.class);
                    timestamp = user.timestamp;
                    success = user.success;
                    callback.session(success);
                }
            }
        }.execute();
    }

    public void logout(){
        success = false;
    }

    public void newData(Data.Post[] post, boolean isSuccess, String[] errors){
        if (callback != null){
            callback.data(post, isSuccess, errors);
        }
    }

    public void setCallback(OnEvent callback){
        this.callback = callback;
    }
}
