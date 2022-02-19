package com.example.advertisment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    Button pay,update,delete;
    int flag=0;
    RecyclerView recyclerView;
    EditText editText;
    ArrayList<Model> dataholder = new ArrayList<>();
   DBHelper DB,DB_;
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    ImageView home,support,profile,cart;
    TextView cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Checking if User is Logged in or not and Cart is empty or not..
        if(fAuth.getCurrentUser()==null)
        {
            Intent intent = new Intent(Cart.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(Cart.this, "Not Logged In", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            DB = new DBHelper(this);
            Cursor res = DB.getdata();
            if (res.getCount() == 0) {
                Intent intent = new Intent(Cart.this, IfEmptyActvity.class);
                startActivity(intent);
                finish();
                Toast.makeText(Cart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }


            setContentView(R.layout.activity_cart);
            delete = findViewById(R.id.delete);
            cod = findViewById(R.id.cod);
            pay = findViewById(R.id.pay);
            editText = findViewById(R.id.editText);
            recyclerView = findViewById(R.id.recyclerView);
            home = findViewById(R.id.home);
            support = findViewById(R.id.support);
            cart = findViewById(R.id.cart);
            profile = findViewById(R.id.profile);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            DB = new DBHelper(this);
            DB_ = new DBHelper(this);
            Cursor cursor = DB.getdata();
            Cursor cursor_ = DB.getdata();
            while (cursor.moveToNext()) {
                Model obj = new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getInt(5));
                dataholder.add(obj);
            }
            MyAdapter adapter = new MyAdapter(dataholder);
            recyclerView.setAdapter(adapter);
            cod.setText("Keep Cash On Delivery " + BagAdapter.bgprice + "*" + dataholder.size());
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
                    String str1 = editText.getText().toString();
                    Boolean checkdeletedata = DB.deletedata(str1);
                    if (checkdeletedata == true) {
                        while (cursor.moveToNext()) {
                            Model obj = new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor_.getInt(5));
                            dataholder.remove(obj);
                        }
                        MyAdapter adapter = new MyAdapter(dataholder);
                        recyclerView.setAdapter(adapter);
                        Toast.makeText(Cart.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Cart.this, "Entry not deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToHome = new Intent(Cart.this, MainActivity.class);
                    startActivity(goToHome);
                }
            });

            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mailbox = new Intent(Cart.this, Support.class);
                    startActivity(mailbox);
                }
            });


            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToCart = new Intent(Cart.this, Cart.class);
                    startActivity(goToCart);
                }
            });


            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToProfile = new Intent(Cart.this, Profile.class);
                    startActivity(goToProfile);
                }
            });


            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = "rajpiyush72@gmail.com";
                    String subject = "Customer details received";
                    String message = "hello You have an order of Paper Bag \n";
                    Cursor res = DB.getdata();

                    while (res.moveToNext()) {
                        message += ("Company Name : " + res.getString(0) + "\n");
                        message += ("Company Email : " + res.getString(1) + "\n");
                        message += ("Company Address : " + res.getString(2) + "\n");
                        message += ("Company Phone : " + res.getString(3) + "\n");
                        message += ("Design Name : " + res.getString(4) + "\n" );
                        message += ("\n \n \n");
                    }

                    //Creating SendMail object
                    SendMail sm = new SendMail(Cart.this, email, subject, message);
                    //Executing sendmail to send email
                    sm.execute();

                    //Sending Mails to all customers
                    Cursor res_ = DB_.getdata();
                    while (res_.moveToNext()) {
                        String name_ = res_.getString(0);
                        String address_ = res_.getString(1);
                        String email_ = res_.getString(2);
                        String phone_ = res_.getString(3);
                        Log.d("email is", address_);
                        String subject_ = "Your Order is Confirmed";
                        String message_ = "Hello" + " " + name_ + "your order is confirmed. " +
                                "\nIt will be delivered soon to your address" + " " + address_ +
                                "\nplease keep Cash ready" +
                                "\nOur delivery partner will call you on" + " " + phone_;
                        SendMail sendMail = new SendMail(Cart.this, email_, subject_, message_);
                        sendMail.execute();
                        Boolean checkdeletedata_ = DB.deletedata(name_);
                        if (checkdeletedata_ == true) {
                            while (cursor_.moveToNext()) {
                                Model obj = new Model(cursor_.getString(0), cursor_.getString(1), cursor_.getString(2), cursor_.getString(3),cursor_.getString(4),cursor_.getInt(5));
                                dataholder.remove(obj);
                            }
                        }
                        name_ = null;
                        email_ = null;
                        address_ = null;
                        phone_ = null;
                        subject_ = null;
                        message_ = null;
                        sendMail = null;
                    }
                    Intent intent = new Intent(Cart.this, MainActivity.class);
                    startActivity(intent);
                }

            });

        }



}
