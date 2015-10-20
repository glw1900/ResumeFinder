package edu.brandeis.resufair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
    public final static String USER_INFO = "edu.brandeis.resufair.userinfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        EditText userEmail = (EditText) findViewById(R.id.userEmail);
        EditText userPassword = (EditText) findViewById(R.id.userPassword);
        String[] message = {userEmail.getText().toString(), userPassword.getText().toString()};
        intent.putExtra(USER_INFO, message);
        startActivity(intent);
    }

    public void signUp(View view) {

    }
}
