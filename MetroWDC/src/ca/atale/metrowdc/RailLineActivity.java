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
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RailLineActivity extends ListActivity {

    private static String url = "http://api.wmata.com/Rail.svc/json/jLines?api_key=";
    private static final String apiKey = "ngwgg6v448jhwhbyjfda2vu4";
    
    private static final String TAG_LINES = "Lines";
    private static final String TAG_DISPLAYNAME = "DisplayName";
    private static final String TAG_ENDSTATIONCODE = "EndStationCode";
    private static final String TAG_INDESTINATION1 = "InternalDestination1";
    private static final String TAG_INDESTINATION2 = "InternalDestination2";
    private static final String TAG_LINECODE = "LineCode";
    private static final String TAG_STARTSTATIONCODE = "StartStationCode";
 
    // contacts JSONArray
    JSONArray lines = null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> railLines;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rail_line);
		
		Log.d("hello","hello");
		
	       railLines = new ArrayList<HashMap<String, String>>();       
	       new GetLines().execute();
	}
	
	private class GetLines extends AsyncTask<Void, Void, Void> {
		 
		private ProgressDialog pDialog;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RailLineActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            DataRetriever sh = new DataRetriever();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + apiKey );
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    lines = jsonObj.getJSONArray(TAG_LINES);
 
                    // looping through All Contacts
                    for (int i = 0; i < lines.length(); i++) {
                        JSONObject c = lines.getJSONObject(i);
                         
                        String displayName = c.getString(TAG_DISPLAYNAME);
                        String endStationCode = c.getString(TAG_ENDSTATIONCODE);
                        String inDestination1 = c.getString(TAG_INDESTINATION1);
                        String inDestination2 = c.getString(TAG_INDESTINATION2);
                        String lineCode = c.getString(TAG_LINECODE);
                        String startCode = c.getString(TAG_STARTSTATIONCODE);
 
                        HashMap<String, String> tmpLine = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        tmpLine.put(TAG_DISPLAYNAME, displayName);
                        tmpLine.put(TAG_ENDSTATIONCODE, endStationCode);
                        tmpLine.put(TAG_INDESTINATION1, inDestination1);
                        tmpLine.put(TAG_INDESTINATION2, inDestination2);
                        tmpLine.put(TAG_LINECODE, lineCode);
                        tmpLine.put(TAG_STARTSTATIONCODE, startCode);

                        // adding contact to contact list
                        railLines.add(tmpLine);
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
        	Log.d("Async", "Successful");
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(RailLineActivity.this, railLines,
                    R.layout.list_item, new String[] {                     		
                    		TAG_DISPLAYNAME, TAG_ENDSTATIONCODE,TAG_INDESTINATION1, 
                    		TAG_INDESTINATION2, TAG_LINECODE, TAG_STARTSTATIONCODE
                    		}, 
                    		new int[] {R.id.displayName, R.id.endStationCode, R.id.internalDestination1, 
            					R.id.internalDestination2, R.id.lineCode, R.id.startStationCode});
            setListAdapter(adapter);
        }
 
    }
	
	 
}
