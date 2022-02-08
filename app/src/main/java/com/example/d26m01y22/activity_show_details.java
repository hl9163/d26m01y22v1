package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	1.0
 * @since		08/02/2022
 * show details screen.
 */
public class activity_show_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static int mode;
    static ArrayList<String> userOptions =new ArrayList<>();;

    Spinner optionList;
    LinearLayout order, moreDetails;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        optionList = (Spinner) findViewById(R.id.sort_list_options);
        order = (LinearLayout) findViewById(R.id.orderLyaut);
        moreDetails = (LinearLayout) findViewById(R.id.moreDetails);
        title = (TextView) findViewById(R.id.titleShowDetails);



        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        connect_spinner_values();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, userOptions);
        optionList.setAdapter(adp);

        order.setVisibility(View.INVISIBLE);
        moreDetails.setVisibility(View.INVISIBLE);
        if (mode == 0){
            title.setText("show details of: worker");
        }else if (mode == 1){
            title.setText("show details of: orders");
        }else if (mode == 2){
            title.setText("show details of: food companies");
        }

    }

    public static void connect_spinner_values(){
        userOptions.clear();
        userOptions.add("choose an option:");
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void back_to_main_menu(View view) {
        finish();
    }
}