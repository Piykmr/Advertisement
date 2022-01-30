package com.example.advertisment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String bag_Name;
    DataSnapshot bag_Image;
    String bag_Price;
    TextView ClickOrder;
    ListView listView;
    BagAdapter bagAdapter;
    String[] bagName = {"Design 1.0","Design 2.0","Design 3.0","Design 4.0","Design 5.0","Design 6.0","Design 7.0","Design 8.0","Design 9.0","Design 10.0","Design 11.0","Design 12.0","Design 13.0","Design 14.0","Design 15.0","Design 16.0","Design 17.0"};
    public static String[] price = {"Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.30","Rs.100","Rs.50","Rs.100"};
    int[] image = {R.drawable.bag16,R.drawable.bag17,R.drawable.bag18,R.drawable.bag19,R.drawable.bag20,R.drawable.bag1,R.drawable.bag2,R.drawable.bag3,R.drawable.bag4,R.drawable.bag5,R.drawable.bag6,R.drawable.bag7,R.drawable.bag8,R.drawable.bag9,R.drawable.bag10,R.drawable.bag14,R.drawable.bag15};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        bagAdapter = new BagAdapter(this,bagName,image,price);
        listView.setAdapter(bagAdapter);
        ClickOrder=findViewById(R.id.ClickOrder);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BagDetails/-MugFLdtjaUSpcRfwcb-");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    bag_Name= snapshot.child("designNameOfBag").getValue().toString();
                    bag_Image= snapshot.child("imageURL");
                    bag_Price= snapshot.child("priceOfBag").getValue().toString();
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    }
}