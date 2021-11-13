package com.medicalcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private TableLayout table;
    private ArrayList<PersonalStatus> pses;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        DBHelper db = new DBHelper(this);
        pses = new ArrayList<>();
        for(int i = 5; i < 10; i++) {
            PersonalStatus p = new PersonalStatus();
            p.date = "2021-07-0" + (i+1);
            int upper = 38;
            int lower = 30;
            p.tempreture = (int) (lower + Math.random() * (upper - lower)) + "";
            upper = 98;
            lower = 90;
            p.oxygen = (int) (lower + Math.random() * (upper - lower)) + "";
            upper = 80;
            lower = 60;
            p.hbpm = (int) (lower + Math.random() * (upper - lower)) + "";
            pses.add(p);
        }
        table = (TableLayout) findViewById(R.id.records_table);

        fillTable();
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

    private void fillTable(){
        if(pses != null && pses.size() > 0){
            for(PersonalStatus p : pses){
                LayoutInflater inflater = LayoutInflater.from(this);
                View v = inflater.inflate(R.layout.view_record,null);
                TextView  day = (TextView) v.findViewById(R.id.day_record);
                day.setText(p.date);

                TextView  hbpm = (TextView) v.findViewById(R.id.hbpm_record);
                hbpm.setText(p.hbpm);

                TextView  oxygen = (TextView) v.findViewById(R.id.oxygen_record);
                oxygen.setText(p.oxygen);

                TextView  temprature = (TextView) v.findViewById(R.id.tempreture_record);
                temprature.setText(p.tempreture);

                table.addView(v);
            }
        }
    }
}