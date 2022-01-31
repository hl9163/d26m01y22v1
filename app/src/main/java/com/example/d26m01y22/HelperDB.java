package com.example.d26m01y22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import static com.example.d26m01y22.tabales.Workers.*;
import static com.example.d26m01y22.tabales.FoodCompany.*;
import static com.example.d26m01y22.tabales.Meals.*;
import static com.example.d26m01y22.tabales.Order_Details.*;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	3.0
 * @since		27/01/2022
 * database helper
 */
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
        strCreate+=" ("+KEY_ID_FoodC+" INTEGER PRIMARY KEY,";
        strCreate+=" "+COMPANY_NUMBER+" TEXT,";
        strCreate+=" "+COMPANY_NAME+" TEXT,";
        strCreate+=" "+C_FIRST_PHONE_NUMBER+" TEXT,";
        strCreate+=" "+C_SECOND_PHONE_NUMBER+" TEXT,";
        strCreate+=" "+IS_WORKING_COMPANY+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_MEALS;
        strCreate+=" ("+KEY_ID_MEALS+" INTEGER PRIMARY KEY,";
        strCreate+=" "+APPETIZER+" TEXT,";
        strCreate+=" "+MAIN_COURSE+" TEXT,";
        strCreate+=" "+EXTRA+" TEXT,";
        strCreate+=" "+DESSERT+" TEXT,";
        strCreate+=" "+DRINK+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_ORDER_DETAILS;
        strCreate+=" ("+KEY_ID_OD+" INTEGER PRIMARY KEY,";
        strCreate+=" "+DATE+" TEXT,";
        strCreate+=" "+TIME+" TEXT,";
        strCreate+=" "+WORKER_ID+" TEXT,";
        strCreate+=" "+FFOOD_COMPANY+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_WORKERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_FOOD_COMPANY;
        db.execSQL(strDelete);

        strDelete="DROP TABLE IF EXISTS "+TABLE_MEALS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_ORDER_DETAILS;
        db.execSQL(strDelete);


        onCreate(db);

    }
}
