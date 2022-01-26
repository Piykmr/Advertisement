package com.example.advertisment;

import static javax.mail.Message.RecipientType.TO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Cart extends AppCompatActivity {
   // TextView coname,coemail,coaddress,cophone;
    Button pay,update,delete;
    RecyclerView recyclerView;
    EditText editText;
    ArrayList<Model> dataholder = new ArrayList<>();
   DBHelper DB;
    ImageView home,mail,profile,login,cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
       /* coname=findViewById(R.id.coname);
        coemail=findViewById(R.id.coemail);
        coaddress=findViewById(R.id.coaddress);
        cophone=findViewById(R.id.cophone);*/
        /*pay=findViewById(R.id.pay);
        update=findViewById(R.id.update);*/
        delete=findViewById(R.id.delete);
        pay = findViewById(R.id.pay);
        editText=findViewById(R.id.editText);
        recyclerView=findViewById(R.id.recyclerView);
        home=findViewById(R.id.home);
        mail=findViewById(R.id.mail);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB=new DBHelper(this);
        Cursor cursor = DB.getdata();
       while(cursor.moveToNext())
        {
            Model obj = new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            dataholder.add(obj);
        }
       MyAdapter adapter = new MyAdapter(dataholder);
       recyclerView.setAdapter(adapter);

       /* DB=new DBHelper(this);
        Cursor res = DB.getdata();
        if(res.getCount()==0){
            Toast.makeText(Cart.this,"Cart is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Company Name : "+res.getString(0)+"\n");
            buffer.append("Company Email : "+res.getString(1)+"\n");
            buffer.append("Company Address : "+res.getString(2)+"\n");
            buffer.append("Company Phone : "+res.getString(3)+"\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
        builder.setCancelable(true);
        builder.setTitle("Welcom Cart");
        builder.setMessage(buffer.toString());
        builder.show();*/
        

       delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1=editText.getText().toString();
                Boolean checkdeletedata = DB.deletedata(str1);
                if(checkdeletedata==true)
                {
                    while(cursor.moveToNext())
                    {
                        Model obj = new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                        dataholder.remove(obj);
                    }
                    MyAdapter adapter = new MyAdapter(dataholder);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(Cart.this,"Entry deleted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Cart.this,"Entry not deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "rajpiyush72@gmail.com";
                String subject = "Customer details received";
                String message = "hello You have an order of "+ BagAdapter.bgname +"\n";
                Cursor res = DB.getdata();


                while(res.moveToNext()){
                    message+=("Company Name : "+res.getString(0)+"\n");
                    message+=("Company Email : "+res.getString(1)+"\n");
                    message+=("Company Address : "+res.getString(2)+"\n");
                    message+=("Company Phone : "+res.getString(3)+"\n");
                }


                //Creating SendMail object
                SendMail sm = new SendMail(Cart.this, email, subject, message);

                //Executing sendmail to send email
                sm.execute();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(Cart.this,MainActivity.class);
                startActivity(goToHome);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailbox=new Intent(Cart.this,Support.class);
                startActivity(mailbox);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCart = new Intent(Cart.this,Cart.class);
                startActivity(goToCart);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(Cart.this,Profile.class);
                startActivity(goToProfile);
            }
        });
    }
    }