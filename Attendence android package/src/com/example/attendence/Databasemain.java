package com.example.attendence;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class Databasemain extends SQLiteOpenHelper{
    Context context;
    
   
	
	public Databasemain(Context context) {
		super(context, "DATABASEDB", null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			
			db.execSQL("CREATE TABLE ACCOUNT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,NAME TEXT,DESIGNATION TEXT,DEPARTMENT TEXT,PHONENUM TEXT);");
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	public void insert(String user,String pass,String name,String desig,String depart,String phoneno ){
		
		SQLiteDatabase db=getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		
		contentValues.put("USERNAME", user);
		contentValues.put("PASSWORD", pass);
		contentValues.put("NAME", name);
		contentValues.put("DESIGNATION", desig);
		contentValues.put("DEPARTMENT", depart);
		contentValues.put("PHONENUM", phoneno);
		
		db.insert("ACCOUNT", null, contentValues);
		
		
	}
	
	public Cursor display(){
		SQLiteDatabase db=getReadableDatabase();
		 
		Cursor cursor=db.rawQuery(("SELECT *FROM ACCOUNT"), null);
		
		 return cursor;
	}

}
