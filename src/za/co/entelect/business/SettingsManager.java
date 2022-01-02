package za.co.entelect.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsManager {
	
	private Context context;
	
	public SettingsManager(Context context){
		this.context = context;
	}
	
	private SharedPreferences getSharedPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(context);		
	}
	
	public boolean getBoolean(String setting)
	{
		return getSharedPreferences().getBoolean(setting, false);
	}
	
	public void setBoolean(String setting, boolean value){
		SharedPreferences settings = getSharedPreferences();
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(setting, value);
		editor.commit();
	}
	
	public String getString(String setting){
		return getString(setting, null);
	}
	
	public String getString(String setting, String defaultValue){
		return getSharedPreferences().getString(setting, defaultValue);
	}
	
	public void setString(String setting, String value){
		SharedPreferences settings = getSharedPreferences();
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(setting, value);
		editor.commit();
	}
	
	public int getInt(String setting){
		return getInt(setting, -1);
	}
	
	public int getInt(String setting, int defaultValue){
		return getSharedPreferences().getInt(setting, defaultValue);
	}
	
	public void setInt(String setting, int value){
		SharedPreferences settings = getSharedPreferences();
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(setting, value);
		editor.commit();
	}
	
}
