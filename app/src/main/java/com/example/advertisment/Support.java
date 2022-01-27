package com.example.advertisment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class Support extends AppCompatActivity {
    Button mailsending;
    TextView textfeedback;
    EditText enterSubject;
    MultiAutoCompleteTextView feedbackmsg;
    ImageView home,mail,profile,cart;
    ImageView callMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#095566"));

        actionBar.setBackgroundDrawable(colorDrawable);
        textfeedback=findViewById(R.id.textfeedback);
        mailsending = findViewById(R.id.mailsending);
        enterSubject=findViewById(R.id.enterSubject);
        feedbackmsg=findViewById(R.id.feedbackmsg);
        callMe=findViewById(R.id.callMe);
        home=findViewById(R.id.home);
        mail=findViewById(R.id.mail);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(Support.this,MainActivity.class);
                startActivity(goToHome);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailbox=new Intent(Support.this,Support.class);
                startActivity(mailbox);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCart = new Intent(Support.this,Cart.class);
                startActivity(goToCart);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(Support.this,Profile.class);
                startActivity(goToProfile);
            }
        });

        callMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9304521641"));

                if (ActivityCompat.checkSelfPermission(Support.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Support.this,"Please grant permission to make a phone call",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });




        mailsending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"rajpiyush72@gmail.com"};
        String[] CC = {"rajpiyush72@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, enterSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackmsg.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "hell");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Support.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }



    }

}