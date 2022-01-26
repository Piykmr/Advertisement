package com.example.advertisment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    TextView profileName,profileEmail,profilePhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;
    Button changeProfile,resetPassword;
    ImageView home,mail,profile,cart,logout;
    ImageView profileImage;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
        profileName = findViewById(R.id.profileName);
            profileEmail = findViewById(R.id.profileEmail);
            profilePhone = findViewById(R.id.profilePhone);
            changeProfile = findViewById(R.id.changeProfile);
            resetPassword = findViewById(R.id.resetPassword);
            profileImage = findViewById(R.id.profileImage);
            logout = findViewById(R.id.logout);
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            userID = fAuth.getCurrentUser().getUid();
            user = FirebaseAuth.getInstance().getCurrentUser();
            home = findViewById(R.id.home);
            mail = findViewById(R.id.mail);
            cart = findViewById(R.id.cart);
            profile = findViewById(R.id.profile);

            changeProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallary, 1000);
                }
            });


     /* DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                profileName.setText("Name" + " " + documentSnapshot.getString("name_"));
                profileEmail.setText("Email" + " " + documentSnapshot.getString("email_"));
                profilePhone.setText("Phone" + " " +  documentSnapshot.getString("phone_"));
            }
        });*/

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child(USERS);
            Log.v("USERID", userRef.getKey());

            userRef.addValueEventListener(new ValueEventListener() {
                String name, email, phone;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot keyId : snapshot.getChildren()) {
                        if (keyId.child("email").getValue().equals(email)) {
                            name = keyId.child("name").getValue(String.class);
                            email = keyId.child("email").getValue(String.class);
                            phone = keyId.child("phone").getValue(String.class);
                            break;
                        }
                    }
                    profileName.setText(name);
                    profileEmail.setText(email);
                    profilePhone.setText(phone);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(Profile.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void logout (View view){
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1000) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = data.getData();
                    profileImage.setImageURI(imageUri);
                }
            }


            home.setOnClickListener(v -> {
                Intent goToHome = new Intent(Profile.this, MainActivity.class);
                startActivity(goToHome);
            });

            mail.setOnClickListener(v -> {
                Intent mailbox = new Intent(Profile.this, Support.class);
                startActivity(mailbox);
            });


            cart.setOnClickListener(v -> {
                Intent goToCart = new Intent(Profile.this, Cart.class);
                startActivity(goToCart);
            });


            profile.setOnClickListener(v -> {
                Intent goToProfile = new Intent(Profile.this, Profile.class);
                startActivity(goToProfile);
            });
        }


    }
