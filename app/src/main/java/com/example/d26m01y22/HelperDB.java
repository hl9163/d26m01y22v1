package com.example.d26m01y22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import static com.example.d26m01y22.tabales.Workers.*;
import static com.example.d26m01y22.tabales.FoodCompany.*;

public class HelperDB extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+TABLE_WORKERS;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+CARD_ID+" TEXT,";
        strCreate+=" "+LAST_NAME+" TEXT,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+WORKER_COMPANY+" TEXT,";
        strCreate+=" "+PERSONAL_ID+" TEXT,";
        strCreate+=" "+PHONE_NUMBER+" TEXT,";
        strCreate+=" "+IS_WORKING+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_FOOD_COMPANY;
        strCreate+=" ("+KEY_ID_Food+" INTEGER PRIMARY KEY,";
        strCreate+=" "+COMPANY_NUMBER+" TEXT,";
        strCreate+=" "+COMPANY_NAME+" TEXT,";
        strCreate+=" "+C_FIRST_PHONE_NUMBER+" TEXT,";
        strCreate+=" "+C_SECOND_PHONE_NUMBER+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_WORKERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_FOOD_COMPANY;
        db.execSQL(strDelete);

        onCreate(db);

    }
}
