package com.example.prasachd.tinyurl;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.SQLException;
import java.util.HashMap;

/**
 * Created by prasachd on 2/11/16.
 */
public class CacheHandler extends Application {

    private static CacheHandler cacheHandler;
    public HashMap<String,String> urlCache;
    private SQLiteDatabase db;
    private final String dbName = "TinyUrlCache";
    private final String createSQL = "CREATE TABLE IF NOT EXISTS TINYURLTABLE(ORIGURL VARCHAR, TINYURL VARCHAR);";

    public static CacheHandler getInstance(){
        checkInstance();
        return cacheHandler;
    }

    public static void checkInstance(){
        if(cacheHandler==null){
            throw new IllegalStateException("Cache Handler no initialized...");
        }
    }

    public static String getTinyUrl(String key){
        if(CacheHandler.getInstance().urlCache==null) {
            throw new IllegalStateException("Cache Unavailable...");
        }
        return CacheHandler.getInstance().urlCache.get(key);
    }

    public static boolean isTinyUrlPresent(String originalUrl){
        if(CacheHandler.getInstance().urlCache!=null){
            String result = CacheHandler.getInstance().urlCache.get(originalUrl);
            if(result==null) return false;
            if(result!=null) return true;

        }else{
            return false;
        }
        return false;
    }

    public static void closeDb(){
        if(CacheHandler.getInstance().db==null) {
            throw new IllegalStateException("Cache Unavailable...");
        }
        CacheHandler.getInstance().db.close();
    }

    public static void addResultToCache(String key, String value){
        if(CacheHandler.getInstance().urlCache==null) {
            throw new IllegalStateException("Cache Unavailable...");
        }
        CacheHandler.getInstance().urlCache.put(key, value);
        String insertStmt = "INSERT INTO TINYURLTABLE VALUES('"+ key + "','" + value + "');";
        try {
            CacheHandler.getInstance().db.execSQL(insertStmt);
        }catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("CacheHandler onConfigurationChanged");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("CacheHandler onCreate");
        cacheHandler = this;
        this.urlCache=new HashMap<String,String>();
        this.db = openOrCreateDatabase(this.dbName, Context.MODE_PRIVATE, null);
        if(this.db==null){
            throw new IllegalStateException("Unable to initialize database");
        }
        try {
            this.db.execSQL(this.createSQL);
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }
        loadDb();
    }

    private void loadDb(){
        System.out.println("CacheHandler loadDb");
        String selectStmnt = "SELECT * FROM TINYURLTABLE;" ;
        Cursor cr = this.db.rawQuery(selectStmnt, null);
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            String key = cr.getString(0);
            String value = cr.getString(1);
            System.out.println("CacheHandler loadDb. Key="+key+" : value="+value);
            this.urlCache.put(key, value);
            cr.moveToNext();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        System.out.println("CacheHandler onTerminate");
        super.onTerminate();
    }
}

