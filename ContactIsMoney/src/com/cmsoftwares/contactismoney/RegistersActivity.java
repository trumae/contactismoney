package com.cmsoftwares.contactismoney;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.cmsoftwares.contactismoney.RegistroDBAdapter;

public class RegistersActivity extends Activity {
	public static final int BUY = 1;
	public static final int PAY = 2;
	public static int oper = 0;
	public static long idContact;
	private ListView mRegistersList;
	private Button btnBuy;
	private Button btnPay;
	private TextView tname;
	private TextView tbalance;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registers);
		context = this;
		Cursor cursorContacts = MainActivity.cursorContacts;
		cursorContacts.moveToPosition((int) MainActivity.selected);
		idContact = (Long.parseLong(cursorContacts.getString(0)));

		mRegistersList = (ListView) this.findViewById(R.id.registersList);

		// preenche cabecalho
		tname = (TextView) this
				.findViewById(R.id.textViewRegistersName);
		tbalance = (TextView) this
				.findViewById(R.id.textViewRegistersBalance);

		tname.setText(cursorContacts.getString(1));		
		preencheBalance();

		// prepara botoes
		btnBuy = (Button) this.findViewById(R.id.btnBuy);
		btnPay = (Button) this.findViewById(R.id.btnPay);

		btnBuy.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				oper = BUY;
				Intent i = new Intent(context, AddRegisterActivity.class);
				startActivity(i);
			}
		});
		btnPay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				oper = PAY;
				Intent i = new Intent(context, AddRegisterActivity.class);
				startActivity(i);
			}
		});
		populateHistoryList();
	}

	private void preencheBalance() {
		long balance = MainActivity.registroDBAdapter
				.getBalanceOfContact(idContact);
		tbalance.setText(MainActivity.moedaFormatter.format(balance));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_registers, menu);
		return true;
	}

	private void populateHistoryList() {
		// Build adapter with contact entries
		Cursor cursor = MainActivity.registroDBAdapter
				.getHistoryOfContact(idContact);

		String[] fields = new String[] { RegistroDBAdapter.KEY_DATA,
				RegistroDBAdapter.KEY_VALUE,
				RegistroDBAdapter.KEY_DESCRIPTION };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.register_entry, cursor, fields,
				new int[] { R.id.textViewDateRegister, R.id.textViewValueRegister, R.id.textViewDescriptionRegister });

		mRegistersList.setAdapter(adapter);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        preencheBalance(); 
        populateHistoryList();    
    }

}
