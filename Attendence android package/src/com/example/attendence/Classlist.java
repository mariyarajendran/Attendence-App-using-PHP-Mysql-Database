package com.example.attendence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

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
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Classlist extends Activity {
	
	 String myJSON,item;

	    String res="";
	 int count=0;
	 Globalclass class1;
	    JSONArray peoples = null;
	    ProgressDialog pDialog;

	    ArrayList<HashMap<String, String>> personList;
		   ListAdapter adapter;
		   ListView list;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classlist);
		list=(ListView)findViewById(R.id.list);
		
		personList=new ArrayList<HashMap<String,String>>();
		
		getData();
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				  String listid =((TextView)view.findViewById(R.id.id)).getText().toString();
				  String listsub =((TextView)view.findViewById(R.id.subject)).getText().toString();
				  String listdep =((TextView)view.findViewById(R.id.department)).getText().toString();
					  String listsec =((TextView)view.findViewById(R.id.section)).getText().toString();
					  String listyear =((TextView)view.findViewById(R.id.year)).getText().toString();
					  String listfrom =((TextView)view.findViewById(R.id.from)).getText().toString();
					 String listto=((TextView)view.findViewById(R.id.to)).getText().toString();
					 
				  
				 Intent intent=new Intent(getApplicationContext(),Delete.class);
				 intent.putExtra("ids", listid);
				 intent.putExtra("subs", listsub);
				 intent.putExtra("deps", listdep);
				 intent.putExtra("secs", listsec);
				 intent.putExtra("years", listyear);
				 intent.putExtra("froms", listfrom);
				 intent.putExtra("tos", listto);
				 
				 startActivity(intent);
				  
				  Toast.makeText(getApplicationContext(), listid, Toast.LENGTH_LONG).show();
				
			}
		});
	           
		
		
		
	}


	
	
	
	protected void showList(){
        try {
        	
        	 
            JSONObject jsonObj = new JSONObject(myJSON);
          
           
            	
            	 peoples = jsonObj.getJSONArray("result");
            	 
            	
            	
            	
            
            
            
            	  // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                   
           for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("classid");
                String subject=c.getString("subject");
                String department=c.getString("department");
                String section=c.getString("section");
                String year=c.getString("year");
                String from=c.getString("rollfrom");
                String to=c.getString("rollto");
              
               
 
                HashMap<String,String> persons = new HashMap<String,String>();
 
                persons.put("classid",id);
                persons.put("subject","Subject: "+subject);
                persons.put("department","Department: "+department);
                persons.put("section","Section: "+section);
                persons.put("year","Year: "+year);
                persons.put("rollfrom",from);
                persons.put("rollto",to);
              
                
 
                personList.add(persons);
            }
 
           adapter = new SimpleAdapter(
                    Classlist.this, personList, R.layout.listv,
                    new String[]{"classid","subject","department","section","year","rollfrom","rollto"},
                    new int[]{R.id.id,R.id.subject,R.id.department,R.id.section,R.id.year,R.id.from,R.id.to}
            );
           
         
          
           list.setAdapter(adapter);
           list.invalidate();
        
           
           
         
         
           
           
           
          
            //	Toast.makeText(getApplicationContext(), "Error please try again", Toast.LENGTH_SHORT).show();
         
           
        }
            catch (JSONException e) {
               e.printStackTrace();
           }
    
       }
	
	
	
	
	
	public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String>{
 
        	
        	
     		@Override
     		protected void onPreExecute() {
     			super.onPreExecute();
     			pDialog = new ProgressDialog(Classlist.this);
     			pDialog.setMessage("Logging In..");
     			pDialog.setIndeterminate(false);
     			pDialog.setCancelable(false);
     			pDialog.show();}
        	
        	
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                Log.d("1", "1");
                HttpPost httppost = new HttpPost("http://10.0.2.2/attend/classlist.php");
                Log.d("2", "2");
                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");
 
                InputStream inputStream = null;
                String result = null;
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
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
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



 
	
	

}
