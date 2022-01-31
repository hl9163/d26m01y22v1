package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.d26m01y22.tabales.FoodCompany;
import com.example.d26m01y22.tabales.Meals;
import com.example.d26m01y22.tabales.Order_Details;
import com.example.d26m01y22.tabales.Workers;

import static com.example.d26m01y22.tabales.FoodCompany.*;
import static com.example.d26m01y22.tabales.Meals.*;
import static com.example.d26m01y22.tabales.Order_Details.TABLE_ORDER_DETAILS;
import static com.example.d26m01y22.tabales.Workers.*;

import java.util.ArrayList;

public class activity_complete_order extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner companyView;
    EditText workerIdF, dateF, timeF;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    String[] columnsCompany = {"COMPANY_NAME"};
    String[] columnsWorker = {"PERSONAL_ID"};
    ArrayList<String> companyNames = new ArrayList<>();
    ArrayList<String> worker_id_tb = new ArrayList<>();

    String appetizer,main_course,extra,dessert,drink;
    String company, workerId, date, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        workerIdF = (EditText) findViewById(R.id.worker_idF);
        dateF = (EditText) findViewById(R.id.dateF);
        timeF = (EditText) findViewById(R.id.timeF);


        companyView = (Spinner) findViewById(R.id.company_list);

        companyView.setOnItemSelectedListener(this);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        Intent gi = getIntent();
        appetizer = gi.getStringExtra("appetizer");
        main_course = gi.getStringExtra("main_course");
        extra = gi.getStringExtra("extra");
        dessert = gi.getStringExtra("dessert");
        drink = gi.getStringExtra("drink");

        companyNames.add("chose a food company:");
        showCompanies();


    }
    public void showCompanies(){
        db = hlp.getWritableDatabase();
        crsr = db.query(TABLE_FOOD_COMPANY,columnsCompany,null,null,null,null,null);
        int col1 = crsr.getColumnIndex(FoodCompany.COMPANY_NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String name = crsr.getString(col1);
            companyNames.add(name);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,companyNames);
        companyView.setAdapter(adp);
    }

    public void save_meal(){
        ContentValues cv = new ContentValues();
        cv.put(Meals.APPETIZER,appetizer);
        cv.put(Meals.MAIN_COURSE,main_course);
        cv.put(Meals.EXTRA,extra);
        cv.put(Meals.DESSERT,dessert);
        cv.put(Meals.DRINK,drink);
        db = hlp.getWritableDatabase();

        db.insert(TABLE_MEALS,null, cv);

        db.close();
    }
    public void save_details(){
        ContentValues cv = new ContentValues();
        cv.put(Order_Details.DATE,date);
        cv.put(Order_Details.TIME,time);
        cv.put(Order_Details.WORKER_ID,workerId);
        cv.put(Order_Details.FFOOD_COMPANY,company);
        db = hlp.getWritableDatabase();

        db.insert(TABLE_ORDER_DETAILS, null, cv);

        db.close();
    }

    public boolean isAlreadyExist(String worker){
        worker_id_tb = new ArrayList<>();
        db = hlp.getWritableDatabase();
        crsr = db.query(TABLE_WORKERS, columnsWorker, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Workers.PERSONAL_ID);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String id = crsr.getString(col1);
            worker_id_tb.add(id);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        for (int i = 0; i< worker_id_tb.size(); i++){
            if (worker.equals(worker_id_tb.get(i))){
                return true;
            }
        }
        return false;
    }
    public boolean check_date(){
        String dd;
        String mm;
        String yy;
        if (date.length() == 8){
            dd = date.substring(0,2);
            if (!is_number(dd)){
                return false;
            }
            int day = Integer.parseInt(dd);
            if (day < 0 || day>31){
                return false;
            }
            mm = date.substring(3,5);
            if (!is_number(mm)){
                return false;
            }
            int month = Integer.parseInt(mm);
            if (month < 1 || month>12){
                return false;
            }
            yy = date.substring(6,8);
            if (!is_number(yy)){
                return false;
            }
        }
        return true;
    }
    public boolean is_number(String num){
        try {
            int value = Integer.parseInt(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public boolean check_time(){
        String hh;
        String mm;
        if (time.length() == 5){
            hh = time.substring(0,2);
            if (!is_number(hh)){
                return false;
            }
            int hours = Integer.parseInt(hh);
            if (hours >16 || hours < 12){
                return false;
            }
            mm = time.substring(3,5);
            if (!is_number(mm)){
                return false;
            }
            int minute = Integer.parseInt(mm);
            if (minute<0 || minute>59){
                return false;
            }
        }
        return true;
    }
    public boolean check_input(){
        if (company.equals("None") || !check_date() || !check_time() || !isAlreadyExist(workerId)){
            return false;
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0){
            company = companyNames.get(position-1);
        }else{
            company = "None";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void save_order(View view) {
        workerId = workerIdF.getText().toString();
        date = dateF.getText().toString();
        time = timeF.getText().toString();
        if (check_input()){
            save_details();
            save_meal();
            Intent si = new Intent(this,MainActivity.class);
            startActivity(si);
        }
    }

    public void back_to_main_menu(View view) {
        finish();
    }
}