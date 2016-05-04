package com.nomader.weather.controllers.managers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nomader.weather.R;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;


/**
 * Created by nomader on 23.01.16.
 */
public class FrameFragmentManager {
    public static String TAG = "FrameFragmentManager";

    private Context context;
    private FragmentManager manager;
    private String currentFragment;
    private int container;
    private HashMap<String, Fragment> fragmentMap;

    public FrameFragmentManager(FragmentManager manager, Context context, int container) {
        this.context = context;
        this.manager = manager;
        fragmentMap = new HashMap<>();
    }


    public FrameFragmentManager(FragmentManager manager, Context context, HashMap<String, Fragment> fragments, Bundle savedInstanceState) {
        this.manager = manager;
        this.context = context;
        this.fragmentMap = fragments;

        if (savedInstanceState != null) {
            this.currentFragment = savedInstanceState.getString(CURRENT_KEY);
            this.container = savedInstanceState.getInt(CONTAINER_KEY);

        }

    }

    public void writeToBundle(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_KEY, currentFragment);
    }

    public void addFragment(String key, Fragment fragment){
        fragmentMap.put(key, fragment);
    }

    public void replaceFragment(String key, boolean addToBackStack){
        if(!fragmentMap.containsKey(key)){
            Log.e(TAG, "No such fragment");
            return;
        }

        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(container, fragmentMap.get(key), )
    }

    private static String CURRENT_KEY = "FFM_current";
    private static String CONTAINER_KEY = "FFM_container";


}
