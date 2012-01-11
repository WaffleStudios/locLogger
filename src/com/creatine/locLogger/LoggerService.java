package com.creatine.locLogger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.List;

public class LoggerService extends Service {
	private String locLog;
	@Override
	public void onCreate() {
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		locLog = "";
		Looper looper = Looper.getMainLooper();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		LocationManager locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		};	
		locationManager.requestSingleUpdate(locationManager.GPS_PROVIDER, locationListener, looper);
		Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
		time.setTime(System.currentTimeMillis());
		locLog += location.getLatitude() + ", " + location.getLongitude() + " (" + time.toString() + ")\n";
 		File directory = new File("/sdcard/creatine/");
 		if(!directory.exists()) {
 			directory.mkdirs();
 		}
 		File outputFile = new File(directory, "locationLog.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));
			if(bw != null) {
				bw.write(locLog);
				bw.newLine();
				bw.flush();
			}
		}
		catch (IOException e){
			Toast.makeText(this, "Directory Not Valid!", Toast.LENGTH_LONG).show();
		}
		Toast.makeText(this, "Location Logged ("+time.toString()+")", Toast.LENGTH_SHORT).show();
		if(LogSettings.getNotificationEnabled(this)) {
			Notification notification = new Notification();
			NotificationManager mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
			String ringtoneStr = sp.getString(LogSettings.SOUND, null);
			notification.sound = TextUtils.isEmpty(ringtoneStr) ? null : Uri.parse(ringtoneStr);;
			mNM.notify(1, notification);
		}
	}
	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
}