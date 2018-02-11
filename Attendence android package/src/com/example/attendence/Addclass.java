package com.example.attendence;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.attendence.Attendence.check;

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

public class Addclass extends Activity {

	EditText subject,department,section,year,from,to;
	ConnectionDetector cd;
	
Boolean isInternetPresent=false;
	
	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;
	
	private static String url="http://10.0.2.2/attend/myclass.php";
	
	String Tag,sub,dep,sec,yea,fr,t;
	
	Button create;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addclass);
		
		
		subject=(EditText)findViewById(R.id.subject);
		department=(EditText)findViewById(R.id.department);
		section=(EditText)findViewById(R.id.section);
		year=(EditText)findViewById(R.id.year);
		from=(EditText)findViewById(R.id.from);
		to=(EditText)findViewById(R.id.to);
		
		create=(Button)findViewById(R.id.create);
		
		cd=new ConnectionDetector(getApplicationContext());
		
		
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				sub=subject.getText().toString();
				dep=department.getText().toString();
				sec=section.getText().toString();
				yea=year.getText().toString();
				fr=from.getText().toString();
				t=to.getText().toString();
				
				
if(sub.equalsIgnoreCase("")||dep.equalsIgnoreCase("")||sec.equalsIgnoreCase("")||yea.equalsIgnoreCase("")||fr.equalsIgnoreCase("")||t.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill all fields",Toast.LENGTH_SHORT).show();
					
				}
				else{
					
					
					isInternetPresent=false;
					isInternetPresent=cd.isConnectingToInternet();
					
					if(isInternetPresent){
						
						Tag="create";
						new check().execute();
						
					//	Toast.makeText(getApplicationContext(), "Data insert", Toast.LENGTH_LONG).show();
						subject.setText("");
						department.setText("");
						section.setText("");
						year.setText("");
						from.setText("");
						to.setText("");
						
						Intent intent=new Intent(getApplicationContext(),Classlist.class);
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
		
		
		
		
		
	}
	
	
	
	
class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Addclass.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("sub", sub));
				param.add(new BasicNameValuePair("dep", dep));
				param.add(new BasicNameValuePair("sec", sec));
				param.add(new BasicNameValuePair("yea", yea));
				param.add(new BasicNameValuePair("from", fr));
				param.add(new BasicNameValuePair("to", t));
				
				
				
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
