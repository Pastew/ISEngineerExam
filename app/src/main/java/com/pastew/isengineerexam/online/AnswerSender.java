package com.pastew.isengineerexam.online;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pastew on 2015-12-13.
 */
public class AnswerSender {

    Context context;
    String user_id;

    public AnswerSender(Context context) {
        this.context = context;
        user_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public void sendAnswer(final int questionId, final String answer){

        // Create request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //  Create json array request
        JSONObject params = new JSONObject();
        try {
            params.put("question_id", questionId);
            params.put("answer", answer);
            params.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();
        try{
            data.put("answer", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://gcweb.drl.pl/is_exam/save.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("ANSWER_SENDER", "response: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ANSWER_SENDER", "error response: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("question_id", Integer.toString(questionId));
                params.put("answer", answer);
                params.put("user_id", user_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // add json array request to the request queue
        requestQueue.add(stringRequest);
    }
}
