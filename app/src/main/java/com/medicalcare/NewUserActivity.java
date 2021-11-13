package com.medicalcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class NewUserActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private EditText confPassword;
    private EditText phoneNumber;

    private Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        name = (EditText)findViewById(R.id.new_name);
        password = (EditText)findViewById(R.id.new_passowrd);
        confPassword = (EditText)findViewById(R.id.conf_passowrd);
        phoneNumber = (EditText)findViewById(R.id.phone_number);

        create = (Button)findViewById(R.id.create_user);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getBaseContext());
                if(isValid(name)) {
                    if(!db.isUserExist(name.getText().toString())) {
                        if (isValid(password) && isValid(confPassword) && isValid(phoneNumber)) {
                                if(confPassword.getText().toString().trim().equals(password.getText().toString().trim())) {
                                    if (db.insert_user(name.getText().toString(), password.getText().toString(),phoneNumber.getText().toString())) { //trimming is made inside DBHelper
                                        Toast.makeText(getBaseContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Something got wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Snackbar.make(v,"doesn't match password",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                }
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "User is already exits!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    boolean isValid(EditText t){
        if(t.getText().toString().trim().equals("")){
            Toast.makeText(getBaseContext(), "One of the fields are empty", Toast.LENGTH_SHORT).show();
            //t.requestFocus();
            return false;
        }

         return true;
    }
}