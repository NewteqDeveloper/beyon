package za.co.entelect.business;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

public class MyAppSettingsManager {
	
	public static String TOGGLE_SCREEN_TIMEOUT = "toggle-screen-timeout";
	public static String ORIGINAL_SCREEN_TIMEOUT = "original-screen-timeout";
	
	private int RESET_TIMEOUT = 30000;
	private int TIMEOUT_OFF = -1;
	
	private SettingsManager settings;
	
	public MyAppSettingsManager(Context context){
		settings = new SettingsManager(context);
	}
	
	public int getCurrentTimeout(ContentResolver content){
		try{
			return android.provider.Settings.System.getInt(content, Settings.System.SCREEN_OFF_TIMEOUT);
		}
		catch (SettingNotFoundException ex){
			Log.e("SettingNotFoundException", "Screen timeout not found");
			return -100;
		}
	}
	
	private void setCurrentTimeout(ContentResolver content, int value){
		android.provider.Settings.System.putInt(content, Settings.System.SCREEN_OFF_TIMEOUT, value);
	}
	
	public int getStoredTimeout(){
		return settings.getInt(ORIGINAL_SCREEN_TIMEOUT);
	}
	
	private void setStoredTimeout(int value) {
		settings.setInt(ORIGINAL_SCREEN_TIMEOUT, value);
	}
	
	public boolean getToggle(){
		return settings.getBoolean(TOGGLE_SCREEN_TIMEOUT);
	}
	
	private void setToggle(boolean value){
		settings.setBoolean(TOGGLE_SCREEN_TIMEOUT, value);
	}
	
	private void toggle(boolean current){
		settings.setBoolean(TOGGLE_SCREEN_TIMEOUT, !current);
	}
	
	public void toggleScreenTimeout(){
		boolean currentToggle = getToggle();
		setToggle(!currentToggle);
	}
	
	public void resetTimeout(ContentResolver content){
		setToggle(false);
		setCurrentTimeout(content, RESET_TIMEOUT);
		setStoredTimeout(RESET_TIMEOUT);
	}
	
	public void toggleScreenTimeout(ContentResolver content) {
		boolean currentToggle = getToggle();
		toggleScreenTimeout(content, !currentToggle);
		//toggle(currentToggle);
	}
	
	public void toggleScreenTimeout(ContentResolver content, boolean currentToggle){
		if (currentToggle){
			storeCurrentScreenTimeout(content);
			
			setScreenAlwaysOn(content);
			
		}
		else{
			int currentTimeout = getCurrentScreenTimeout(content);
			//if the current timeout is always on, then reset it to what it was previously
			if (currentTimeout == -1)
			{
				setScreenTimeoutBack(content);
			}
		}
	}
	
	private int getCurrentScreenTimeout(ContentResolver content){
		try {
			int current = android.provider.Settings.System.getInt(content, Settings.System.SCREEN_OFF_TIMEOUT);
			return current;
		}
		catch (SettingNotFoundException ex)
		{
			Log.e("SettingNotFoundException", "Screen timeout setting not found");
			Log.e("SettingNotFoundException", ex.getMessage());
			return -1;
		}
	}
	
	private void storeCurrentScreenTimeout(ContentResolver content){
		try
		{
			int current = android.provider.Settings.System.getInt(content, Settings.System.SCREEN_OFF_TIMEOUT);
			settings.setInt(ORIGINAL_SCREEN_TIMEOUT, current);
		}
		catch (SettingNotFoundException ex){
			Log.e("SettingNotFoundException", "Screen timeout setting not found");
			Log.e("SettingNotFoundException", ex.getMessage());
		}
	}
	
	private void setScreenAlwaysOn(ContentResolver content){
		android.provider.Settings.System.putInt(content, Settings.System.SCREEN_OFF_TIMEOUT, TIMEOUT_OFF);
	}
	
	private void setScreenTimeoutBack(ContentResolver content){
		int original = settings.getInt(ORIGINAL_SCREEN_TIMEOUT, 0);
		if (original == 0){
			android.provider.Settings.System.putInt(content, Settings.System.SCREEN_OFF_TIMEOUT, RESET_TIMEOUT);
		}
		else
		{
			android.provider.Settings.System.putInt(content, Settings.System.SCREEN_OFF_TIMEOUT, original);
		}
	}
}
