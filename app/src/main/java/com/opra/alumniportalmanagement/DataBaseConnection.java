package com.opra.alumniportalmanagement;


import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DataBaseConnection {
    //Network Ip
    String URL = "http://192.168.43.111/ALM/";
    JSONParser jsonParser = new JSONParser();


    public int ValidateUser(String email, String password) throws ExecutionException, InterruptedException, JSONException {


        JSONObject jsonObject = new AttemptValidation().execute(email, password).get();
        if (jsonObject != null) {
            return jsonObject.getInt("success");
        }
        return -2;
    }

    private class AttemptValidation extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            URL += "login.php";
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


    public JSONObject getAllAlumnus(String email) throws ExecutionException, InterruptedException, JSONException {

        JSONObject jsonObject = new AttemptGetAllAlumnus().execute(email).get();

        if (jsonObject != null) {
            System.out.println("Get All Alumnus Result:" + jsonObject.toString());
            return jsonObject;
        }
        return null;
    }

    public JSONObject getAllAlumnus(String email, String yearOrCompany, boolean isYear) throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = null;

        if (isYear) {
            jsonObject = new AttemptGetAllAlumnusByYear().execute(email, yearOrCompany).get();
        } else {
            jsonObject = new AttemptGetAllAlumnusByCompany().execute(email, yearOrCompany).get();
        }

        if (jsonObject != null) {
            System.out.println("Get YEAR Alumnus Result:" + jsonObject.toString());
            return jsonObject;
        }
        return null;
    }


    private class AttemptGetAllAlumnus extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL += "getAllAlumnus.php";

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", strings[0]));


            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "GET", params);
            // check log cat from response
            return jsonObject;
        }
    }

    private class AttemptGetAllAlumnusByYear extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL += "getAllAlumnusByYear.php";

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", strings[0]));
            params.add(new BasicNameValuePair("year", strings[1]));
            System.out.println("YEAR AND EMAIL:" + strings[0] + " " + strings[1]);


            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "GET", params);
            // check log cat from response
            System.out.println(jsonObject);
            return jsonObject;
        }
    }

    private class AttemptGetAllAlumnusByCompany extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL += "getAllAlumnusCompany.php";

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", strings[0]));
            params.add(new BasicNameValuePair("company", strings[1]));


            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "GET", params);
            // check log cat from response
            return jsonObject;
        }
    }


    public JSONObject searchAlumniByAlumniId(String email, String alumniId) throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = null;

        jsonObject = new AttemptGetAlumniByAlumniRegID().execute(email, alumniId).get();

        if (jsonObject != null) {
            System.out.println("Search By ID:" + jsonObject.toString());
            return jsonObject;
        }
        return null;
    }

    private class AttemptGetAlumniByAlumniRegID extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL += "getAlumniByAlumniRegID.php";

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", strings[0]));
            params.add(new BasicNameValuePair("AlumniRegId", strings[1]));


            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "GET", params);
            // check log cat from response
            return jsonObject;
        }
    }

    public JSONObject UpdateAlumniRecord(String email, Alumni alumni) throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = null;

        jsonObject = new AttemptUpdateAlumniRecord().execute(email, alumni.alumniRegId,alumni.name, alumni.emailId, alumni.password, alumni.contactNo, alumni.company, alumni.designation, alumni.packageSalary, alumni.coPassword, alumni.year, alumni.department, alumni.profilePic, alumni.linkedInLink).get();


        if (jsonObject != null) {
            System.out.println("Get YEAR Alumnus Result:" + jsonObject.toString()+" "+ alumni.name);
            return jsonObject;
        }
        return null;
    }

    private class AttemptUpdateAlumniRecord extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {

            URL += "UpdateAlumniByAlumniRegID.php";

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", strings[0]));
            params.add(new BasicNameValuePair("AlumniRegId", strings[1]));
            params.add(new BasicNameValuePair("AlumniName", strings[2]));
            params.add(new BasicNameValuePair("EmailID", strings[3]));
            params.add(new BasicNameValuePair("Password", strings[4]));
            params.add(new BasicNameValuePair("ContactNo", strings[5]));
            params.add(new BasicNameValuePair("CompnyNameAdd", strings[6]));
            params.add(new BasicNameValuePair("Designation", strings[7]));
            params.add(new BasicNameValuePair("Package", strings[8]));
            params.add(new BasicNameValuePair("CoPassword", strings[9]));
            params.add(new BasicNameValuePair("PassoutYear", strings[10]));
            params.add(new BasicNameValuePair("Department", strings[11]));
            params.add(new BasicNameValuePair("ProfilePic", strings[12]));
            params.add(new BasicNameValuePair("LnkdInLink", strings[13]));
            //System.out.println(params);

            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "POST", params);
            //System.out.println("CURRENT OUTPUT:"+jsonObject+" "+URL);
            // check log cat from response
            return jsonObject;
        }
    }

}


