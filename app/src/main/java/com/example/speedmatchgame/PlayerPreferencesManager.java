package com.example.speedmatchgame;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PlayerPreferencesManager {

    static PlayerPreferencesManager instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static PlayerPreferencesManager setInstance(Context context){
        if (instance == null){
            instance = new PlayerPreferencesManager(context);
        }
        return instance;
    }

    private PlayerPreferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences("my_file", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void putPlayerManager(PlayerAttributes playerList){
        Gson gson = new Gson();
        String playerAttributes = gson.toJson(playerList);
        editor.putString("player", playerAttributes);
        editor.apply();
    }

    public PlayerAttributes getPlayerManager(){
        Gson gson = new Gson();
        String playerAttribute = sharedPreferences.getString("player", null);
        return gson.fromJson(playerAttribute, PlayerAttributes.class);
    }

}
