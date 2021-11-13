package com.medicalcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private Button phoneEdit;
    private  DBHelper db;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        db = new DBHelper(this);

        phoneNumber = (EditText)findViewById(R.id.phone_number);
        phoneNumber.setText(db.getPhoneNumber());

        phoneEdit = (Button)findViewById(R.id.edit_phone);
        phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phoneNumber.isEnabled()) {
                    phoneNumber.setEnabled(true);
                    phoneEdit.setText("Save");
                }
                else{
                    String number = phoneNumber.getText().toString().trim();
                    if(!number.equals("")) {
                        phoneNumber.setEnabled(false);
                        phoneEdit.setText("Edit Phone Number");
                        if(db.update_number(number)){
                            Snackbar.make(v,"Phone number is updated!",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                        }
                    }else{
                        Snackbar.make(v,"Phone number was not provided!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }

                }
            }
        });
    }
}