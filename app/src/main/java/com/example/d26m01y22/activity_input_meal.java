package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class activity_input_meal extends AppCompatActivity {
    String appetizer,main_course,extra,dessert,drink;
    EditText app,mC,ex,des,dri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);

        app = (EditText) findViewById(R.id.appetizerF);
        mC = (EditText) findViewById(R.id.main_courseF);
        ex = (EditText) findViewById(R.id.extraF);
        des = (EditText) findViewById(R.id.dessertF);
        dri = (EditText) findViewById(R.id.drinkF);
    }


    public void back_to_main_menu(View view) {
        finish();
    }

    public boolean check_input(){
        if (main_course.length() == 0){
            return false;
        }
        return true;

    }

    public void continueToNextPart(View view) {
        appetizer = app.getText().toString();
        main_course = mC.getText().toString();
        extra = ex.getText().toString();
        dessert = des.getText().toString();
        drink = dri.getText().toString();
        if (check_input()){

        }
    }
}