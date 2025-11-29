package com.example.trip_bag;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPref {
    private static final String Pref_Name = "trip_bag_pref";
    private static final String Key_Items = "bag_items";

    public static void saveItems(Context context, ArrayList<BagItem> items) {
        SharedPreferences prefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString(Key_Items, json);
        editor.apply();

    }

    public static ArrayList<BagItem> loadItems(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=prefs.getString(Key_Items,null);
        Type type=new TypeToken<ArrayList<BagItem>>(){}.getType();

        ArrayList<BagItem>list=gson.fromJson(json,type);
        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    public static void saveSearchQuery(Context context, String query) {
        SharedPreferences prefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("search_query", query);
        editor.apply();

    }
    public static String loadSearchQuery(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        return prefs.getString("search_query", null);
    }
    public static void saveFilterPacked(Context context, boolean query) {
    SharedPreferences prefs=context.getSharedPreferences(Pref_Name,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=prefs.edit();
    editor.putBoolean("filter_packed",query);
    editor.apply();
    }

    public static boolean loadFilterPacked(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
       return  prefs.getBoolean("filter_packed",false);
    }
    }



