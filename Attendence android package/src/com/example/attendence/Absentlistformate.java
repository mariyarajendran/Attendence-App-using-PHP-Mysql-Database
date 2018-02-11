package com.example.attendence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Absentlistformate extends Activity {
	
	  String myJSON;
	    String results; 
	    HashMap<String,String> persons;
	    
	    JSONArray peoples = null;
	    ProgressDialog pDialog,ppDialog;
	    
	    ArrayList<String> dt;
	 
	    ArrayList<HashMap<String, String>> personList;

	String subject,department,section,year;
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_absentlistformate);
		list=(ListView)findViewById(R.id.list);
		dt=new ArrayList<String>();
		personList=new ArrayList<HashMap<String,String>>();
		
		
		
		Intent intent=getIntent();
		subject=intent.getStringExtra("subject");
		department=intent.getStringExtra("department");
		section=intent.getStringExtra("section");
		year=intent.getStringExtra("year");
		
		
		getDatas();
	}
	
	
	
	protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples=jsonObj.getJSONArray("result");
            
            for(int i=1;i<peoples.length();i++){
                 JSONObject c = peoples.getJSONObject(i);
                String absent = c.getString("absents");
                String time = c.getString("dateandtime");
                String sub = c.getString("subject");
                String dep = c.getString("department");
                String sec = c.getString("section");
                String year = c.getString("year");
               
 
                 persons = new HashMap<String,String>();
                 persons.put("absent", absent);
                 persons.put("time",time);
                 persons.put("sub", sub);
                 persons.put("dep",dep);
                 persons.put("sec", sec);
                 persons.put("year",year);
              
                personList.add(persons);
            }
 
 
 String[] from=new String[]{
		"absent",
		"time",
		"sub",
		"dep",
		"sec",
		"year"
		 
		 
	 } ;          
	 
	 
	 int[] to=new int[]{
			 R.id.absent,
			R.id.time,
			R.id.subject,
			R.id.department,
			R.id.section,
			R.id.year
			
			
	 };
 
            ListAdapter adapter = new SimpleAdapter(
                    Absentlistformate.this, personList, R.layout.absentfake,
                   from,
                    to
            );
 
            list.setAdapter(adapter);
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
    }
	
	
	
	public void getDatas(){
        class GetDataJSON extends AsyncTask<String, Void, String>{
 
        	
        	
     		@Override
     		protected void onPreExecute() {
     			super.onPreExecute();
     			pDialog = new ProgressDialog(Absentlistformate.this);
     			pDialog.setMessage("Logging In..");
     			pDialog.setIndeterminate(false);
     			pDialog.setCancelable(false);
     			pDialog.show();}
        	
        	
            @Override
            protected String doInBackground(String... params) {
            	
            
            		con("http://10.0.2.2/attend/finalabsentlist.php");
            		
            	
            	
            	return results;
              
            	
                
            }
 
            @Override
            protected void onPostExecute(String result){
                myJSON=result;
               
                showList();
               pDialog.dismiss(); 
            
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
 
 
 public String con(String c){
	 
	 DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
     Log.d("1", "1");
     HttpPost httppost = new HttpPost(c);
     Log.d("2", "2");
     // Depends on your web service
     httppost.setHeader("Content-type", "application/json");

     InputStream inputStream = null;
      results = null;
     try {
         HttpResponse response = httpclient.execute(httppost);
         HttpEntity entity = response.getEntity();

         inputStream = entity.getContent();
         // json is UTF-8 by default
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
         StringBuilder sb = new StringBuilder();

         String line = null;
         while ((line = reader.readLine()) != null)
         {
             sb.append(line + "\n");
         }
         results = sb.toString();
     } catch (Exception e) {
         // Oops
     }
     finally {
         try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
     }
     return results;
	 
	 
 }


	
}
