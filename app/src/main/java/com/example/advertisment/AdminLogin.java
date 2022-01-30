package com.example.advertisment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AdminLogin extends Fragment {
    EditText user,pass;
    Button loginbt;
    ProgressBar progressBar2;
    public static final String userName = "__piyushkumar";
    public static final String password = "piyush123";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_admin_login, container, false);
        loginbt = v.findViewById(R.id.loginbt);
        user=v.findViewById(R.id.user);
        pass=v.findViewById(R.id.pass);
        progressBar2=v.findViewById(R.id.progressBar2);
        loginbt.setOnClickListener(v1 -> {
            String uSer=user.getText().toString();
            String pASS=pass.getText().toString();
            if(uSer.equals(userName) && pASS.equals(password))
            {
                Admin.flag=1;
                progressBar2.setVisibility(View.VISIBLE);
                startActivity(new Intent(getActivity().getApplicationContext(),Admin.class));
                getActivity().finish();
            }
            else
            {
                Toast.makeText(getActivity(), "You Are Not Admin", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
            }
        });


        return v;
    }
}