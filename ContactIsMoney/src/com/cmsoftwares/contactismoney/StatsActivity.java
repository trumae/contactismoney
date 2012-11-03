package com.cmsoftwares.contactismoney;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class StatsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stats, menu);
        return true;
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		int id = item.getItemId();
	    switch (id) {	        
	        case R.id.menuAllContacts:
	            this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
