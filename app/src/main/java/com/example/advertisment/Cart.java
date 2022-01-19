package com.example.advertisment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
   // TextView coname,coemail,coaddress,cophone;
    Button pay,update,delete;
    RecyclerView recyclerView;
    EditText editText;
    ArrayList<Model> dataholder = new ArrayList<>();
   DBHelper DB;
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
        editText=findViewById(R.id.editText);
    recyclerView=findViewById(R.id.recyclerView);
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
                        dataholder.add(obj);
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



    }
}