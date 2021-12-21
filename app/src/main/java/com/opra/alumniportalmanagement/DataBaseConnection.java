package com.opra.alumniportalmanagement;


import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DataBaseConnection {
    String URL = "http://192.168.43.111/ALM/login.php";
    JSONParser jsonParser = new JSONParser();


    public int ValidateUser(String email, String password) throws ExecutionException, InterruptedException, JSONException {


        JSONObject jsonObject = new AttemptValidation().execute(email, password).get();
        if(jsonObject!=null)
        {
            System.out.println("JSON OBJECT:" + jsonObject.toString());
            return jsonObject.getInt("success");
        }
        return -2;
    }
    public interface AsyncResponse{
        void  processFinish(JSONObject output);
    }

    private class AttemptValidation extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];

            System.out.println("VALUES:" + email + " " + password);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));


            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "GET", params);
            // check log cat from response
            return jsonObject;
        }


    }
}


