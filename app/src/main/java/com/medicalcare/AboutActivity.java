package com.medicalcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void ComposeClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Abdulkareemha.ye@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Toast.makeText(this,"Install Gmail please", Toast.LENGTH_SHORT).show();
        }

    }
}