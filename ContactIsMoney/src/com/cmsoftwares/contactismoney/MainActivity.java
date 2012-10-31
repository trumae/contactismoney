package com.cmsoftwares.contactismoney;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.util.Log;

public class MainActivity extends Activity {
	private static final String TAG = "ContactIsMoney";
	private ListView mContactList;
	public static long idContact = 0;
	public static SimpleCursorAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContactList = (ListView) this.findViewById(R.id.contactsList);
		mContactList.setOnItemClickListener(mMessageClickedHandler); 
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

	private void populateContactList() {
		// Build adapter with contact entries
		Cursor cursor = getContacts();

		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME };
		adapter = new SimpleCursorAdapter(this,
				R.layout.contact_entry, cursor, fields,
				new int[] { R.id.contactEntryText });
		mContactList.setAdapter(adapter);
	}

	private Cursor getContacts() {
		boolean mShowInvisible = false;
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME};
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ (mShowInvisible ? "0" : "1") + "'";
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		return managedQuery(uri, projection, selection, selectionArgs,
				sortOrder);
	}
	
	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long id) {
			idContact = id;
			goRegistro();			
		}
	};

	protected void goRegistro() {
		Intent i = new Intent(this, RegistersActivity.class);
		startActivity(i);
	}
}
