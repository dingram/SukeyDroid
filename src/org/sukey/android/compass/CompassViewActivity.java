package org.sukey.android.compass;

import org.sukey.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CompassViewActivity extends Activity implements OnClickListener {
	CompassView mCompass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compass_activity);

		mCompass = (CompassView) findViewById(R.id.compass);
		mCompass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		mCompass.setOffset(mCompass.getOffset() + 15);
	}

}
