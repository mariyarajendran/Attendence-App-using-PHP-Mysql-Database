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

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String myJSON;
    JSONArray peoples = null;
	    ProgressDialog pDialog;
		int i;
	ArrayList<Integer>  dt=new ArrayList<Integer>();	
	private GridView gv;
	private Adapter adapter;
		ArrayList<Seat> seat = new ArrayList<Seat>();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv  = (GridView) findViewById(R.id.gridView1);
        
        getData();
        
      
        gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				Seat _seat = seat.get(pos);
				Toast.makeText(MainActivity.this,_seat.getSeatno() , Toast.LENGTH_LONG).show();
				if( _seat.getImg() == R.drawable.rred){
					seat.remove(pos);
					seat.add(pos, new Seat(_seat.getSeatno(), R.drawable.rblack));
					adapter.notifyDataSetChanged();
				}else if( _seat.getImg() == R.drawable.rblack){
					seat.remove(pos);
					seat.add(pos, new Seat(_seat.getSeatno(), R.drawable.rred));
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
        
   
	
	protected void showList(){
        try {
        	
        	 
            JSONObject jsonObj = new JSONObject(myJSON);
          
            int er=jsonObj.getInt("error");
            
            String e=String.valueOf(er);
            
            Toast.makeText(getApplicationContext(), e, Toast.LENGTH_LONG).show();
            //res=jsonObj.getString("error");
            
            peoples=jsonObj.getJSONArray("selected_seat");
            
           for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                int id = c.getInt("selected");
              
               
                  dt.add(id);
            
            }
            
          
            
            if(er==0){
                peoples = jsonObj.getJSONArray("selected_seat");
            	for(int i=0;i<100;i++){
            		Log.d("sear1", "sear1");
            	          	if(dt.contains(i)){
            			Log.i("sear2", "sear2"+dt.contains(i));
            			seat.add(new Seat("S"+i, R.drawable.rgreen)); 
            			Log.d("sear3", "sear3");
            	          	}
            				else{
                				seat.add(new Seat("S"+i, R.drawable.rred));	
                     			
                			}}
            			}
            adapter = new Adapter(this, seat);
	        gv.setAdapter(adapter);
	        gv.setSelector(R.drawable.transparent);

      
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
	     			pDialog = new ProgressDialog(MainActivity.this);
	     			pDialog.setMessage("Logging In..");
	     			pDialog.setIndeterminate(false);
	     			pDialog.setCancelable(false);
	     			pDialog.show();}
	        	
	        	
	            @Override
	            protected String doInBackground(String... params) {
	                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
	                Log.d("1", "1");
	                HttpPost httppost = new HttpPost("http://10.0.2.2/MovieLogin/seatp.php");
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
