package com.cmsoftwares.contactismoney;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import java.text.DateFormat;
import java.text.NumberFormat;
import com.cmsoftwares.contactismoney.provider.DataContentProvider;
import com.googlecode.chartdroid.core.IntentConstants;

public class MainActivity extends Activity {
	private static final String TAG = "ContactIsMoney";
	private ListView mContactList;

	public static RegistroDBAdapter registroDBAdapter;
	public static long selected = 0;
	public static SimpleCursorAdapter adapter;
	public static Cursor cursorContacts;
	public static NumberFormat moedaFormatter;
	public static DateFormat dataFormatter;

	final int DIALOG_CHARTDROID_DOWNLOAD = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		moedaFormatter = NumberFormat.getCurrencyInstance();
		dataFormatter = DateFormat.getDateInstance();

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

		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME,
				"balance" };
		adapter = new SimpleCursorAdapter(this, R.layout.contact_entry,
				cursorContacts, fields, new int[] { R.id.contactEntryText,
						R.id.contactBalance });

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

	@Override
	protected Dialog onCreateDialog(int id) {

		Log.d(TAG, "Called onCreateDialog()");

		switch (id) {
		case DIALOG_CHARTDROID_DOWNLOAD:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Download ChartDroid")
					.setMessage(
							"You need to download ChartDroid to display this data.")
					.setPositiveButton("Market download",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									startActivity(Market
											.getMarketDownloadIntent(Market.CHARTDROID_PACKAGE_NAME));
								}
							})
					.setNeutralButton("Web download",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Market.APK_DOWNLOAD_URI_CHARTDROID));
								}
							}).create();
		}
		return null;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent i;
		int id = item.getItemId();
		switch (id) {
		case R.id.menuStats:
			i = new Intent(this, StatsActivity.class);
			startActivity(i);
			return true;
		case R.id.menuGraph:
			i = new Intent(Intent.ACTION_VIEW, DataContentProvider.PROVIDER_URI);
            i.putExtra(Intent.EXTRA_TITLE, TemperatureData.DEMO_CHART_TITLE);
			i.putExtra(IntentConstants.Meta.Axes.EXTRA_FORMAT_STRING_Y, "%.1f°C");

			if (Market.isIntentAvailable(this, i)) {
				startActivity(i);
			} else {
				showDialog(DIALOG_CHARTDROID_DOWNLOAD);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
