package za.co.entelect;

import za.co.entelect.business.MyAppSettingsManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private MyAppSettingsManager settingsManager;
	
	private Button toggleButton;
	private Button resetButton;
	
	private void setOnButtonClicks(){
		toggleButton.setOnClickListener(new ToggleButtonListener());
		
		resetButton.setOnClickListener(new ResetButtonListener());
	}
	
	private void setPreferenceListener() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener(preferencesChanged);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.settings:
	            //Toast.makeText(this, "Settings pressed", Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent(this, SettingsActivity.class);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settingsManager = new MyAppSettingsManager(this);
		
		toggleButton = (Button) findViewById(R.id.toggle);
		resetButton = (Button) findViewById(R.id.reset);
		
		setOnButtonClicks();
		
		setPreferenceListener();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private class ToggleButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			settingsManager.toggleScreenTimeout();
			
		}
	}
	
	private class ResetButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {

			settingsManager.resetTimeout(getContentResolver());
			
		}
	}
	
	OnSharedPreferenceChangeListener preferencesChanged = new SharedPreferences.OnSharedPreferenceChangeListener()
	{
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			
			if (key.equals(MyAppSettingsManager.TOGGLE_SCREEN_TIMEOUT)){
				settingsManager.toggleScreenTimeout(getContentResolver(), sharedPreferences.getBoolean(key, false));
			}
			
		}
	};
}
