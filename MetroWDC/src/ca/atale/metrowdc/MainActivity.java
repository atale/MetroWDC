package ca.atale.metrowdc;

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
import android.widget.Button;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 
		final Button railBut =(Button) findViewById(R.id.button_railway);
		railBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Log.d("hello","Heelo");
				Intent intent = new Intent(MainActivity.this, RailLineActivity.class);
				startActivity(intent);			
			}
		});
	}
}
