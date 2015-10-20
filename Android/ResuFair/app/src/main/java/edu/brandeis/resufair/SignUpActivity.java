package edu.brandeis.resufair;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    protected void authenticate(View view) {
        EditText userEmail = (EditText) findViewById(R.id.userEmail);
        EditText userPassword = (EditText) findViewById(R.id.userPassword);
        EditText userRepassword = (EditText) findViewById(R.id.reUserPassword);
        if (userPassword.equals(userRepassword)){
            //write to database
            this.finish();
        } else {
            userPassword.setText("");
            userRepassword.setText("");
        }
    }
}

