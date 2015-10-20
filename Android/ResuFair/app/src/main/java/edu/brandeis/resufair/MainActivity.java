package edu.brandeis.resufair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    public final static String USER_INFO = "edu.brandeis.resufair.userinfo";

    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        if (userType == null) {
            // display a toast message to remind the user to select a type
            Context context = getApplicationContext();
            String userType1 = getString(R.string.user_type_1);
            String userType2 = getString(R.string.user_type_2);
            CharSequence text = "Are you a " + userType1 + " or " + userType2 + "?";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        } else {
            Intent intent = new Intent(this, StatusActivity.class);
            EditText userEmail = (EditText) findViewById(R.id.userEmail);
            EditText userPassword = (EditText) findViewById(R.id.userPassword);


            // will be replaced by requesting server
            HashMap<String, String> map = generateTestUserInfo();

            intent.putExtra(USER_INFO, map);
            startActivity(intent);
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        userType = button.getText().toString();
    }

    private HashMap<String, String> generateTestUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "Xiaoming Wang");
        map.put("birthday", "9/9/1999");
        map.put("school", "Brandeis University");
        map.put("major", "Computer Science");
        map.put("gpa", "3.998");
        return map;
    }

    private void stortUserInfo(String name, String password) {
        
    }
}
