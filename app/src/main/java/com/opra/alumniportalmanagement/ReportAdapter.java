package com.opra.alumniportalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ReportAdapter extends ArrayAdapter<Report> {
    public ReportAdapter(@NonNull Context context, ArrayList<Report> report) {
        super(context,0, report);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.report_list_item,parent,false
            );
        }

        Report currentReport = getItem(position);

        TextView reportID = (TextView) listItemView.findViewById(R.id.reportId);
        reportID.setText(currentReport.value);

        TextView count = (TextView) listItemView.findViewById(R.id.count);
        count.setText(String.valueOf(currentReport.freq));

        //Set same width for each column.
        int width = parent.getWidth();

        reportID.setWidth(width/2);
        count.setWidth(width/2);

        return listItemView;
    }
}
