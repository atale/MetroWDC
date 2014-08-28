package ca.atale.metrowdc;

import java.util.ArrayList;
import java.util.Arrays;

import ca.atale.metrowdc.R;
import ca.atale.metrowdc.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{

	private ListView mainListView ;  
	private ArrayAdapter< String > listAdapter ;  
	
	  Integer[] imageId = {
		      R.drawable.trainicon,
		      R.drawable.busicon };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		 
	    mainListView = (ListView) findViewById( R.id.mainmenu );  
	     
	    String[] mainMenu = new String[] {
	    		getResources().getString( R.string.menu_rail ),
	    		getResources().getString( R.string.menu_bus )
	    };
	       
	    ArrayList< String > mainList = new ArrayList< String >();  
	    mainList.addAll( Arrays.asList(mainMenu) );  
	       
	    CustomList adapter = new
	            CustomList(MainActivity.this, mainMenu, imageId);
	    
	    //listAdapter = new ArrayAdapter<String>(this, R.layout.main_list, mainList);  	      
	      
	    mainListView.setAdapter( adapter );     
		
	    mainListView.setOnItemClickListener(new OnItemClickListener(){
	    	   public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	    		   
	    		   Intent i = new Intent(MainActivity.this, RailLineActivity.class);
	    		   startActivity(i);
	    	   }
	    
		//final Button railBut =(Button) findViewById(R.id.button_railway);
		//railBut.setOnClickListener(new View.OnClickListener() {
			
		//	@Override
		//	public void onClick(View v) {
			
			//	Log.d("hello","Heelo");
				//Intent intent = new Intent(MainActivity.this, RailLineActivity.class);
				//startActivity(intent);			
	    });
	
	}
}
