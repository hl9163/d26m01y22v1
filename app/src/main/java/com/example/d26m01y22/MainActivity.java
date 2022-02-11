package com.example.d26m01y22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	1.3
 * @since		26/01/2022
 * main menu of the program, the user can choose if he wants to see all the details
 * or update them.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * click the button will take the user to the update mode
     * <p>
     *
     * @param	view Description	button
     */

    public void gotoUpdateMenu(View view) {
        Intent si = new Intent(this,activity_update_or_add.class);
        startActivity(si);
    }
    /**
     * click the button will take the user to the show mode
     * <p>
     *
     * @param	view Description	button
     */
    public void gotoDetailsMenu(View view) {
        Intent si = new Intent(this,showDetailsMenuActivity.class);
        startActivity(si);
    }
    /**
     * create the menu.
     * <p>
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /**
     * move to the credits activity.
     * <p>
     *
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.return_mainMenu){
            Intent si = new Intent(this,MainActivity.class);
            startActivity(si);
            return true;
        }else if(id == R.id.credits){
            Intent si = new Intent(this,creditsActivity.class);
            startActivity(si);
            return true;

        }
        return true;
    }
}