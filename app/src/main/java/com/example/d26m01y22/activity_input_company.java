package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.d26m01y22.tabales.FoodCompany;

import java.util.ArrayList;

import static com.example.d26m01y22.tabales.FoodCompany.*;
import static com.example.d26m01y22.tabales.Workers.TABLE_WORKERS;

public class activity_input_company extends AppCompatActivity {
    int mode;
    String company_name, company_id, phone_number, second_phone_number;

    LinearLayout serviceMode;
    TextView title;
    EditText company_name_field, company_id_field, phone_number_field, second_phone_number_field;

    AlertDialog.Builder adb;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> company_id_tb = new ArrayList<>();
    ArrayList<String> company_name_tb = new ArrayList<>();
    String[] columns = {"COMPANY_NAME","COMPANY_NUMBER"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_company);

        serviceMode = (LinearLayout) findViewById(R.id.serviceLL);

        company_id_field = (EditText) findViewById(R.id.Company_id);
        company_name_field = (EditText) findViewById(R.id.Company_name);
        phone_number_field = (EditText) findViewById(R.id.phone_num);
        second_phone_number_field = (EditText) findViewById(R.id.second_num);

        title = (TextView) findViewById(R.id.titleUpdateCompany);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        serviceMode.setVisibility(View.INVISIBLE);
        if (mode == 0){
               title.setText("add a new food company:");
        }else if (mode == 1){
            title.setText("edit food company details:");
        }
    }
    public int isAlreadyExist(String companyId, String companyName){
        int good = 0;
        company_id_tb = new ArrayList<>();
        company_name_tb = new ArrayList<>();
        db = hlp.getWritableDatabase();
        crsr = db.query(TABLE_FOOD_COMPANY, columns, null, null, null, null, null);
        int col2 = crsr.getColumnIndex(FoodCompany.COMPANY_NAME);
        int col1 = crsr.getColumnIndex(FoodCompany.COMPANY_NUMBER);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String company_id1 = crsr.getString(col1);
            String company_name1 = crsr.getString(col2);
            company_id_tb.add(company_id1);
            company_name_tb.add(company_name1);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        for (int i = 0;i<company_id_tb.size();i++){
            if (companyId.equals(company_id_tb.get(i))){
                good++;
            }
        }
        for (int i = 0;i<company_name_tb.size();i++){
            if (companyName.equals(company_name_tb.get(i))){
                good++;
            }
        }
        return good;
    }
    /**
     * pop error alert dialog massage to the user
     * <p>
     *
     */
    public void popErrorMassage(){
        adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("wrong input!");
        adb.setMessage("you entered wrong input to one or more of the input fields");
        adb.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();

    }

    public void save_data() {
        company_id = company_id_field.getText().toString();
        company_name = company_name_field.getText().toString();
        phone_number = phone_number_field.getText().toString();
        second_phone_number = second_phone_number_field.getText().toString();
        if (check_inputs()) {
            if (mode == 0) {
                ContentValues cv = new ContentValues();
                cv.put(FoodCompany.COMPANY_NAME,company_name);
                cv.put(FoodCompany.COMPANY_NUMBER,company_id);
                cv.put(FoodCompany.C_FIRST_PHONE_NUMBER,phone_number);
                cv.put(FoodCompany.C_SECOND_PHONE_NUMBER,second_phone_number);
                cv.put(FoodCompany.IS_WORKING_COMPANY,1);
                db = hlp.getWritableDatabase();

                db.insert(TABLE_FOOD_COMPANY, null, cv);

                db.close();
                finish();
            }

        } else {
            popErrorMassage();
        }
    }
    public boolean check_inputs(){
        if (company_id.length() == 0 || company_name.length() == 0 || phone_number.length() == 0 || isAlreadyExist(company_id,company_name) !=0){
            return false;
        }
        return true;
    }
    public void back_to_main_menu(View view) {
        finish();
    }

    public void saveWorker(View view) {
        if (mode == 0){
            save_data();
        }
    }
}