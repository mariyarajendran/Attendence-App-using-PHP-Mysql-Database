package com.example.attendence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Takeattendence extends Activity {
	
	
	ConnectionDetector cd;
	Boolean isInternetPresent=false;

	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;

	String Tag;

	private static String url="http://10.0.2.2/attend/insertabsentlist.php";
	
	
	
	
	
	
 	String myJSON,m,from,to,seatfinal;
    JSONArray peoples = null;
	    ProgressDialog pDialog;
		int	count=0;
	    
		int i,fr,too;
	ArrayList<Integer>  dt=new ArrayList<Integer>();	
	ArrayList<String> posi;
	ArrayList<String> select;
	ArrayList<String> unselect;
	
	private GridView gv;
	private Adapter adapter;
		ArrayList<Seat> seat;
		String cusname,id,movie,show,section,department,year,subject;
		View v;
		
		Seat _seat;
		int finalpos;
		String po;
		Boolean comp;
		Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeattendence);
		
		
		 gv  = (GridView) findViewById(R.id.gridView1);
	        posi=new ArrayList<String>();
	        seat= new ArrayList<Seat>();
	        select=new ArrayList<String>();
	        unselect=new ArrayList<String>();
	        
	       Tag="ok";
	       
	       
	      Intent intent=getIntent();
	      id=intent.getStringExtra("id");
	      subject=intent.getStringExtra("subject");
	      department=intent.getStringExtra("department");
	      section=intent.getStringExtra("section");
	      year=intent.getStringExtra("year");
	      from=intent.getStringExtra("froms");
	     to=intent.getStringExtra("tos");
	     
	     Toast.makeText(getApplicationContext(),from+ ""+to, Toast.LENGTH_LONG).show();
	       
	     
	     fr=Integer.parseInt(from);
	     too=Integer.parseInt(to);
	       
	     
			
	 	for( int i=1;i<too;i++){
    		Log.d("sear1", "sear1");
    	          	if(dt.contains(i)){
    			Log.i("sear2", "sear2"+dt.contains(i));
    			seat.add(new Seat(""+i, R.drawable.green)); 
    			Log.d("sear3", "sear3");
    	          	}
    				else{
        				seat.add(new Seat(""+i, R.drawable.red));	
             			
        			}}
    			
    adapter = new Adapter(this, seat);
    gv.setAdapter(adapter);
	    
	        
	        gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> av, View v, int pos,
						long id) {
					// TODO Auto-generated method stub
				
					finalpos=pos;
					
					_seat = seat.get(finalpos);
					
					
					
					if( _seat.getImg() == R.drawable.red){
						seat.remove(finalpos);
						seat.add(finalpos, new Seat(_seat.getSeatno(), R.drawable.green));
						
						String poss=String.valueOf(finalpos);
					
							select.add(poss);
							Toast.makeText(getApplicationContext(),select+"", Toast.LENGTH_LONG).show();
					
						adapter.notifyDataSetChanged();
					}else if( _seat.getImg() == R.drawable.green){
						seat.remove(finalpos);
						seat.add(finalpos, new Seat(_seat.getSeatno(), R.drawable.red));
						
						String poss=String.valueOf(finalpos);
						unselect.add(poss);
						
						Toast.makeText(getApplicationContext(),unselect+"", Toast.LENGTH_LONG).show();
						
						
						
						adapter.notifyDataSetChanged();
					}
				}
				
				
				
				
				
			});
	        
	        
	     
	}

class check extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Takeattendence.this);
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
				param.add(new BasicNameValuePair("absent",seatfinal ));
				
			
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
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.takeattendence, menu);
			
			
			return true;
		}
	 
	
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		  int id = item.getItemId();
		  
		
	    if (id == R.id.ne) {
	    	
for(String m:unselect){
				
				select.remove(m);
			}
    
			if(select.isEmpty()){
				
				Toast.makeText(getApplicationContext(), "please select your seat", Toast.LENGTH_LONG).show();	
			}
			
			else{
				String seats="0";
				for(String pos:select){
					
					count=count+1;
					
					seatfinal=seats+=","+pos;
					
				}
				
				Tag="insert";
				new check().execute();
				
				Intent intent=new Intent(Takeattendence.this,Attendencehome.class);
				intent.putExtra("id", id);
				intent.putExtra("subject",subject);
				intent.putExtra("department", department);
				intent.putExtra("section", section);
				intent.putExtra("year", year);
				intent.putExtra("from", from);
				intent.putExtra("to", to);			
				intent.putExtra("absent", seatfinal);
				
				String coun=String.valueOf(count);
				Toast.makeText(getApplicationContext(), coun+" Students absent today", Toast.LENGTH_LONG).show();	
	    	
				
				startActivity(intent);
				count=0;
			
			                 } 
	        
	    }
	    return true;

	 }}

