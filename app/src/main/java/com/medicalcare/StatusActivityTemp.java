package com.medicalcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class StatusActivityTemp extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView heart;
    private TextView heat;
    private TextView oxgen;

    ArrayList<PersonalStatus> pses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        pses = new ArrayList<>();

        heart = (TextView)findViewById(R.id.hbpm);
        oxgen = (TextView)findViewById(R.id.oxygen);
        heat = (TextView)findViewById(R.id.tempreture);

        heart.setText("68");
        oxgen.setText("91");
        heat.setText("30");

        DBHelper db = new DBHelper(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_setting){
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        else if(item.getItemId() == R.id.menu_about){
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pses.size() > 0){
            for(PersonalStatus p : pses){
                DBHelper db = new DBHelper(this);
                db.insertStatus(p);
            }
        }
    }
}



