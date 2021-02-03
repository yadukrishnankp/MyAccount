package com.example.myaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myaccount.Model.Amount_model;

import java.util.ArrayList;

public class Dbhandle extends SQLiteOpenHelper {

    private static final int Databaseversion = 1;
    private static String  Databasename="Zpace Shopee";
    public Dbhandle(@Nullable Context context) {
        super(context,Databasename,null, Databaseversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String a="create table earning(erid integer primary key,earnid text) ";
        db.execSQL(a);
        String b="create table expense(exid integer primary key,expenseid text) ";
        db.execSQL(b);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists earning");
        onCreate(db);
        db.execSQL("drop table if exists expense");
        onCreate(db);

    }
    public void expense_insert(Amount_model amountModel)
    {
        SQLiteDatabase  db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("expenseid",amountModel.getExpense());
        db.insert("expense",null,values);
        db.close();
        Log.e("inserted","=");
    }
    public void earn_insert(Amount_model amountModel)
    {
        SQLiteDatabase  db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("earnid",amountModel.getEarning());
        db.insert("earning",null,values);
        db.close();
    }
    public ArrayList<Amount_model>getexpense()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Amount_model>list=new ArrayList<>();
        Amount_model amount_model=null;
        Cursor cursor=db.rawQuery("select * from earning",null);
        if (cursor!=null&&cursor.moveToFirst())
        {
            do {
                amount_model=new Amount_model();
                amount_model.setExid(cursor.getString(0));
                amount_model.setExpense(cursor.getString(1));
                list.add(amount_model);
            }
            while (cursor.moveToNext());
            {
                cursor.close();
                db.close();
            }
        }
        return list;
    }
    public ArrayList<Amount_model>getearning()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Amount_model>list=new ArrayList<>();
        Amount_model amount_model=null;
        Cursor cursor=db.rawQuery("select * from expense",null);
        if (cursor!=null&&cursor.moveToFirst())
        {
            do {
                amount_model=new Amount_model();
                amount_model.setErid(cursor.getString(0));
                amount_model.setEarning(cursor.getString(1));
                list.add(amount_model);
            }
            while (cursor.moveToNext());
            {
                cursor.close();
                db.close();
            }
        }
        return list;
    }

    public void delete_earning()
    {
        Log.e("hi","=");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from  earning");
    }
    public void delete_expense()
    {
        Log.e("hi","=");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from  expense");
    }


}
