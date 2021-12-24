package com.opra.alumniportalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlumniAdapter extends ArrayAdapter<Alumni> {

    public AlumniAdapter(@NonNull Context context, ArrayList<Alumni> alumni){
        super(context,0, alumni );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.alumni_list_item,parent,false
            );
        }

        Alumni currentAlumni = getItem(position);

        TextView AlumniRegID = (TextView) listItemView.findViewById(R.id.LAlumniRegID);
        AlumniRegID.setText(currentAlumni.alumniRegId);

        TextView AlumniName = (TextView) listItemView.findViewById(R.id.LAlumniName);
        AlumniName.setText(String.valueOf(currentAlumni.name));

        TextView AlumniYear= (TextView) listItemView.findViewById(R.id.LAlumniYear);
        AlumniYear.setText(currentAlumni.year);

        TextView AlumniCompany = (TextView) listItemView.findViewById(R.id.LAlumniCompany);
        AlumniCompany.setText(currentAlumni.company);

        //View parentLayout = (View)AlumniCompany.getParent();
        int width = parent.getWidth();

        AlumniRegID.setWidth(width/4);
        AlumniName.setWidth(width/4);
        AlumniYear.setWidth(width/4);
        AlumniCompany.setWidth(width/4);

//        return super.getView(position, convertView, parent);
        return listItemView;
    }
}
