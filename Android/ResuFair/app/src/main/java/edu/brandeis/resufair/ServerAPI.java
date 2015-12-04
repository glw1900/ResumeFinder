package edu.brandeis.resufair;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

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

    public void logIn(Context context, String username, String password, String userType) {
        this.userEmail = username;
        this.userPassword = password;
        this.userType = userType;
    }

    public void getCompany(Context context, AsyncResponse delegate) {
        String url = "http://resumefinder.herokuapp.com/api/comp_login/comp_email=" + userEmail + "&password=" + userPassword;
        AsyncTask task = new RequestTask(context, this.requestSingleton, delegate).execute(url);
    }

    public Candidate getCandidate(Context context) {
        return null;
    }


    public boolean addNewCandidate(Context context, String candidateEmail, AsyncResponse delegate) {
        String url = "http://resumefinder.herokuapp.com/api/request/appl_email=" + userEmail + "&comp_email=" + candidateEmail;
        AsyncTask task = new RequestTask(context, this.requestSingleton, delegate).execute(url);
        try {
            JSONObject result = (JSONObject) task.get();
            try {
                if (result.get("status").equals("true")) {
                    return true;
                }
            } catch (JSONException e) {
                return false;
            }
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (ExecutionException e) {
            return false;
        }
    }
}

class RequestTask extends AsyncTask<String, Void, JSONObject> {
    public AsyncResponse delegate = null;
    private ProgressDialog dialog;
    private Context context;
    private RequestSingleton requestSingleton;

    public RequestTask(Context context, RequestSingleton requestSingleton, AsyncResponse delegate) {
        this.context = context;
        dialog = new ProgressDialog(context);
        this.requestSingleton = requestSingleton;
        this.delegate = delegate;

    }

    protected void onPreExecute() {
        this.dialog.setMessage("Getting information...");
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(final JSONObject result) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (result == null || result.length() == 0) {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        } else {
            Log.i("JSON returned", result.toString());
            delegate.processFinish(result);
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }
    }

    protected JSONObject doInBackground(final String... args) {
        try {
            return requestSingleton.newRequest(args[0]);
        } catch (Exception e) {
            Log.e("tag1231231231233123123", "errortag1231231231233123123", e);
            return null;
        }
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

    public JSONObject newRequest(String url) {
        Log.v("ttttttttREquest", url);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
        this.addToRequestQueue(request);
        try {
            return future.get(); // this will block
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        }
        // Access the RequestQueue through your singleton class.
    }

    public JSONObject newRequest(String url, JSONObject postJson) {
        Log.v("ttttttttREquest", url);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, postJson, future, future);
        this.addToRequestQueue(request);
        try {
            return future.get(); // this will block
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        }
        // Access the RequestQueue through your singleton class.
    }


}


