package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	1.4
 * @since		27/01/2022
 * side menu that the user need to choose what he want to add or update
 */
public class activity_update_or_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_add);
    }
    /**
     * click the button will take the user to add a new worker
     * mode 0 = add worker
     * mode 1 = update worker details
     * same screen to those two goals.
     * <p>
     *
     * @param	view Description	button add worker
     */
    public void add_worker(View view) {
        Intent si = new Intent(this,activity_input_worker.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to update a worker
     * mode 0 = add worker
     * mode 1 = update worker details
     * same screen to those two goals.
     * <p>
     *
     * @param	view Description	button add worker
     */
    public void update_worker(View view) {
        Intent si = new Intent(this,activity_input_worker.class);
        si.putExtra("mode",1);
        startActivity(si);
    }
    public void make_new_order(View view) {
        Intent si = new Intent(this,activity_input_meal.class);
        startActivity(si);
    }
    /**
     * click the button will take the user to add a new food company
     * mode 0 = add food company
     * mode 1 = update food company details
     * same screen to those two goals.
     * <p>
     *
     * @param	view Description	button add food company
     */
    public void add_food_company(View view) {
        Intent si = new Intent(this,activity_input_company.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to update food company details
     * mode 0 = add food company
     * mode 1 = update food company details
     * same screen to those two goals.
     * <p>
     *
     * @param	view Description	button update food company
     */
    public void update_fc_details(View view) {
        Intent si = new Intent(this,activity_input_company.class);
        si.putExtra("mode",1);
        startActivity(si);
    }

    public void back_to_main_menu(View view) {
        finish();
    }


}