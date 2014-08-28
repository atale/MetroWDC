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

public class RailLineActivity extends ListActivity {

    private static String url = "http://api.wmata.com/Rail.svc/json/jLines?api_key=";
    private static final String apiKey = "ngwgg6v448jhwhbyjfda2vu4";
    //Put  apiKey/url in values/string.xml later :)
    
    private static final String TAG_LINES = "Lines";
    private static final String TAG_DISPLAYNAME = "DisplayName";
    private static final String TAG_ENDSTATIONCODE = "EndStationCode";
    private static final String TAG_INDESTINATION1 = "InternalDestination1";
    private static final String TAG_INDESTINATION2 = "InternalDestination2";
    private static final String TAG_LINECODE = "LineCode";
    private static final String TAG_STARTSTATIONCODE = "StartStationCode";
   
    JSONArray lines = null;
     
    ArrayList<HashMap<String, String>> railLines;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rail_line);
		
		Log.d("hello","hello");
		
	       railLines = new ArrayList<HashMap<String, String>>();       
	       new GetLines().execute();
	       
	       ListView lv = getListView();
	       
	       lv.setOnItemClickListener(new OnItemClickListener(){
	    	   public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	    		   
	    		   String lineName = ((TextView) view.findViewById(R.id.displayName))
	                        .getText().toString();
	    		   
	    		   Intent i = new Intent(RailLineActivity.this, RLineSelect.class);
	    		   
	    		   i.putExtra("title", lineName);		   
	    		   i.putExtra(TAG_ENDSTATIONCODE, railLines.get(position).get(TAG_ENDSTATIONCODE));
	    		   i.putExtra(TAG_INDESTINATION1, railLines.get(position).get(TAG_INDESTINATION1));
	    		   i.putExtra(TAG_INDESTINATION2, railLines.get(position).get(TAG_INDESTINATION2));
	    		   i.putExtra(TAG_LINECODE, railLines.get(position).get(TAG_LINECODE));
	    		   i.putExtra(TAG_STARTSTATIONCODE, railLines.get(position).get(TAG_STARTSTATIONCODE));

	    		   
	    		   startActivity(i);
	    		   Log.d("yolo","asdasd");
	    		   
	    	   }
	       });
	       
	       
	}
	
	private class GetLines extends AsyncTask<Void, Void, Void> {
		 
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
            
            String jsonStr = sh.makeServiceCall(url + apiKey );
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    
                    lines = jsonObj.getJSONArray(TAG_LINES);
 
                    
                    for (int i = 0; i < lines.length(); i++) {
                        JSONObject j = lines.getJSONObject(i);
                         
                        String displayName = j.getString(TAG_DISPLAYNAME);
                        String endStationCode = j.getString(TAG_ENDSTATIONCODE);
                        String inDestination1 = j.getString(TAG_INDESTINATION1);
                        String inDestination2 = j.getString(TAG_INDESTINATION2);
                        String lineCode = j.getString(TAG_LINECODE);
                        String startCode = j.getString(TAG_STARTSTATIONCODE);
 
                        HashMap<String, String> tmpLine = new HashMap<String, String>();
 
                        
                        tmpLine.put(TAG_DISPLAYNAME, displayName);
                        tmpLine.put(TAG_ENDSTATIONCODE, endStationCode);
                        tmpLine.put(TAG_INDESTINATION1, inDestination1);
                        tmpLine.put(TAG_INDESTINATION2, inDestination2);
                        tmpLine.put(TAG_LINECODE, lineCode);
                        tmpLine.put(TAG_STARTSTATIONCODE, startCode);

                        
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
            
            spinner.setVisibility(View.GONE);

            ListAdapter adapter = new SimpleAdapter(RailLineActivity.this, railLines,
                    R.layout.list_item, new String[] {                     		
                    		TAG_DISPLAYNAME
                    		}, 
                    		new int[] {R.id.displayName});                    		

            setListAdapter(adapter);
        }
 
    }
	
	 
}
