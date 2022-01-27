package com.example.advertisment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends Fragment {
    TextView register;
    Button loginbt;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;
    public static EditText user,pass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_login_activity, container, false);
        register = v.findViewById(R.id.register);
        loginbt = v.findViewById(R.id.loginbt);
        user=v.findViewById(R.id.user);
        pass=v.findViewById(R.id.pass);
        fAuth = FirebaseAuth.getInstance();
        progressBar2=v.findViewById(R.id.progressBar2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationActivity ra = new RegistrationActivity();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,ra).commit();
            }
        });
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= user.getText().toString().trim();
                String password=pass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    user.setError("UserName is Requires");
                }
                if(TextUtils.isEmpty(password)){
                    pass.setError("Email is Required");
                }
                if(password.length() < 6){
                    pass.setError("Password must be >= 6 Characters");
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);

                // authenticate the user
              fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       Toast.makeText(getActivity(),"Logged In Successfully",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getActivity().getApplicationContext(),MainActivity3.class));
                   }
                   else
                   {
                       Toast.makeText(getActivity(),"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       progressBar2.setVisibility(View.GONE);
                   }
                });
            }
        });


        return v;
    }
}