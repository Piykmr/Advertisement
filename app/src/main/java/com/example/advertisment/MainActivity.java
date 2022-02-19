package com.example.advertisment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageView home,mail,cart,profile;
    String bag_Name;
    DataSnapshot bag_Image;
    String bag_Price;
    TextView ClickOrder;
    ListView listView;
    BagAdapter bagAdapter;
    String[] bagName = new String[50];
    String[] tempbagName={"Design 1.0","Design 2.0","Design 3.0","Design 4.0","Design 5.0","Design 6.0","Design 7.0","Design 8.0","Design 9.0","Design 10.0","Design 11.0","Design 12.0","Design 13.0","Design 14.0","Design 15.0","Design 16.0","Design 17.0"};
    public static String[] price = new String[50];
    String[] tempPrice={"Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.100"};
    int[] image=new int[50];
    int[] tempImage={R.drawable.bag16,R.drawable.bag17,R.drawable.bag18,R.drawable.bag19,R.drawable.bag20,R.drawable.bag1,R.drawable.bag2,R.drawable.bag3,R.drawable.bag4,R.drawable.bag5,R.drawable.bag6,R.drawable.bag7,R.drawable.bag8,R.drawable.bag9,R.drawable.bag10,R.drawable.bag14,R.drawable.bag15};
    int index=tempbagName.length-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);
        home=findViewById(R.id.home);
        mail=findViewById(R.id.support);
        for(int i=0;i<index;i++) {
            bagName[i]=tempbagName[i];
            image[i]=tempImage[i];
            price[i]=tempPrice[i];

        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("BagDetails");
        storageReference = FirebaseStorage.getInstance().getReference("BagDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    bagName[index] = dataSnapshot.child("designNameOfBag").getValue().toString();
                    image[index]=R.drawable.paperbag;
                    price[index] = dataSnapshot.child("priceOfBag").getValue().toString();
                    index++;
                }
                String[] b = new String[index];
                String[] p = new String[index];
                int[] im = new int[index];
                for(int i=0;i<index;i++) {
                    b[i]=bagName[i];
                    im[i]=image[i];
                    p[i]=price[i];

                }

                bagAdapter = new BagAdapter(MainActivity.this,b,im,p);
                ListView.getDefaultSize(index,index);
                listView.setAdapter(bagAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        ClickOrder=findViewById(R.id.ClickOrder);
        ClickOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Admin.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#095566"));

        actionBar.setBackgroundDrawable(colorDrawable);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(MainActivity.this,MainActivity.class);
                startActivity(goToHome);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailbox=new Intent(MainActivity.this,Support.class);
                startActivity(mailbox);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCart = new Intent(MainActivity.this,Cart.class);
                startActivity(goToCart);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(MainActivity.this,Profile.class);
                startActivity(goToProfile);
            }
        });
    }
}