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
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends Activity {
	private static final String TAG = "ContactIsMoney";
	private ListView mContactList;

	public static RegistroDBAdapter registroDBAdapter;
	public static long selected = 0;
	public static SimpleCursorAdapter adapter;
	public static Cursor cursorContacts;
	public static NumberFormat moedaFormatter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		moedaFormatter = NumberFormat.getCurrencyInstance();

		registroDBAdapter = new RegistroDBAdapter(this);
		registroDBAdapter.open();
		mContactList = (ListView) this.findViewById(R.id.contactsList);
		mContactList.setOnItemClickListener(mMessageClickedHandler);
		try {
			this.populateContactList();
		} catch (Exception ex) {
			Log.e("Erro", "Erro populando Contact list " + ex.getMessage());
		}
	}

	@Override
	protected void onDestroy() {
		super.onPause();
		registroDBAdapter.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void populateContactList() {
		// Build adapter with contact entries
		cursorContacts = getContacts();

		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME, "balance" };
		adapter = new SimpleCursorAdapter(this, R.layout.contact_entry,
				cursorContacts, fields, new int[] { R.id.contactEntryText, R.id.contactBalance });

		mContactList.setAdapter(adapter);
	}

	private Cursor getContacts() {
		boolean mShowInvisible = false;
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.PHOTO_ID,
				ContactsContract.Contacts.LAST_TIME_CONTACTED };
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ (mShowInvisible ? "0" : "1") + "'";
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		Cursor orig = managedQuery(uri, projection, selection, selectionArgs,
				sortOrder);
		ContactsCursor cursor = new ContactsCursor(orig);
		return cursor;
	}

	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long id) {
			selected = pos;
			goRegistro();
		}
	};

	protected void goRegistro() {
		Intent i = new Intent(this, RegistersActivity.class);
		startActivity(i);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        populateContactList();    
    }
}
