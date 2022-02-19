package com.example.advertisment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    TextView t1,t2,t3;
    ImageView increment,decrement,home,mail,profile,login,cart;
    ImageView imageView;
    private int index=1;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#095566"));
        actionBar.setBackgroundDrawable(colorDrawable);
        home=findViewById(R.id.home);
        mail=findViewById(R.id.support);
        login=findViewById(R.id.cart);
        increment=findViewById(R.id.increment);
        decrement=findViewById(R.id.decrement);
        imageView=findViewById(R.id.imageView);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);
        proceed=findViewById(R.id.proceed);
        String price1=getIntent().getStringExtra("bagPrice");

        t2.setText(getIntent().getStringExtra("bagPrice"));
        imageView.setImageResource(BagAdapter.img);;
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                t1.setText(index + "");
                t2.setText(getIntent().getStringExtra("bagPrice")+ "*" + index);
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index>1) {
                    index--;
                    t1.setText(" " + index + "");
                    t2.setText(getIntent().getStringExtra("bagPrice")+ "*" + index);
                }
                else
                {
                    Toast.makeText(MainActivity2.this,"Cart value is minimum",Toast.LENGTH_LONG).show();
                }

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buy=new Intent(MainActivity2.this,MainActivity3.class);
                buy.putExtra("bagimg",getIntent().getIntExtra("bagImage",0));
                startActivity(buy);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(goToHome);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailbox=new Intent(MainActivity2.this,Support.class);
                startActivity(mailbox);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCart = new Intent(MainActivity2.this,Cart.class);
                startActivity(goToCart);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(MainActivity2.this,Profile.class);
                startActivity(goToProfile);
            }
        });
    }
}