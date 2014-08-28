package ca.atale.metrowdc;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RLineSelect extends ListActivity {
	
    private static String url = "http://api.wmata.com/Rail.svc/json/jStations?LineCode=";
    private static final String apiKey = "&api_key=ngwgg6v448jhwhbyjfda2vu4";

    private static final String TAG_STATION = "Stations";
    private static final String TAG_SNAME = "Name";
    private static final String TAG_ADDRESS = "Address";
    private static final String TAG_STREET = "Street";
    private static final String TAG_ZIP = "Zip";
    private static final String TAG_COORDLAT = "Lat";
    private static final String TAG_COORDLON = "Lon";
    
    String lineCode = null;
    
    JSONArray stations = null;   
    ArrayList<HashMap<String, String>> stationInfo;
   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rlineselect);
		
		Intent in = getIntent();
		Bundle b = in.getExtras();
		
		if(b!=null)
		{
			lineCode = (String) b.getString("LineCode");
		}
		
		stationInfo = new ArrayList<HashMap<String, String>>();
		new GetStation().execute();
	}
	
	
	private class GetStation extends AsyncTask<Void, Void, Void> {
		 
		private ProgressBar spinner;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        spinner = (ProgressBar)findViewById(R.id.pbar_railline);
            spinner.setVisibility(View.VISIBLE);

	    }

	    @Override
	    protected Void doInBackground(Void... arg0) {
	       
	        DataRetriever sh = new DataRetriever();
	        
	        String jsonStr = sh.makeServiceCall(url + lineCode + apiKey );

	        Log.d("Response: ", "> " + jsonStr);

	        if (jsonStr != null) {
	            try {
	                JSONObject jsonObj = new JSONObject(jsonStr);
	                 
	                // Getting JSON Array node
	                stations = jsonObj.getJSONArray(TAG_STATION);


	                // looping through All Contacts
	                for (int i = 0; i < stations.length(); i++) {
	                    
	                	JSONObject j = stations.getJSONObject(i);
	                    
	                    JSONObject address = j.getJSONObject(TAG_ADDRESS);
	                    
	                    String sName = j.getString(TAG_SNAME);
	                    String sLat = j.getString(TAG_COORDLAT);
	                    String sLon = j.getString(TAG_COORDLON);
	                    String sStreet = address.getString(TAG_STREET);
	                    String sZip = address.getString(TAG_ZIP);	                    

	                    HashMap<String, String> tmpStation = new HashMap<String, String>();
	                    
	                    tmpStation.put(TAG_SNAME, sName);
	                    tmpStation.put(TAG_COORDLAT, sLat);
	                    tmpStation.put(TAG_COORDLON, sLon);
	                    tmpStation.put(TAG_STREET, sStreet);
	                    tmpStation.put(TAG_ZIP, sZip);

	                    stationInfo.add(tmpStation);
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        } else {
	            Log.d("ServiceHandler", "Couldn't get any data from the url");
	        }

	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	    	Log.d("Async", "Stations Successful");
	        super.onPostExecute(result);

	        spinner.setVisibility(View.GONE);

	        ListAdapter adapter = new SimpleAdapter(RLineSelect.this, stationInfo,
	                R.layout.station_list, 
	                	new String[] {                     		
	                		TAG_SNAME, TAG_STREET, TAG_ZIP}, 
	                	new int[] {
	        				R.id.label_sName, R.id.station_address, R.id.station_zip});                    		
	        setListAdapter(adapter);
	    }

	}

	 
}
	



