
package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	1.0
 * @since		26/01/2022
 * side menu that the user need to choose which details he want to see
 */
public class showDetailsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_menu);
    }

    public void show_workers(View view) {
    }

    public void show_orders(View view) {
    }

    public void show_food_comp(View view) {
    }

    public void back_to_main_menu(View view) {
        finish();
    }
}