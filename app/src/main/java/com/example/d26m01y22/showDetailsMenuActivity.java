
package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	2.0
 * @since		26/01/2022
 * side menu that the user need to choose which details he want to see.
 */
public class showDetailsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_menu);
    }
    /**
     * click the button will take the user to see all the details of workers
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * same screen to this goals.
     * <p>
     *
     * @param	view Description	button show worker details
     */
    public void show_workers(View view) {
        Intent si = new Intent(this,activity_show_details.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to see all the details of orders
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * same screen to this goals.
     * <p>
     *
     * @param	view Description	button show order details
     */
    public void show_orders(View view) {
        Intent si = new Intent(this,activity_show_details.class);
        si.putExtra("mode",1);
        startActivity(si);
    }
    /**
     * click the button will take the user to see all the details of food companies
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * same screen to this goals.
     * <p>
     *
     * @param	view Description	button show food company details
     */
    public void show_food_comp(View view) {
        Intent si = new Intent(this,activity_show_details.class);
        si.putExtra("mode",2);
        startActivity(si);
    }

    public void back_to_main_menu(View view) {
        finish();
    }
}