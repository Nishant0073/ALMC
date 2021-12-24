package com.opra.alumniportalmanagement;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class SearchAlumniFragment extends Fragment {

    private ListView searchByListView;
    private View view;
    private AutoCompleteTextView filterByMenu;
    private AutoCompleteTextView yearMenu;
    private AutoCompleteTextView companyMenu;
    private String filterBy;
    private TextInputLayout companyPicker;
    private TextInputLayout yearPicker;
    private RelativeLayout SearchAlumniListSection;


    public SearchAlumniFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_alumni, container, false);

        String[] filterArray = {"Show All Alumnus", "Graduation Year", "Placed Company"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.filter_by_item, filterArray);
        filterByMenu = view.findViewById(R.id.autoCompleteTextView);
        filterByMenu.setAdapter(adapter);

        ArrayList<String> yearList = new ArrayList<String>();

        for (int i = 2003; i < Calendar.getInstance().get(Calendar.YEAR); i++) {
            String tYear = String.valueOf(i);
            yearList.add(tYear);
        }


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_by_item, yearList);
        yearMenu = view.findViewById(R.id.autoCompleteYearPicker);
        yearMenu.setAdapter(yearAdapter);
        yearPicker = view.findViewById(R.id.yearPicker);
        yearPicker.setVisibility(View.GONE);

        yearMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(), yearList.get(position), Toast.LENGTH_SHORT).show();
                SearchAlumniListSection.setVisibility(RelativeLayout.GONE);

                try {
                    showAllAlumnusByYear(yearList.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        String[] companyList = {"Google", "Facebook", "Amazon", "Tesla", "IBM","Ferrari"};


        ArrayAdapter<String> companyAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_by_item, companyList);
        companyMenu = view.findViewById(R.id.autoCompleteCompanyPicker);
        companyMenu.setAdapter(companyAdapter);
        companyPicker = view.findViewById(R.id.companyPicker);
        companyPicker.setVisibility(View.GONE);

        companyMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), companyList[position], Toast.LENGTH_SHORT).show();
                SearchAlumniListSection.setVisibility(RelativeLayout.GONE);
                try {
                    showAllAlumnusByCompany(companyList[position]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SearchAlumniListSection = (RelativeLayout) view.findViewById(R.id.SearchAlumniListSection);
        SearchAlumniListSection.setVisibility(RelativeLayout.GONE);

        filterByMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        filterBy = "ALL";
                        yearPicker.setVisibility(View.GONE);
                        companyPicker.setVisibility(View.GONE);
                        //SearchAlumniListSection.setVisibility(LinearLayout.GONE);
                        try {
                            showAllAlumnus();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        filterBy = "YEAR";
                        companyPicker.setVisibility(View.GONE);
                        yearPicker.setVisibility(View.VISIBLE);
                        SearchAlumniListSection.setVisibility(RelativeLayout.GONE);
                        break;
                    case 2:
                        filterBy = "COMPANY";
                        yearPicker.setVisibility(View.GONE);
                        companyPicker.setVisibility(View.VISIBLE);
                        SearchAlumniListSection.setVisibility(RelativeLayout.GONE);
                        break;
                }
            }
        });


        return view;
    }

    private void showAllAlumnus() throws JSONException {

        SearchAlumniListSection.setVisibility(LinearLayout.VISIBLE);

        ArrayList<Alumni> alumnus = new ArrayList<Alumni>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        String email = loginPrefs.getString("emailId", "NA");

        JSONObject response = null;
        try {
            System.out.println("Email:"+email);
            response = dataBaseConnection.getAllAlumnus(email);
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
            if (response.getInt("success")==1) {
               // Toast.makeText(getActivity(), "Alumnus Data Found!", Toast.LENGTH_SHORT).show();
                int n = response.length();
                System.out.println("Value of N:"+n);
                for(int i=0;i<n-3;i++)
                {
                    JSONObject json = (JSONObject) response.getJSONObject(String.valueOf(i));
                   // System.out.println("FROM JSON:"+json);
                    alumnus.add(new Alumni(json.getString("RegID"),json.getString("AlmniRegID"),json.getString("AlmniName"),json.getString("EmailID"), json.getString("Password"), json.getString("ContactNo"), json.getString("CompyNameAdd"),json.getString("Designation"),json.getString("Package"),json.getString("CoPassword"),json.getString("PassoutYear"),json.getString("Department"),json.getString("ProfilePic"), json.getString("LnkdInLinK")));
                }

                AlumniAdapter alumniAdapter = new AlumniAdapter(getActivity(), alumnus);
                ListView listView = (ListView) getActivity().findViewById(R.id.AlumniList);
                listView.setAdapter(alumniAdapter);

            } else {
                SearchAlumniListSection.setVisibility(LinearLayout.INVISIBLE);
                Toast.makeText(getActivity(), "No Alumnus Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void showAllAlumnusByYear(String year) throws JSONException {

        SearchAlumniListSection.setVisibility(LinearLayout.VISIBLE);
        ArrayList<Alumni> alumnus = new ArrayList<Alumni>();


        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        String email = loginPrefs.getString("emailId", "NA");

        JSONObject response = null;
        try {
            System.out.println("Email:"+email);
            response = dataBaseConnection.getAllAlumnus(email,year,true);
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
            if (response.getInt("success")==1) {
                // Toast.makeText(getActivity(), "Alumnus Data Found!", Toast.LENGTH_SHORT).show();
                int n = response.length();
                System.out.println("Value of N:"+n);
                for(int i=0;i<n-3;i++)
                {
                    JSONObject json = (JSONObject) response.getJSONObject(String.valueOf(i));
                    System.out.println("FROM YEAR JSON:"+json);
                    alumnus.add(new Alumni(json.getString("RegID"),json.getString("AlmniRegID"),json.getString("AlmniName"),json.getString("EmailID"), json.getString("Password"), json.getString("ContactNo"), json.getString("CompyNameAdd"),json.getString("Designation"),json.getString("Package"),json.getString("CoPassword"),json.getString("PassoutYear"),json.getString("Department"),json.getString("ProfilePic"), json.getString("LnkdInLinK")));
                }
                AlumniAdapter alumniAdapter = new AlumniAdapter(getActivity(), alumnus);
                ListView listView = (ListView) getActivity().findViewById(R.id.AlumniList);
                listView.setAdapter(alumniAdapter);

            } else {
                SearchAlumniListSection.setVisibility(LinearLayout.INVISIBLE);
                Toast.makeText(getActivity(), "No Alumnus Found!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void showAllAlumnusByCompany(String company) throws JSONException {

        SearchAlumniListSection.setVisibility(LinearLayout.VISIBLE);
        ArrayList<Alumni> alumnus = new ArrayList<Alumni>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        String email = loginPrefs.getString("emailId", "NA");

        JSONObject response = null;
        try {
            System.out.println("Email:"+email);
            response = dataBaseConnection.getAllAlumnus(email,company,false);
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
            if (response.getInt("success")==1) {
                // Toast.makeText(getActivity(), "Alumnus Data Found!", Toast.LENGTH_SHORT).show();
                int n = response.length();
                System.out.println("Value of N:"+n);
                for(int i=0;i<n-3;i++)
                {
                    JSONObject json = (JSONObject) response.getJSONObject(String.valueOf(i));
                    System.out.println("FROM  COMPANY JSON:"+json);
                    alumnus.add(new Alumni(json.getString("RegID"),json.getString("AlmniRegID"),json.getString("AlmniName"),json.getString("EmailID"), json.getString("Password"), json.getString("ContactNo"), json.getString("CompyNameAdd"),json.getString("Designation"),json.getString("Package"),json.getString("CoPassword"),json.getString("PassoutYear"),json.getString("Department"),json.getString("ProfilePic"), json.getString("LnkdInLinK")));
                }

                AlumniAdapter alumniAdapter = new AlumniAdapter(getActivity(), alumnus);
                ListView listView = (ListView) getActivity().findViewById(R.id.AlumniList);
                listView.setAdapter(alumniAdapter);

            } else {
                SearchAlumniListSection.setVisibility(LinearLayout.INVISIBLE);
                Toast.makeText(getActivity(), "No Alumnus Found!", Toast.LENGTH_SHORT).show();
            }
        }

    }

}

