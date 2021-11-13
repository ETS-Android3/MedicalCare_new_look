package com.medicalcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;

    public static int USER_ID;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper db = new DBHelper(this);
        db.flush();

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.passowrd);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getBaseContext());
                if(isValid(name)){
                    if(db.isUserExist(name.getText().toString())){
                        if(isValid(password)) {
                            int id = db.isPasswordCorrect(name.getText().toString(), password.getText().toString());
                            if (id != -1) {
                                Intent i = new Intent(getBaseContext(), MainMenuActivity.class);
                                USER_ID = id;
                                startActivity(i);
                            } else {
                                Toast.makeText(getBaseContext(), "Password is incorrect.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Username doesn't exist.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void NewUser(View view) {
        Intent i = new Intent(this,NewUserActivity.class);
        startActivity(i);
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