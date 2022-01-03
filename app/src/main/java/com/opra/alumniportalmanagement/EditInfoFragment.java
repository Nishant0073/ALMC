package com.opra.alumniportalmanagement;

import static com.opra.alumniportalmanagement.R.drawable.alumni_defult_profile_pic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class EditInfoFragment extends Fragment {

    //Required variables
    LinearLayout JUserInfoLinearLayout;
    ImageView JAlumniPic;
    private TextInputEditText JAlumniRegIDEdit_text;
    private Button JBGetProfile;
    private TextInputEditText JUAlumniNameEdit_text;
    private TextInputEditText JUAlumniEmailEdit_text;
    private TextInputEditText JUAlumniRegIDEdit_text;
    private TextInputEditText JUPasswordEdit_text;
    private TextInputEditText JUContactNoEdit_text;
    private TextInputEditText JUCompanyNameEdit_text;
    private TextInputEditText JUDesignationEdit_text;
    private TextInputEditText JUPackageEdit_text;
    private TextInputEditText JUCoPasswordEdit_text;
    private TextInputEditText JUPassoutYearEdit_text;
    private TextInputEditText JUDepartmentEdit_text;
    private TextInputEditText JUProfilePicLinkEdit_text;
    private TextInputEditText JULnkdInLinKEdit_text;
    private Button UpdateProfile;
    private String AlumniRegId;
    private Alumni alumni;


    public EditInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);


        JAlumniPic = (ImageView) view.findViewById(R.id.AlumniPic);
        JUAlumniNameEdit_text = (TextInputEditText) view.findViewById(R.id.UAlumniNameEdit_text);
        JUAlumniEmailEdit_text = (TextInputEditText) view.findViewById(R.id.UAlumniEmailEdit_text);
        JUAlumniRegIDEdit_text = (TextInputEditText) view.findViewById(R.id.AlumniRegIDEdit_text);
        JUPasswordEdit_text = (TextInputEditText) view.findViewById(R.id.UPasswordEdit_text);
        JUContactNoEdit_text = (TextInputEditText) view.findViewById(R.id.UContactNoEdit_text);
        JUCompanyNameEdit_text = (TextInputEditText) view.findViewById(R.id.UCompanyNameEdit_text);
        JUDesignationEdit_text = (TextInputEditText) view.findViewById(R.id.UDesignationEdit_text);
        JUPackageEdit_text = (TextInputEditText) view.findViewById(R.id.UPackageEdit_text);
        JUCoPasswordEdit_text = (TextInputEditText) view.findViewById(R.id.UCoPasswordEdit_text);
        JUPassoutYearEdit_text = (TextInputEditText) view.findViewById(R.id.UPassoutYearEdit_text);
        JUDepartmentEdit_text = (TextInputEditText) view.findViewById(R.id.UDepartmentEdit_text);
        JUProfilePicLinkEdit_text = (TextInputEditText) view.findViewById(R.id.UProfilePicLinkEdit_text);
        JULnkdInLinKEdit_text = (TextInputEditText) view.findViewById(R.id.ULnkdInLinKEdit_text);
        UpdateProfile = (Button) view.findViewById(R.id.UpdateProfile);


        //Hiding JUserInfoLinearLayout Layout
        JUserInfoLinearLayout = (LinearLayout) view.findViewById(R.id.UserInfoLinearLayout);
        JUserInfoLinearLayout.setVisibility(JUserInfoLinearLayout.GONE);

        JBGetProfile = (Button) view.findViewById(R.id.EBGetProfile);
        JAlumniRegIDEdit_text = view.findViewById(R.id.AlumniRegIDEdit_text);

        //On request from user profile.
        JBGetProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                AlumniRegId = JAlumniRegIDEdit_text.getText().toString().trim();
                JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                if (AlumniRegId.isEmpty()) {
                    Toast.makeText(getActivity(), "Alumni Registration ID is Required!", Toast.LENGTH_SHORT).show();
                } else {

                    //If Alumni Reg Id is provided then
                    DataBaseConnection dataBaseConnection = new DataBaseConnection();
                    SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
                    String email = loginPrefs.getString("emailId", "NA");

                    JSONObject response = null;
                    try {
                         //get alumni profile from server
                        response = dataBaseConnection.searchAlumniByAlumniId(email, AlumniRegId);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //if some problem occurred while connecting db.
                    if (response == null) {
                        JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                        Toast.makeText(getActivity(), "\"Unable to connect DB!\"", Toast.LENGTH_SHORT).show();

                    } else {
                        try {
                            if (response.getInt("success") == 1) {

                                JSONObject json = (JSONObject) response.getJSONObject("alumni");
                                System.out.println("FROM ALUMNI REG ID JSON:" + json);
                                alumni = new Alumni(json.getString("RegID"), json.getString("AlmniRegID"), json.getString("AlmniName"), json.getString("EmailID"), json.getString("Password"), json.getString("ContactNo"), json.getString("CompyNameAdd"), json.getString("Designation"), json.getString("Package"), json.getString("CoPassword"), json.getString("PassoutYear"), json.getString("Department"), json.getString("ProfilePic"), json.getString("LnkdInLinK"));
                                setProfile(alumni, view);

                            } else {
                                //if alumni does not exist.
                                JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                                Toast.makeText(getActivity(), "No Alumnus Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //After updating value of profile
        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences loginPrefs = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
                String email = loginPrefs.getString("emailId", "NA");
                alumni.name = JUAlumniNameEdit_text.getText().toString().trim();
                alumni.emailId = JUAlumniEmailEdit_text.getText().toString().trim();
                alumni.password = JUPasswordEdit_text.getText().toString().trim();
                alumni.contactNo = JUContactNoEdit_text.getText().toString().trim();
                alumni.company = JUCompanyNameEdit_text.getText().toString().trim();
                alumni.designation = JUDesignationEdit_text.getText().toString().trim();
                alumni.packageSalary = JUPackageEdit_text.getText().toString().trim();
                alumni.coPassword = JUCoPasswordEdit_text.getText().toString().trim();
                alumni.year = JUPassoutYearEdit_text.getText().toString().trim();
                alumni.department = JUDepartmentEdit_text.getText().toString().trim();
                alumni.profilePic = JUProfilePicLinkEdit_text.getText().toString().trim();
                alumni.linkedInLink = JULnkdInLinKEdit_text.getText().toString().trim();

                //if all values are not provided
                if (!isValidAlumni(alumni)) {
                    Toast.makeText(getActivity(), "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataBaseConnection dataBaseConnection = new DataBaseConnection();
                JSONObject response = null;
                try {
                    response = dataBaseConnection.UpdateAlumniRecord(email, alumni);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println("Response:" + response);
                if (response == null) {
                    JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                    Toast.makeText(getActivity(), "\"Unable to connect DB!\"", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (response.getInt("success") == 1) {
                            JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                            Toast.makeText(getActivity(), "Profile of alumni with alumni registration Id " + alumni.alumniRegId + " updated!", Toast.LENGTH_SHORT).show();
                            System.out.println("FROM ALUMNI UPDATE ID JSON:" + response);

                        } else {
                            JUserInfoLinearLayout.setVisibility(LinearLayout.GONE);
                            Toast.makeText(getActivity(), "No Alumnus Found!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


        return view;
    }

    private boolean isValidAlumni(Alumni alumni) {
        if (alumni.regId.isEmpty() || alumni.alumniRegId.isEmpty() || alumni.name.isEmpty() || alumni.emailId.isEmpty() || alumni.password.isEmpty() || alumni.contactNo.isEmpty() || alumni.company.isEmpty() || alumni.designation.isEmpty() || alumni.packageSalary.isEmpty() || alumni.coPassword.isEmpty() || alumni.year.isEmpty() || alumni.department.isEmpty() || alumni.profilePic.isEmpty() || alumni.linkedInLink.isEmpty())
            return false;
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setProfile(Alumni alumni, View view) {
        JUserInfoLinearLayout.setVisibility(LinearLayout.VISIBLE);

        if ("blank.jpg".equals(alumni.profilePic)) {
            JAlumniPic.setImageResource(R.drawable.alumni_defult_profile_pic);
        } else {
            JAlumniPic.setImageURI(Uri.parse(alumni.profilePic));
        }
        JUAlumniNameEdit_text.setText(alumni.name);
        JUAlumniEmailEdit_text.setText(alumni.emailId);
        JUPasswordEdit_text.setText(alumni.password);
        JUContactNoEdit_text.setText(alumni.contactNo);
        JUCompanyNameEdit_text.setText(alumni.company);
        JUDesignationEdit_text.setText(alumni.designation);
        JUPackageEdit_text.setText(alumni.packageSalary);
        JUCoPasswordEdit_text.setText(alumni.coPassword);
        JUPassoutYearEdit_text.setText(alumni.year);
        JUDepartmentEdit_text.setText(alumni.department);
        JUProfilePicLinkEdit_text.setText(alumni.profilePic);
        JULnkdInLinKEdit_text.setText(alumni.linkedInLink);

    }
}