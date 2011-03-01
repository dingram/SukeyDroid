package org.sukey.android.compass;

import java.util.List;

import org.sukey.android.R;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CompassViewActivity extends Activity implements OnClickListener,
		SensorEventListener {
	private final String TAG = "CompassViewActivity";
	protected CompassView mCompass;
	protected SensorManager sm = null;
	protected boolean sensorRunning = false;
	protected boolean canSense = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compass_activity);

		// get compass sensor
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);

		mCompass = (CompassView) findViewById(R.id.compass);
		mCompass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (!canSense) mCompass.setAngleOffset(mCompass.getAngleOffset() + 15);
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<Sensor> availableSensors = sm.getSensorList(Sensor.TYPE_ORIENTATION);
		if (availableSensors.size() > 0) {
			// register for fairly high-speed data capture - balance between smoothness and battery usage
			sm.registerListener(this, availableSensors.get(0),
					SensorManager.SENSOR_DELAY_GAME);
			canSense = true;
			sensorRunning = true;

		} else if (canSense) {
			// only show this warning once
			Toast.makeText(this, "No compass available, so the exits will not be properly angled.", Toast.LENGTH_LONG).show();
			sensorRunning = false;
			canSense = false;
		}
	}

	@Override
	protected void onPause() {
		// unregister listener
		if (sensorRunning) {
			sm.unregisterListener(this);
			sensorRunning = false;
		}
		super.onPause();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// nothing to do here
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// This must be negative
		mCompass.setAngleOffset((int) -(event.values[0] + 0.5f));
	}
}
