package edu.brandeis.resufair;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/*This file is pretending an API getting data from server*/


public class ServerAPI {

    static ServerAPI server;
    String userEmail;
    String userPassword;
    String userType;
    RequestSingleton requestSingleton;

    private ServerAPI(Context context) {
        requestSingleton = new RequestSingleton(context);
    }

    static synchronized ServerAPI getInstance(Context context) {
        if (server == null) {
            server = new ServerAPI(context);
        }
        return server;
    }

    public void logIn(String username, String password, String userType) {
        this.userEmail = username;
        this.userPassword = password;
        this.userType = userType;
    }

    public void getCompany(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = "http://resumefinder.herokuapp.com/api/comp_login/comp_email=" + userEmail + "&password=" + userPassword;
        requestSingleton.newRequest(url, listener, errorListener);
    }

    public Candidate getCandidate(Context context) {
        return null;
    }


    public void addNewCandidate(String candidateEmail, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = "http://resumefinder.herokuapp.com/api/request/appl_email=" + candidateEmail + "&comp_email=" + userEmail;
        requestSingleton.newRequest(url, listener, errorListener);
    }
}


class RequestSingleton {
    private static RequestSingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    public RequestSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized RequestSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void newRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Log.e("URLE", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, listener, errorListener);
        this.addToRequestQueue(jsObjRequest);
    }
}