package za.co.entelect;

import za.co.entelect.business.MyAppSettingsManager;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsActivity extends PreferenceActivity {

	MyAppSettingsManager settingsManager;

	private void logBefore(){
		Log.d("currentTimeout BEFORE", "" + settingsManager.getCurrentTimeout(getContentResolver()));
		Log.d("storedTimeout BEFORE", "" + settingsManager.getStoredTimeout());
		Log.d("toggleValue BEFORE", "" + settingsManager.getToggle());
	}
	
	private void logAfer(){
		Log.d("currentTimeout AFTER", "" + settingsManager.getCurrentTimeout(getContentResolver()));
		Log.d("storedTimeout AFTER", "" + settingsManager.getStoredTimeout());
		Log.d("toggleValue AFTER", "" +settingsManager.getToggle());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
	}
	
}
