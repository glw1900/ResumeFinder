package edu.brandeis.resufair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    ServerAPI server;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        server = ServerAPI.getInstance(this);
        userType = getString(R.string.user_type_1);

    }

    public void authenticate(View view) {
        EditText userEmail = (EditText) findViewById(R.id.userEmail_regis);
        EditText userPassword = (EditText) findViewById(R.id.userPassword_regis);
        EditText userRepassword = (EditText) findViewById(R.id.reUserPassword_regis);
        EditText info1 = (EditText) findViewById(R.id.userInfo1_regis);
        EditText info2 = (EditText) findViewById(R.id.userInfo2_regis);
        Response.Listener<JSONObject> resListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                finish();
            }
        };
        Response.ErrorListener errListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, "Failed, try again", Toast.LENGTH_LONG).show();

            }
        };


        if (userPassword.getText().toString().equals(userRepassword.getText().toString())){
            if(userType.equals(getString(R.string.user_type_1))) {
                server.registerCandidate(userEmail.getText().toString(), userPassword.getText().toString(), info1.getText().toString(), info2.getText().toString(), resListener
                        , errListener);
            } else {
                server.registerCompany(userEmail.getText().toString(), userPassword.getText().toString(), info1.getText().toString(), info2.getText().toString(), resListener
                        , errListener);
            }
            this.finish();
        } else {
            userPassword.setText("");
            userRepassword.setText("");
            Toast.makeText(this, "Password not match,try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        userType = button.getText().toString();
        EditText info1 = (EditText) findViewById(R.id.userInfo1_regis);
        EditText info2 = (EditText) findViewById(R.id.userInfo2_regis);
        if(userType.equals(getString(R.string.user_type_1))) {
            info1.setHint(R.string.candidate_signup_hint1);
            info2.setHint(R.string.candidate_signup_hint2);
        } else {
            info1.setHint(R.string.company_signup_hint1);
            info2.setHint(R.string.company_signup_hint2);
        }
    }
}

