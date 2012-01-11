package com.creatine.locLogger;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocLoggerActivity extends Activity {
	
	private Intent myIntent;
	private PendingIntent pendingIntent;
	private LocationManager locationManager;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button buttonStart = (Button)findViewById(R.id.buttonStart);
		Button buttonStop = (Button)findViewById(R.id.buttonStop);
		Button buttonSettings = (Button)findViewById(R.id.buttonSettings);
		
		buttonStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				myIntent = new Intent(LocLoggerActivity.this, LoggerService.class);
				pendingIntent = PendingIntent.getService(LocLoggerActivity.this, 0, myIntent, 0);
				AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
					Toast.makeText(LocLoggerActivity.this, "GPS not on, service not started", Toast.LENGTH_SHORT).show();
				}
				else{
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 900000, pendingIntent);
				}
			}
		});
	
		buttonStop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
				alarmManager.cancel(pendingIntent);
				Toast.makeText(LocLoggerActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();
			}
		});
		buttonSettings.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(LocLoggerActivity.this, LogSettings.class);
				startActivity(i);
			}
		});
	}
}
	