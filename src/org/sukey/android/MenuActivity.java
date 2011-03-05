package org.sukey.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener {
	protected Button mCascadeButton;
	protected Button mMapButton;
	protected Button mCompassButton;
	protected Button mReportButton;
	protected Button mSettingsButton;
	protected Button mAboutButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		mCascadeButton = (Button)findViewById(R.id.cascade);
		mMapButton = (Button)findViewById(R.id.map);
		mCompassButton = (Button)findViewById(R.id.compass);
		mReportButton = (Button)findViewById(R.id.report);
		mSettingsButton = (Button)findViewById(R.id.settings);
		mAboutButton = (Button)findViewById(R.id.about);
		
		mCascadeButton.setOnClickListener(this);
		mMapButton.setOnClickListener(this);
		mCompassButton.setOnClickListener(this);
		mReportButton.setOnClickListener(this);
		mSettingsButton.setOnClickListener(this);
		mAboutButton.setOnClickListener(this);
		
	}

	public void onClick(View view) {
		if (view == mCascadeButton) {
			Intent intent = new Intent(this, org.sukey.android.cascade.SettingsActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_cascade, 500).show();
		}
		
		if (view == mMapButton) {
			Intent intent = new Intent(this, org.sukey.android.map.MapViewActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_map, 500).show();
		}
		/*
		if (view == mCompassButton) {
			Intent intent = new Intent(this, org.sukey.android.compass.CompassViewActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_compass, 500).show();
		}
		if (view == mReportButton) {
			Intent intent = new Intent(this, org.sukey.android.report.ReportViewActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_report, 500).show();
		}
		if (view == mSettingsButton) {
			Intent intent = new Intent(this, org.sukey.android.settings.SettingsMenuActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_settings, 500).show();
		}
		if (view == mAboutButton) {
			Intent intent = new Intent(this, org.sukey.android.map.AboutWindowActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.toast_about, 500).show();
		}
		*/
	}
}
