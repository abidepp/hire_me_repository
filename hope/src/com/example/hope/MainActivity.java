package com.example.hope;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	TextView name;
	TextView number;
	
	// url to create new product
    private static String url_create_product = "http://10.0.2.2:2020/android_connect/create_product.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         name =(TextView)findViewById(R.id.text1);
         number=(TextView)findViewById(R.id.text2);
        Button bt=(Button)findViewById(R.id.submit);
        Button getlist=(Button)findViewById(R.id.getlist);
       
        
        bt.setOnClickListener(new View.OnClickListener() {
        	 
        	 @Override
             public void onClick(View view) {
                 // creating new product in background thread
                 new CreateNewProduct().execute();
                 Log.i("called", "create_product");
             }
         });
    }
    
    public void onClick(View v)
    {
    	Intent in = new Intent(this, Allproducts.class);
    	
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    

class CreateNewProduct extends AsyncTask<String, String, String> {
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Creating Product..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		Log.i("entered","doin background");
	    String name1 = name.getText().toString();
        String number1 = number.getText().toString();
       

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name1));
        params.add(new BasicNameValuePair("number", number1));
        Log.i("added","values in list");

        // getting JSON Object
        // Note that create product url accepts POST method
        Log.i("going to","send request");
        JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                "POST", params);
        Log.i("request","sent");
        
        // check log cat for response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
            	 Log.i("product created","success");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                Log.i("process","done");

                // closing this screen
                finish();
                Log.i("finish","called");
            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return null;
	}
}
}


