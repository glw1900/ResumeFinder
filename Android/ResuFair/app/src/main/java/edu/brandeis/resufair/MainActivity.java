package edu.brandeis.resufair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    public final static String USER_INFO = "edu.brandeis.resufair.userinfo";
    public final static String SERVER = "SERVER";
    private String userType;
    private ServerAPI server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUserInputFields();
        server = new ServerAPI();
    }

    private void initUserInputFields() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        String storedUsername = pref.getString("username", "");
        userType = pref.getString("userType", "");
        EditText userEmail = (EditText) findViewById(R.id.user_email_text_view_main);

        RadioButton button1 = (RadioButton) findViewById(R.id.radio_button1_main);
        RadioButton button2 = (RadioButton) findViewById(R.id.radio_button2_main);
        if (!storedUsername.equals("")) {
            userEmail.setText(storedUsername);
        }
        if(!userType.equals("")) {
            if (userType.equals(button1.getText().toString())) {
                button1.setChecked(true);
            } else {
                button2.setChecked(true);
            }
        }
    }

    public void signIn(View view) {
        if (userType.equals("")) {
            // display a toast message to remind the user to select a type
            Context context = getApplicationContext();
            String userType1 = getString(R.string.user_type_1);
            String userType2 = getString(R.string.user_type_2);
            CharSequence text = "Are you a " + userType1 + " or " + userType2 + "?";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        } else {
            TextView emailView = (TextView) findViewById(R.id.user_email_text_view_main);
            TextView passwordView = (TextView) findViewById(R.id.user_email_text_view_main);
            String userEmail = emailView.getText().toString();
            String userPassword = passwordView.getText().toString();
            this.storeUserInfo(userEmail);

            server.logIn(userEmail, userPassword, userType);

            if (userType.equals(getString(R.string.user_type_1))) {
                // will be replaced by requesting server
                HashMap<String, String> map = generateTestUserInfo();
                Intent intent = new Intent(this, StatusActivity.class);
                intent.putExtra(USER_INFO, map);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, CompanyStatusActivity.class);
                intent.putExtra(SERVER, server);
                startActivity(intent);
            }
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

    private void storeUserInfo(String username) {

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("userType", userType);
        editor.apply();
    }
}
