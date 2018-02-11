package com.example.attendence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;


public class Absentlist extends Activity {

	EditText subject,department,year,section;
	
	String sub,dep,sec,yea;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_absentlist);
		subject=(EditText)findViewById(R.id.subject);
		department=(EditText)findViewById(R.id.department);
		year=(EditText)findViewById(R.id.year);
		section=(EditText)findViewById(R.id.section);
		
		sub=subject.getText().toString();
		dep=department.getText().toString();
		sec=section.getText().toString();
		yea=year.getText().toString();
		
		Intent intent=new Intent(Absentlist.this,Absentlistformate.class);
		intent.putExtra("subject", sub);
		intent.putExtra("department", dep);
		intent.putExtra("section", sec);
		intent.putExtra("year", yea);
		startActivity(intent);
		
		
		
		
		
	}

	
}
