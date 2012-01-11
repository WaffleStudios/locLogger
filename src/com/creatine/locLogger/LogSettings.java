package com.creatine.locLogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class LogSettings extends PreferenceActivity {
	private static final String NOTI_SOUND = "sound_toggle";
	public static final String DIRECTORY = "directory";
	public static final String FILE = "file";
	public static final String SOUND = "notification_sound";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	// Returns whether or not to use the sound
	public static boolean getNotificationEnabled(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notificationsEnabled =
            prefs.getBoolean(LogSettings.NOTI_SOUND, true);
        return notificationsEnabled;
    }

}
