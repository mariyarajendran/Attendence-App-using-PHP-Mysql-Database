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
import android.widget.Toast;

public class Delete extends Activity {
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	
	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;
	
	String id,Tag,from,to,subject,department,section,year;
	Button delete,take,view;
	private static String url="http://10.0.2.2/attend/delete.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		delete=(Button)findViewById(R.id.delete);
		view=(Button)findViewById(R.id.view);
		
		take=(Button)findViewById(R.id.take);
		cd=new ConnectionDetector(getApplicationContext());
		
		Intent intent=getIntent();
		 id=intent.getStringExtra("ids");
		 subject=intent.getStringExtra("subs");
		 department=intent.getStringExtra("deps");
		 section=intent.getStringExtra("secs");
		 year=intent.getStringExtra("years");
		 from=intent.getStringExtra("froms");
		 to=intent.getStringExtra("tos");
		 
		 Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
		
		
	
		

		//	Intent i=new Intent(Delete.this,Classlist.class);
		//	startActivity(i);
		
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Tag="delete";
				new check().execute();
				Intent intent=new Intent(Delete.this,Classlist.class);
				startActivity(intent);
					
			}
		});
		
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(Delete.this,Absentlistformate.class);
				startActivity(intent);
					
				
				
			}
		});
		
		
	take.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(Delete.this,Takeattendence.class);
				intent.putExtra("id", id);
				intent.putExtra("subject", subject);
				intent.putExtra("department", department);
				intent.putExtra("section", section);
				intent.putExtra("year", year);
				intent.putExtra("froms", from);
				intent.putExtra("tos", to);
				
				startActivity(intent);
				
			}
		});
		
	}
	
	
	
class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Delete.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("deleteid", id));
			
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

