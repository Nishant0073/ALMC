package com.opra.alumniportalmanagement;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {



    private TextInputEditText ELoginEMail;
    private TextInputEditText ELoginPassword;
    private Button BLoginSubmit;
    private View view;
    private String LoginEmail;
    private String LoginPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_login, container, false);

        ELoginEMail = view.findViewById(R.id.LoginEmail);
        ELoginPassword = view.findViewById(R.id.LoginPassword);
        BLoginSubmit = view.findViewById(R.id.LLoginSubmit);



        BLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginEmail = ELoginEMail.getText().toString().trim();
                LoginPassword = ELoginPassword.getText().toString().trim();
                System.out.println("Email:"+LoginEmail+" "+LoginPassword);

                if(!EmailValidate())
                {
                    Toast.makeText(getActivity(), "Enter valid Email!", Toast.LENGTH_SHORT).show();
                }
                else if(LoginPassword.isEmpty())
                {
                    Toast.makeText(getActivity(), "Enter Password!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BLoginSubmit.setEnabled(false);
                    DataBaseConnection dataBaseConnection = new DataBaseConnection();
                    int response = 0;
                    try {
                        response = dataBaseConnection.ValidateUser(LoginEmail,LoginPassword);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Response:"+response);
                    if(response==1)
                    {
//                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE).edit();
//                        editor.putString("emailId", LoginEmail);
//                        editor.putBoolean("isLogin",true);
//                        editor.apply();
//                        getActivity().recreate();
                        Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    }
                    else if(response==0)
                    {
                        Toast.makeText(getActivity(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Incorrect Email!", Toast.LENGTH_SHORT).show();
                    }
                    BLoginSubmit.setEnabled(true);
                }
            }
        });

        return view;
    }



    private boolean EmailValidate() {

        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher  regMatcher   = regexPattern.matcher(LoginEmail);
        if(regMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
