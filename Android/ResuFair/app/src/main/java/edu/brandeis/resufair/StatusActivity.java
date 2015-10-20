package edu.brandeis.resufair;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Get the message from the intent
        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra(MainActivity.USER_INFO);

        TextView username = (TextView) findViewById(R.id.status_username);
        TextView password = (TextView) findViewById(R.id.status_password);

        username.setText(message[0]);
        password.setText(message[1]);
    }

}
