package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.d26m01y22.tabales.Workers;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	1.0
 * @since		08/02/2022
 * show details screen.
 */
public class activity_show_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    static int mode, spinner_pos;
    static ArrayList<String> userOptions =new ArrayList<>();

    Spinner optionList;
    LinearLayout order, moreDetails;
    TextView title, sub_info_field;
    ListView screen_list;
    Switch order_pointer;

    SQLiteDatabase db;
    HelperDB hlp;
    static Cursor crsr;
    static ArrayList<String> tbl = new ArrayList<>();
    static ArrayList<String> sub_tbl = new ArrayList<>();
    static ArrayList<Integer>d1 =new ArrayList<>();
    static ArrayList<Integer>d2 =new ArrayList<>();
    static ArrayList<String>d3 =new ArrayList<>();
    static ArrayList<Integer> d3_index_helper =new ArrayList<>();
    static ArrayList<String>d3helper_copy =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        optionList = (Spinner) findViewById(R.id.sort_list_options);
        order = (LinearLayout) findViewById(R.id.orderLyaut);
        moreDetails = (LinearLayout) findViewById(R.id.moreDetails);
        title = (TextView) findViewById(R.id.titleShowDetails);
        sub_info_field = (TextView) findViewById(R.id.f1);
        screen_list = (ListView) findViewById(R.id.show_filed);
        order_pointer = (Switch) findViewById(R.id.switch_order);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        connect_spinner_values();


        order.setVisibility(View.INVISIBLE);
        moreDetails.setVisibility(View.INVISIBLE);
        tbl.clear();
        sub_tbl.clear();
        d1.clear();
        d2.clear();
        d3.clear();
        d3_index_helper.clear();
        d3helper_copy.clear();
        if (mode == 0){
            title.setText("show details of: worker");
            db = hlp.getWritableDatabase();
            crsr = db.query(Workers.TABLE_WORKERS, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Workers.CARD_ID);
            int col2 = crsr.getColumnIndex(Workers.LAST_NAME);
            int col3 = crsr.getColumnIndex(Workers.NAME);
            int col4 = crsr.getColumnIndex(Workers.WORKER_COMPANY);
            int col5 = crsr.getColumnIndex(Workers.PERSONAL_ID);
            int col6 = crsr.getColumnIndex(Workers.PHONE_NUMBER);
            int col7 = crsr.getColumnIndex(Workers.IS_WORKING);
            crsr.moveToFirst();
            int index = 0;
            while (!crsr.isAfterLast()) {
                String details;
                String sub_details;
                String card = crsr.getString(col1);
                String last_name =  crsr.getString(col2);
                String first_name =  crsr.getString(col3);
                String worker_company =  crsr.getString(col4);
                String personal_id =  crsr.getString(col5);
                String phone_number =  crsr.getString(col6);
                int work_mode =  crsr.getInt(col7);
                if (work_mode == 1){
                    details = ""+personal_id+", "+first_name+", "+last_name+", "+worker_company;
                    sub_details = ""+card+", "+phone_number+", working";
                    Log.e("activity_show_details",String.valueOf(index));
                    d1.add(index);
                }else{
                   details = ""+personal_id+", "+first_name+" "+last_name+", "+worker_company;
                    sub_details = ""+card+", "+phone_number+", not working";
                    d2.add(index);
                }
                index++;
                tbl.add(details);
                sub_tbl.add(sub_details);
                d3.add(first_name+" "+last_name);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            d3helper_copy = d3;
        }else if (mode == 1){
            title.setText("show details of: orders");
        }else if (mode == 2){
            title.setText("show details of: food companies");
        }

        optionList.setOnItemSelectedListener(this);
        ArrayAdapter<String> adp_spinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, userOptions);
        optionList.setAdapter(adp_spinner);

        screen_list.setOnItemClickListener(this);
        screen_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        screen_list.setAdapter(adp_list);



    }

    public static void connect_spinner_values(){
        userOptions.clear();
        userOptions.add("all:");
        if (mode == 0){
            userOptions.add("Show only active employees");
            userOptions.add("Show only inactive employees");
            userOptions.add("sort by name");
        }else if (mode == 1){
            userOptions.add("Show by time frame");
            userOptions.add("company");
            userOptions.add("main course");
        }else if (mode == 2){
            userOptions.add("Show only active food companies");
            userOptions.add("Show only inactive food companies");
            userOptions.add("sort by name");
        }
    }
    public static ArrayList<String> mirror(ArrayList<String> arr){
        ArrayList<String> rev = new ArrayList<String>();
        for (int i = arr.size()-1;i>=0;i--){
            rev.add(arr.get(i));
        }
        return rev;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        moreDetails.setVisibility(View.VISIBLE);
        if (mode == 0){
            Log.e("activity_show_details",String.valueOf(spinner_pos));
            if (spinner_pos == 0){
                sub_info_field.setText(sub_tbl.get(position));

            }else if (spinner_pos == 1){
                sub_info_field.setText(sub_tbl.get(d1.get(position)));
                // fix it to search by string like in the third
            }else if (spinner_pos == 2){
                sub_info_field.setText(sub_tbl.get(d2.get(position)));
                // fix it to search by string like in the third
            }else{
                sub_info_field.setText(sub_tbl.get(d3_index_helper.get(position)));
            }
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> result =new ArrayList<>();
        order.setVisibility(View.INVISIBLE);
        if (mode == 0){
            if (position == 0){
                spinner_pos = position;
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                screen_list.setAdapter(adp_list);
            }else if(position == 1){
                for (int i =0;i<d1.size();i++){
                    result.add(tbl.get(d1.get(i)));
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);

            }else if(position == 2){
                spinner_pos = position;
                for (int i =0;i<d2.size();i++){
                    result.add(tbl.get(d2.get(i)));
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }else{
                spinner_pos = position;
                order.setVisibility(View.VISIBLE);
                d3helper_copy = new ArrayList<String>(d3);
                Collections.sort(d3);
                if (order_pointer.isChecked()){
                    d3_index_helper.clear();
                    d3 = mirror(d3);
                }
                organize_after_shorting();
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, d3);
                screen_list.setAdapter(adp_list);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void organize_after_shorting(){
        for (int i =0;i<d3helper_copy.size();i++){
            String current = d3.get(i);
            for (int j=0;j<d3helper_copy.size();j++){
                if (current.equals(d3helper_copy.get(j))){
                    d3_index_helper.add(j);
                }
            }
        }
    }

    public void actionToDiffrence_sw(View view) {
        if (mode == 0 && spinner_pos == 3){
            Collections.sort(d3);
            d3_index_helper.clear();
            if (order_pointer.isChecked()){
                d3 = mirror(d3);
            }
            organize_after_shorting();
            ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, d3);
            screen_list.setAdapter(adp_list);

        }
    }
    public void back_to_main_menu(View view) {
        finish();
    }
}