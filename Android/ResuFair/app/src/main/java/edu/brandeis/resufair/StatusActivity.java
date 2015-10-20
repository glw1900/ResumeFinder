package edu.brandeis.resufair;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.HashMap;
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
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");
        //   Log.v("HashMapTest", hashMap.get("key"));

        TextView name = (TextView) findViewById(R.id.status_name);
        TextView birthday = (TextView) findViewById(R.id.status_birthday);
        TextView college = (TextView) findViewById(R.id.status_college);
        TextView major = (TextView) findViewById(R.id.status_major);
        TextView gpa = (TextView) findViewById(R.id.status_gpa);

        name.setText(hashMap.get("name"));
        birthday.setText(hashMap.get("birthday"));
        college.setText(hashMap.get("college"));
        major.setText(hashMap.get("major"));
        gpa.setText(hashMap.get("gpa"));
//        String[] message = intent.getStringArrayExtra(MainActivity.USER_INFO);
//
//        TextView username = (TextView) findViewById(R.id.status_username);
//        TextView password = (TextView) findViewById(R.id.status_password);
//
//        username.setText(message[0]);
//        password.setText(message[1]);
    }

}
