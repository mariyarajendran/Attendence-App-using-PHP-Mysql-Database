package com.example.attendence;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
EditText use,pas;
Button log,signuplog;

ConnectionDetector cd;
Boolean isInternetPresent=false;

JSONParser jsonParser=new JSONParser();
ProgressDialog progressDialog;

String edituse,editpas,Tag,staffname;

private static String url="http://10.0.2.2/attend/index.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		
		cd=new ConnectionDetector(getApplicationContext());
		signuplog=(Button)findViewById(R.id.signuplog);
		
		use=(EditText)findViewById(R.id.use);
		pas=(EditText)findViewById(R.id.pas);
		log=(Button)findViewById(R.id.log);
		
		
		
		
		
		log.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				edituse=use.getText().toString();
				editpas=pas.getText().toString();
				
				
				if(edituse.equalsIgnoreCase("")||editpas.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
				}
				
				else{
					
					
					isInternetPresent=false;
					isInternetPresent=cd.isConnectingToInternet();
					
					if(isInternetPresent){
						
						Tag="log";
						new check().execute();
						
						use.setText("");
						pas.setText("");
						//Toast.makeText(getApplicationContext(), "valid", Toast.LENGTH_LONG).show();
						Intent intent=new Intent(Login.this,Attendencehome.class);
						startActivity(intent);
						
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
		
		
		signuplog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Login.this,Attendence.class);
				startActivity(intent);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	
class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Login.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("username", edituse));
				param.add(new BasicNameValuePair("password", editpas));
				
				
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

}
