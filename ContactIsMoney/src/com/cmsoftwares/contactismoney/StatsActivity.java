package com.cmsoftwares.contactismoney;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatsActivity extends Activity {
	private TextView mTextCountContact = null;
	private TextView mTextReceivable = null;
	private TextView mTextMeanContact = null;
	private TextView mTextGreaterBalance = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		mTextCountContact = (TextView) this
				.findViewById(R.id.textViewStatsCountContacts);
		mTextReceivable = (TextView) this
				.findViewById(R.id.textViewStatsReceivable);
		mTextMeanContact = (TextView) this
				.findViewById(R.id.textViewStatsMeanContacts);
		mTextGreaterBalance = (TextView) this
				.findViewById(R.id.textViewStatsGreaterBalance);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			int count = MainActivity.cursorContacts.getCount();
			mTextCountContact.setText((new Integer(count)).toString());

			long sumAllBalance = MainActivity.registroDBAdapter
					.getSumAllBalance();
			mTextReceivable.setText(MainActivity.moedaFormatter
					.format(sumAllBalance));

			long media = sumAllBalance / count;
			mTextMeanContact.setText(MainActivity.moedaFormatter.format(media));

			Cursor cursor = MainActivity.cursorContacts;

			int idxNome = 1;
			int idxBalance = 5;
			long maxBalance = 0;
			String maxName = "";
			if (cursor.moveToFirst())
				do {
					long bal = Long.parseLong(cursor.getString(idxBalance));
					if (bal > maxBalance) {
						maxBalance = bal;
						maxName = cursor.getString(idxNome);
					}
				} while (cursor.moveToNext());

			if (maxBalance == 0)
				mTextGreaterBalance.setText("-");
			else
				mTextGreaterBalance.setText(maxName + " - "
						+ MainActivity.moedaFormatter.format(maxBalance));
		} catch (Exception e) {
			Log.e("Erro", e.getMessage());
		}
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
