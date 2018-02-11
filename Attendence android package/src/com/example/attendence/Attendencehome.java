package com.example.attendence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Attendencehome extends Activity {
Button addnewclass,listofclass,search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendencehome);
		
		addnewclass=(Button)findViewById(R.id.addnewclass);
		listofclass=(Button)findViewById(R.id.listofclass);
		
		
		addnewclass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				Intent intent=new Intent(Attendencehome.this,Addclass.class);
				startActivity(intent);
			}
		});
		
		
		listofclass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent=new Intent(Attendencehome.this,Classlist.class);
				startActivity(intent);
				
			}
		});
		
	
		
		
	}

	

}
