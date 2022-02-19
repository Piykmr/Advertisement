package com.example.advertisment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    TextView profileName,profileEmail,profilePhone;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore fStore;
    FirebaseUser user;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String userID;
    Button changeProfile,resetPassword;
    ImageView home,mail,profile,cart,logout;
    ImageView profileImage;
    String userEmail;
    public static ArrayList<String> name = new ArrayList<>();
    public static ArrayList<String> email = new ArrayList<>();
    public static ArrayList<String> phone = new ArrayList<>();
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fAuth.getCurrentUser()==null)
        {
            Intent intent = new Intent(Profile.this, IfEmpty.class);
            startActivity(intent);
            finish();
            Toast.makeText(Profile.this, "Not Logged In", Toast.LENGTH_SHORT).show();
            return;
        }
            setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
            profileEmail = findViewById(R.id.profileEmail);
            profilePhone = findViewById(R.id.profilePhone);
            changeProfile = findViewById(R.id.changeProfile);
            resetPassword = findViewById(R.id.resetPassword);
            profileImage = findViewById(R.id.profileImage);
            logout = findViewById(R.id.logout);
            fStore = FirebaseFirestore.getInstance();
            userID = fAuth.getCurrentUser().getUid();
            user = FirebaseAuth.getInstance().getCurrentUser();
            home = findViewById(R.id.home);
            mail = findViewById(R.id.support);
            cart = findViewById(R.id.cart);
            profile = findViewById(R.id.profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null ) {
            userEmail = user.getEmail();
        } else {
            Toast.makeText(Profile.this,"No user is Signed in",Toast.LENGTH_LONG).show();
            Intent goToHome = new Intent(Profile.this, MainActivity.class);
            startActivity(goToHome);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UsersDetails");
        storageReference = FirebaseStorage.getInstance().getReference("UsersDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    name.add(dataSnapshot.child("name").getValue().toString());
                    email.add(dataSnapshot.child("email").getValue().toString());
                    phone.add(dataSnapshot.child("phone").getValue().toString());
                }
                for(int i=0;i<email.size();i++) {
                   if (userEmail.equals(email.get(i))) {
                        profileName.setText(name.get(i));
                        profileEmail.setText(email.get(i));
                        profilePhone.setText(phone.get(i));
                       MainActivity3.finalIndexOfProfile=i;
                        return;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

            changeProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallary, 1000);
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



        }


    }
