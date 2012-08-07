/**
 * 
 */
package com.mis.icequeen;

import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}

