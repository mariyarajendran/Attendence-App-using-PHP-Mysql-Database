package com.example.attendence;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.attendence.Attendence.check;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultList extends Activity {

	
	
	ArrayList<String> ld;
	ListView li;
	TextView txtatt;
	Globalclass global;
	Button put;
	
	  String Tag;
	    
	    
	   JSONObject  json;
	    
	    ConnectionDetector cd;
		Boolean isInternetPresent=false;
		
		JSONParser jsonParser=new JSONParser();
		ProgressDialog progressDialog;
		
		private static String url="http://10.0.2.2/attend/absent.php";
		
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_list);
		ld=new ArrayList<String>();
		global=new Globalclass();
		cd=new ConnectionDetector(getApplicationContext());
		   Log.i("ones", "onss "+Globalclass.myVal);
		   Log.i("twos", "twoss "+Globalclass.myValname);
		   
		   Log.i("jesusroll", "jesusroll "+Globalclass.myValroll);
		   Log.i("jesusname", "jesusname"+Globalclass.myValname);
		
	put=(Button)findViewById(R.id.put);
		
		   ArrayAdapter<String> adapter =      
	                 new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Globalclass.myVal);
		  
		li=(ListView)findViewById(R.id.li);		
		txtatt=(TextView)findViewById(R.id.txtatt);
		
		    li.setAdapter(adapter);
		
		
						
				
	
			
			
			
			
			
		    
		    
		    
		    put.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					isInternetPresent=false;
					isInternetPresent=cd.isConnectingToInternet();
					
					if(isInternetPresent){
						
						Tag="put";
						new check().execute();
						
						Toast.makeText(getApplicationContext(), "Data insert", Toast.LENGTH_LONG).show();
						
						
					}
					
					else if(!isInternetPresent){
						
						Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
					}
					
					else{
						
						Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG).show();
						
					}
					
					
					
					 
					   Log.i("inserttwos", "inserttwo "+Globalclass.myValname);
					
				 
					
				}
			});
		    
		    
		
	}
	
	
	
	

class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(ResultList.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			
			
			
			for(String msg:Globalclass.myVal ){
				
				ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				
				
					param.add(new BasicNameValuePair("tag", Tag));
					param.add(new BasicNameValuePair("rollno", msg));
				
				//param.add(new BasicNameValuePair("name", msg2));
				
				
					  json=jsonParser.makeHttpRequest(url, "POST", param);
				
			}
			
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
