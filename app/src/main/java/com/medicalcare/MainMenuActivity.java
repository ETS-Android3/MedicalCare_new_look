package com.medicalcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainMenuActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
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

    public void StatusClick(View view) {
        Intent i = new Intent(this, StatusActivityTemp.class);
        startActivity(i);
    }

    public void RecordsClick(View view) {
        Intent i = new Intent(this, RecordsActivity.class);
        startActivity(i);
    }

    public void emerge_click(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Hospital");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else{
            Toast.makeText(this,"Install Google Maps please!",Toast.LENGTH_LONG).show();
        }
    }

}