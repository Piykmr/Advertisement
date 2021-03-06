package com.example.advertisment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends Fragment {
    EditText nameR,emailR,mobileR,passR;
    Button enter;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("UsersDetails");
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_registration_activity, container, false);
        nameR=v.findViewById(R.id.nameR);
        emailR=v.findViewById(R.id.emailR);
        mobileR=v.findViewById(R.id.mobileR);
        passR=v.findViewById(R.id.passR);
        enter=v.findViewById(R.id.enter);
        progressBar=v.findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = nameR.getText().toString();
                String email = emailR.getText().toString().trim();
                String password = passR.getText().toString().trim();
               String phone = mobileR.getText().toString().trim();
              if(TextUtils.isEmpty(name)){
                  nameR.setError("Name is Requires");
              }
              if(TextUtils.isEmpty(email)){
                  emailR.setError("Email is Required");
              }
              if(TextUtils.isEmpty(password)){
                  passR.setError("Password is Requires");
              }
                if(TextUtils.isEmpty(phone)){
                    mobileR.setError("Mobile Number is Requires");
                }
              if(password.length() < 6){
                  passR.setError("Password must be >= 6 Characters");
                  return;
              }
                if(phone.length() !=10){
                    mobileR.setError("Mobile must be 10 digits");
                    return;
                }
              progressBar.setVisibility(View.VISIBLE);

              // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"User created",Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            uploadFirebase();

                                startActivity(new Intent(getActivity().getApplicationContext(),Profile.class));

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity3.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                });

            }
        });


        Toast.makeText(getActivity(), "Please Enter Details", Toast.LENGTH_LONG).show();

        return v;
    }


    private void uploadFirebase()
    {
        String name = nameR.getText().toString();
        String email = emailR.getText().toString().trim();
        String phone = mobileR.getText().toString();
        String password = passR.getText().toString().trim();
                @SuppressWarnings("VisibleForTests")
                Regst regst = new Regst(name, email, password,phone);
                String uploadId = root.push().getKey();
                root.child(uploadId).setValue(regst);
        }


}