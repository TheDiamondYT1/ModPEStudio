package tk.thediamondyt.modpeide.activities;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import tk.thediamondyt.modpeide.fragments.SettingsFragment;
import tk.thediamondyt.modpeide.R;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
            
        return super.onOptionsItemSelected(item);
    }
}
