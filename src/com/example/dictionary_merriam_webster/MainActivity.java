package com.example.dictionary_merriam_webster;

import java.io.IOException;
import java.util.Iterator;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView output;
	Button btnSearch;
	EditText txtinput;
	Database data;
	private ProgressDialog mProgressDialog = null;
	private Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		output = (TextView)findViewById(R.id.tvoutput);
		btnSearch = (Button)findViewById(R.id.btnsearch);
		txtinput = (EditText)findViewById(R.id.txtinput);
		mContext = this;
		//data = new Database();

		new MyAsyncTask().execute("");		
				
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
				
				//setFullListData();
				int ix = 0;
				String value = txtinput.getText().toString().toLowerCase();
		       /* for (ix = 0; ix < data.Words.size(); ix++) {
		            String it = data.Words.get(ix).toLowerCase();
		            if (it.startsWith(value)) {
		                break;
		            }
		        }*/
		        
		        
		        if (ix != -1) {
		           // String t = data.Words.get(ix);
		            try {
		                Word.LoadMean();
		               
		            	//output.setText(mean);
		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
				
				
			}
			}
		});
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	private class MyAsyncTask extends AsyncTask<String, Integer, Integer>{
    	boolean isCancel = false;
    	@Override
    	protected void onPreExecute() {
    		// TODO Khoi tao
    		super.onPreExecute();
    		mProgressDialog = new ProgressDialog(MainActivity.this);
    		mProgressDialog.setTitle("Dialog");
    		mProgressDialog.setMessage("Chờ Xíu");
    		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		mProgressDialog.setMax(100);
    		mProgressDialog.show();
    		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					isCancel = true;
				}
			});
    	}
    	@Override
    	protected Integer doInBackground(String... params) {
    		// TODO background
    		int nDownload = 0;
    		try {
    			data = new Database();
    			while(nDownload < 100 && !isCancel){
    				//Thread.sleep(100);
    				nDownload += 10;
    				publishProgress(nDownload);
    				
    			}
    			
			} catch (Exception e) {
			}
    		return nDownload;
    	}
    	@Override
    	protected void onProgressUpdate(Integer... values) {
    		
    		super.onProgressUpdate(values);
    		mProgressDialog.setProgress(values[0]);
    		if(values[0] == 25){
    			mProgressDialog.setMessage("Step 2");
    			
    		}
    		if(values[0] == 50){
    			mProgressDialog.setMessage("Step 3");
    			
    		}
    		if(values[0] == 75){
    			mProgressDialog.setMessage("Step 4");
    			
    		}
    	}
    	@Override
    	protected void onPostExecute(Integer result) {
    		// TODO Finish
    		super.onPostExecute(result);
    		mProgressDialog.dismiss();
    		Toast.makeText(mContext, "finish at :" + result + " %", Toast.LENGTH_SHORT).show();
    		if(result == 100){
    		}
    	}
    }
	
	
	
	
	
	
	

}
