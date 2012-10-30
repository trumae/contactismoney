package com.cmsoftwares.contactismoney;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;

public class MainActivity extends Activity{
	private static final String TAG = "ContactIsMoney";
	private ListView mContactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContactList = (ListView) this.findViewById(R.id.contactsList);
		try {
			this.populateContactList();
		} catch (Exception ex) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Populate the contact list based on account currently selected in the
	 * account spinner.
	 */
	private void populateContactList() {
		// Build adapter with contact entries
		Cursor cursor = getContacts();

		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.contact_entry, cursor, fields,
				new int[] { R.id.contactEntryText });
		mContactList.setAdapter(adapter);
	}

	
    private Cursor getContacts()
    {
    	boolean mShowInvisible = false;
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" +
                (mShowInvisible ? "0" : "1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * Launches the ContactAdder activity to add a new contact to the selected accont.
     */
    protected void launchContactAdder() {
        //Intent i = new Intent(this, ContactAdder.class);
        //startActivity(i);
    }
}
