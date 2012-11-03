package com.cmsoftwares.contactismoney;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
	private Dialog dialogDeleteMessage;
	private Cursor cursor;
	private int registerSelected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registers);
		context = this;
		Cursor cursorContacts = MainActivity.cursorContacts;
		cursorContacts.moveToPosition((int) MainActivity.selected);
		idContact = (Long.parseLong(cursorContacts.getString(0)));

		mRegistersList = (ListView) this.findViewById(R.id.registersList);
		mRegistersList.setOnItemClickListener(mMessageClickedHandler);
		dialogDeleteMessage = createDialogDeleteMessage();

		// preenche cabecalho
		tname = (TextView) this.findViewById(R.id.textViewRegistersName);
		tbalance = (TextView) this.findViewById(R.id.textViewRegistersBalance);

		tname.setText(cursorContacts.getString(1));
		updateActivity();

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
	}

	private void preencheBalance() {
		long balance = MainActivity.registroDBAdapter
				.getBalanceOfContact(idContact);
		tbalance.setText(MainActivity.moedaFormatter.format(balance));
	}

	private void populateHistoryList() {
		// Build adapter with contact entries
		Cursor orig = MainActivity.registroDBAdapter
				.getHistoryOfContact(idContact);

		cursor = new HistoryCursor(orig);
		String[] fields = new String[] { RegistroDBAdapter.KEY_DATA,
				RegistroDBAdapter.KEY_VALUE, RegistroDBAdapter.KEY_DESCRIPTION };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.register_entry, cursor, fields, new int[] {
						R.id.textViewDateRegister, R.id.textViewValueRegister,
						R.id.textViewDescriptionRegister });

		mRegistersList.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateActivity();
	}

	private void updateActivity() {
		preencheBalance();
		populateHistoryList();
	}

	private Dialog createDialogDeleteMessage() {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.delete_register_message)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								cursor.moveToPosition((int) registerSelected);
								long idReg = (Long.parseLong(cursor.getString(0)));
								MainActivity.registroDBAdapter.removeRecord(idReg);
								updateActivity();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long id) {
			registerSelected = pos;
			dialogDeleteMessage.show();
		}
	};
}
