package com.opra.alumniportalmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ReportFragment extends Fragment {
    private View view;
    private ListView listView;
    Button yearButton ;
    Button companyButton ;
    TextView totalPlacedAlumnus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report, container, false);
        totalPlacedAlumnus = (TextView) view.findViewById(R.id.TotalPlaceStudent);
        totalPlacedAlumnus.setVisibility(View.GONE);
        yearButton = view.findViewById(R.id.yearWiseReportButton);
        companyButton = view.findViewById(R.id.companyWiseReportButton);
        companyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getCompanyWiseReport();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getYearWiseReport();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void getCompanyWiseReport() throws JSONException {

        ArrayList<Report> reportList = new ArrayList<>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        String email = loginPrefs.getString("emailId", "NA");

        JSONObject response = null;
        try {
            System.out.println("HERE TO FIND YEAR WISE REPORT:" + email);
            response = dataBaseConnection.getYearOrCompanyWiseReport(email, "0");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("Response:" + response);
        if (response == null) {
            Toast.makeText(getActivity(), "\"Unable to connect DB!\"", Toast.LENGTH_SHORT).show();
        } else {
            if (response.getInt("success") == 1) {
                // Toast.makeText(getActivity(), "Alumnus Data Found!", Toast.LENGTH_SHORT).show();
                Map<String, Integer> mp = new LinkedHashMap<String, Integer>();
                int n = response.length();
                System.out.println("Value of N:" + n);
                for (int i = 0; i < n - 3; i++) {
                    if (mp.containsKey(response.getString(String.valueOf(i)))) {
                        mp.put(response.getString(String.valueOf(i)), mp.get(response.getString(String.valueOf(i))) + 1);
                    } else {
                        mp.put(response.getString(String.valueOf(i)), 1);
                    }

                }
                int totalPlacedCnt = 0;
                for (Map.Entry<String, Integer> entry : mp.entrySet()) {
                    reportList.add(new Report(entry.getKey(), entry.getValue()));
                    totalPlacedCnt+=entry.getValue();
                }

                System.out.println("SIze of list:"+reportList.size());
                ReportAdapter reportAdapter = new ReportAdapter(getActivity(), reportList);
                listView = (ListView) getActivity().findViewById(R.id.CompanyWisePlaceList);
                listView.setAdapter(reportAdapter);
                totalPlacedAlumnus.setVisibility(View.VISIBLE);
                totalPlacedAlumnus.setText("Total count of placed alumnus is :  "+ totalPlacedCnt);
            } else {
                // SearchAlumniListSection.setVisibility(LinearLayout.INVISIBLE);
                Toast.makeText(getActivity(), "No Year Wise Record Found!", Toast.LENGTH_SHORT).show();
                totalPlacedAlumnus.setVisibility(View.GONE);
            }
        }

    }

    private void getYearWiseReport() throws JSONException {

        ArrayList<Report> reportList = new ArrayList<>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        String email = loginPrefs.getString("emailId", "NA");

        JSONObject response = null;
        try {
            System.out.println("HERE TO FIND YEAR WISE REPORT:" + email);
            response = dataBaseConnection.getYearOrCompanyWiseReport(email, "1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("Response:" + response);
        if (response == null) {
            Toast.makeText(getActivity(), "\"Unable to connect DB!\"", Toast.LENGTH_SHORT).show();
        } else {
            if (response.getInt("success") == 1) {
                // Toast.makeText(getActivity(), "Alumnus Data Found!", Toast.LENGTH_SHORT).show();
                Map<String, Integer> mp = new HashMap<>();
                int n = response.length();
                System.out.println("Value of N:" + n);
                for (int i = 0; i < n - 3; i++) {
                    if (mp.containsKey(response.getString(String.valueOf(i)))) {
                        mp.put(response.getString(String.valueOf(i)), mp.get(response.getString(String.valueOf(i))) + 1);
                    } else {
                        mp.put(response.getString(String.valueOf(i)), 1);
                    }

                }
                int totalPlacedCnt = 0;
                for (Map.Entry<String, Integer> entry : mp.entrySet()) {
                    reportList.add(new Report(entry.getKey(), entry.getValue()));
                    totalPlacedCnt+=entry.getValue();
                }

                System.out.println("SIze of list:"+reportList.size());
                ReportAdapter reportAdapter = new ReportAdapter(getActivity(), reportList);
                listView = (ListView) getActivity().findViewById(R.id.CompanyWisePlaceList);
                listView.setAdapter(reportAdapter);
                totalPlacedAlumnus.setVisibility(View.VISIBLE);
                totalPlacedAlumnus.setText("Total count of placed alumnus is :  "+ totalPlacedCnt);
            } else {
                // SearchAlumniListSection.setVisibility(LinearLayout.INVISIBLE);
                totalPlacedAlumnus.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Year Wise Record Found!", Toast.LENGTH_SHORT).show();
            }
        }

    }

}