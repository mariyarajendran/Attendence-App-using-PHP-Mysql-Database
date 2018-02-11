package com.example.attendence;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

public class Attendence extends Activity implements android.widget.CompoundButton.OnCheckedChangeListener{
	
	
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	
	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;
	
	private static String url="http://10.0.2.2/attend/index.php";
	
	
	
	
	Databasemain databasemain;
	Cursor cursor;

String[] name={"Mr","Mrs","Ms"};
AutoCompleteTextView autoname;
Spinner desig,ug;

EditText userup,passup,phone;
Button create;



String uname,Tag,usersig, passi,user,pass,names,design,departments,contactno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendence);
		
		cd=new ConnectionDetector(getApplicationContext());
		
		databasemain=new Databasemain(getApplicationContext());
				
		autoname=(AutoCompleteTextView)findViewById(R.id.autoname);
		
		desig=(Spinner)findViewById(R.id.desig);
		ug=(Spinner)findViewById(R.id.ug);
		
		userup=(EditText)findViewById(R.id.userup);
		passup=(EditText)findViewById(R.id.passup);
		
		phone=(EditText)findViewById(R.id.phone);
		create=(Button)findViewById(R.id.create);
		
				
		
		
		ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,name);
		autoname.setAdapter(adapter);
		
		
		List<String> list=new ArrayList<String>();
		list.add("Head and Associate Professor");
		list.add("Associate Professor");
		list.add("Assistant Professor");
		
		
		ArrayAdapter<String> designation=new ArrayAdapter<String>(this,R.layout.spintext,list);
		designation.setDropDownViewResource(R.layout.spintext);
		desig.setAdapter(designation);
	
		List<String> list1=new ArrayList<String>();
		list1.add("UG");
		list1.add("PG");
		
		
		
		ArrayAdapter<String> department=new ArrayAdapter<String>(this,R.layout.spintext,list1);
		department.setDropDownViewResource(R.layout.spintext);
		ug.setAdapter(department);
		
		
		
		
		
		
		
		
		
		

		
		
		
		
		

		
		
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 user=userup.getText().toString();
				 pass=passup.getText().toString();
				//String conf=confirm.getText().toString();
				 names=autoname.getText().toString();
		         design=desig.getSelectedItem().toString();
		         departments=ug.getSelectedItem().toString();
		      contactno=phone.getText().toString();
				
				
				
				
				if(user.equalsIgnoreCase("")||pass.equalsIgnoreCase("")||names.equalsIgnoreCase("")||contactno.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill all fields",Toast.LENGTH_SHORT).show();
					
				}
				else{
					
					
					isInternetPresent=false;
					isInternetPresent=cd.isConnectingToInternet();
					
					if(isInternetPresent){
						
						Tag="create";
						new check().execute();
						
					//	Toast.makeText(getApplicationContext(), "Data insert", Toast.LENGTH_LONG).show();
						userup.setText("");
						passup.setText("");
						autoname.setText("");
						phone.setText("");
						
					}
					
					else if(!isInternetPresent){
						
						Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
					}
					
					else{
						
						Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG).show();
						
					}
				}
				
					
				
				
				
				
			}
		});
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.attendence, menu);
		return true;
	}
	
	
class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Attendence.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("username", user));
				param.add(new BasicNameValuePair("password", pass));
				param.add(new BasicNameValuePair("name", names));
				param.add(new BasicNameValuePair("rank", design));
				param.add(new BasicNameValuePair("department", departments));
				param.add(new BasicNameValuePair("phone_number", contactno));
				
				
				
				JSONObject json=jsonParser.makeHttpRequest(url, "POST", param);
				
				
				try 
				{
					final String Result=json.getString("result" );
					
					//with in doInBackground we must use runOnUiThread for display toast
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), Result , Toast.LENGTH_LONG).show();
						}
					});
					
				}
				catch (JSONException e) 
				{
						e.printStackTrace();
				}
				
				
				
			
		return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
		}
	
	
	}


@Override
public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
	// TODO Auto-generated method stub
	
}
	

}
