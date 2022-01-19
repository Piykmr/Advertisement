package com.example.advertisment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(companyname TEXT primary key, email TEXT, address TEXT,phone INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertuserdata(String companyname,String email,String address,int phone)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        ContentValues contextValues = new ContentValues();
        contextValues.put("companyname",companyname);
        contextValues.put("email",email);
        contextValues.put("address",address);
        contextValues.put("phone",phone);
        long result = DB.insert("Userdetails",null,contextValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Boolean updateuserdata(String companyname,String email,String address,int phone)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        ContentValues contextValues = new ContentValues();
        contextValues.put("email",email);
        contextValues.put("address",address);
        contextValues.put("phone",phone);
        Cursor cursor = DB.rawQuery("Select * from userdetails where companyname = ?", new String[]{companyname});
        if(cursor.getCount()>0){

            long result = DB.update("Userdetails",contextValues,"companyname=?",new String[]{companyname});
            if(result==-1)
            {
                return false;
            }
            else
            {
                return true;
            }

        }
        else
        {
            return  false;
        }

    }


    public Boolean deletedata(String companyname)
    {
        SQLiteDatabase DB =this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from userdetails where companyname = ?", new String[]{companyname});
        if(cursor.getCount()>0){

            long result = DB.delete("Userdetails","companyname=?",new String[]{companyname});
            if(result==-1)
            {
                return false;
            }
            else
            {
                return true;
            }

        }
        else
        {
            return  false;
        }

    }


    public Cursor getdata()
    {
        SQLiteDatabase DB =this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from userdetails", null);
        return cursor;

    }
}
