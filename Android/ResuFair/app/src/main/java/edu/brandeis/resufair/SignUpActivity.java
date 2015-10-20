package edu.brandeis.resufair;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    public void authenticate(View view) {
        EditText userEmail = (EditText) findViewById(R.id.userEmail_regis);
        EditText userPassword = (EditText) findViewById(R.id.userPassword_regis);
        EditText userRepassword = (EditText) findViewById(R.id.reUserPassword_regis);
        if (userPassword.getText().toString().equals(userRepassword.getText().toString())){
            //write to database
            this.finish();
        } else {
            userPassword.setText("");
            userRepassword.setText("");

            Context context = getApplicationContext();
            CharSequence text = "Password not match,try again.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}

