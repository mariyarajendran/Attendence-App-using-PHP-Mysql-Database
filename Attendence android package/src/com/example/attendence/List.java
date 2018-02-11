package com.example.attendence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class List extends Activity {

Button btn;

	 String myJSON,item;
	 Intent is;
	    private static final String TAG_RESULTS="result";
	    private static final String TAG_ROLLNO = "rollno";
	    private static final String TAG_NAME = "name";
	    String res="";
	 int count=0;
	 Globalclass class1;
	    JSONArray peoples = null;
	    ProgressDialog pDialog;
	   //public static ArrayList<String> myVal = new ArrayList<String>() ;
	    ArrayList<String> fake = new ArrayList<String>();
	View v;
	    ArrayList<HashMap<String, String>> personList;
	   ListAdapter adapter;
		
ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		btn=(Button)findViewById(R.id.btn);
		
		list=(ListView)findViewById(R.id.list);
		class1=new Globalclass();
			
		personList = new ArrayList<HashMap<String,String>>();
		getData();
		
	






		
		
	}



	
	protected void showList(){
        try {
        	
        	 
            JSONObject jsonObj = new JSONObject(myJSON);
          
            int er=jsonObj.getInt("error");
            
            String e=String.valueOf(er);
            
            
            //res=jsonObj.getString("error");
          
            
            if(er==0){
            	
            	 peoples = jsonObj.getJSONArray("selected_seat");
            	 
            	
            	
            	
            
            
            
            	  // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                   
           for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("selected");
              
               
 
                HashMap<String,String> persons = new HashMap<String,String>();
 
                persons.put("selected",id);
              
                
 
                personList.add(persons);
            }
 
           adapter = new SimpleAdapter(
                    List.this, personList, R.layout.listv,
                    new String[]{"selected"},
                    new int[]{R.id.roll}
            );
           
           list.setAdapter(adapter);
           
           
         
            }
            
            else if(er==1){
            	
            	Toast.makeText(getApplicationContext(), "Error please try again", Toast.LENGTH_SHORT).show();
            }
           
           
           
           
           list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
           
          
            
            btn.setOnClickListener(new Button.OnClickListener(){
            	  
            	 
                @Override
                public void onClick(View v) {
                  
                    String selected = "";
                    int cntChoice = list.getCount();
      
                    SparseBooleanArray sparseBooleanArray = list.getCheckedItemPositions();
                    for(int i = 0; i < cntChoice; i++){ 
                        if(sparseBooleanArray.get(i)) { 
                            selected += list.getItemAtPosition(i).toString() + "\n";
      
                        }
      
                    }
      
                    Toast.makeText(List.this, selected, Toast.LENGTH_LONG).show();
      
                }});
      
            Log.i("attend", "attend "+class1.myVal);
            
            Log.i("find", "find"+  item);
            
     
            
            
            list.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View v,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					

            	    String place_name = ((TextView) v.findViewById(R.id.name)).getText().toString();
            	    String roll = ((TextView) v.findViewById(R.id.roll)).getText().toString();
            	
            	    class1.myVal.add(roll);
            	    class1.myVal.add(place_name);
            	    
            	   
					
					return true;
				}
			});
            
        
            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View v, int pos,
                        long id) {
                   
                     Log.i("attend", "attend "+class1.myVal);
                	
            	    String place_name = ((TextView) v.findViewById(R.id.name)).getText().toString();
            	    String roll = ((TextView) v.findViewById(R.id.roll)).getText().toString();
            	    class1.myVal.add(roll);
            	    class1.myVal.add(place_name);
            	
                }


            });
            
        }
        
 
         catch (JSONException e) {
            e.printStackTrace();
        }
 
    }
	
	
	
	 public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			
			
			Intent intent=new Intent(List.this,ResultList.class);
			startActivity(intent);
			finish();

		Toast.makeText(getApplicationContext(), "Back pressed", Toast.LENGTH_SHORT).show();
			
		
		
		
		}
	 
	
	

	public void onItemClick(AdapterView<?> a, View v, int position, long id)
	{
	    Intent intent = new Intent(List.this, ResultList.class);

	    String place_name = ((TextView) v.findViewById(R.id.name)).getText().toString();

	    intent.putExtra("place_name",place_name);
	    startActivity(intent);}
	
	
   
	
	
	 public void getData(){
	        class GetDataJSON extends AsyncTask<String, Void, String>{
	 
	        	
	        	
	     		@Override
	     		protected void onPreExecute() {
	     			super.onPreExecute();
	     			pDialog = new ProgressDialog(List.this);
	     			pDialog.setMessage("Logging In..");
	     			pDialog.setIndeterminate(false);
	     			pDialog.setCancelable(false);
	     			pDialog.show();}
	        	
	        	
	            @Override
	            protected String doInBackground(String... params) {
	                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
	                Log.d("1", "1");
	                HttpPost httppost = new HttpPost("http://10.0.2.2/m/seatp.php");
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
