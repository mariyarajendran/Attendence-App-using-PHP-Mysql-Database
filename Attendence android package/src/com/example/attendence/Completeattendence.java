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
import android.widget.Button;
import android.widget.Toast;

public class Completeattendence extends Activity {
String id,subject,department,section,year,from,to,absent;

ConnectionDetector cd;
Boolean isInternetPresent=false;

JSONParser jsonParser=new JSONParser();
ProgressDialog progressDialog;

String Tag;

private static String url="http://10.0.2.2/attend/delete.php";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_completeattendence);
		
		cd=new ConnectionDetector(getApplicationContext());
		Intent intent=getIntent();
		id=intent.getStringExtra("id");
		subject=intent.getStringExtra("subject");
		department=intent.getStringExtra("department");
		section=intent.getStringExtra("section");
		year=intent.getStringExtra("year");
		from=intent.getStringExtra("from");
		to=intent.getStringExtra("to");
		absent=intent.getStringExtra("absent");
		
		
		
	}

	
	
	
class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Completeattendence.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("subject", subject));
				param.add(new BasicNameValuePair("department", department));
				param.add(new BasicNameValuePair("section", section));
				param.add(new BasicNameValuePair("year", year));
				param.add(new BasicNameValuePair("absent",absent ));
				
			
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
