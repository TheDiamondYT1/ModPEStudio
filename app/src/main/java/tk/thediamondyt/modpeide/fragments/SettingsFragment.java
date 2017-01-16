package tk.thediamondyt.modpeide.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import tk.thediamondyt.modpeide.R;

public class SettingsFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
