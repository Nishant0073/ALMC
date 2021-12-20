package com.miniprojectg3.alumniportalmanagement;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InsertData {
    String URL = "http://192.168.43.111/ALM/";
    JSONParser jsonParser = new JSONParser();
    int response=0;


    public int registerCustomer(String name,String password,String department,String email,String role)
    {
        AttemptRegistration attemptRegistration = new AttemptRegistration();
        attemptRegistration.execute(name,password,department,email,role);
        return response;
    }

    private class AttemptRegistration extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String name = strings[0];
            String password = strings[1];
            String department = strings[2];
            String email = strings[3];
            String role = strings[4];
            System.out.println("VALUES:" + name + " " + password + " " + department + " " + email + " " + role);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("department", department));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("role", role));
            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", jsonObject.toString());
            return jsonObject;
        }


        protected void onPostExecute(JSONObject jsonObject) {
                if (jsonObject != null) {
                    response = 1;
                } else {
                    response = 2;
                }
        }
    }
}
