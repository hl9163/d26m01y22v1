package com.example.d26m01y22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class creditsActivity extends AppCompatActivity {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		11/02/2022
     * credits activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }
    /**
     * return the the first activity.
     * <p>
     *
     */
    public void returnFistAc(View view) {
        finish();
    }
}