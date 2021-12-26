package com.opra.alumniportalmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report, container, false);
        return view;
    }

    public static class YearWiseReportObject
    {
        public String year;
        public int count;

        YearWiseReportObject(String year,int count)
        {
            this.year = year;
            this.count = count;
        }
    }

    public static class CompanyWiseReportObject
    {
        public String company;
        public int count;

        CompanyWiseReportObject(String company,int count)
        {
            this.company = company;
            this.count = count;
        }

        public CompanyWiseReportObject(String company) {
            this.company = company;
        }
    }

}