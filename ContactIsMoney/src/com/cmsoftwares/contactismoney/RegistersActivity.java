package com.cmsoftwares.contactismoney;

import java.util.Date;

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
	private ListView mRegistersList;
    private Button btnBuy;
    private Button btnPay;
    Context context;    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registers);
		context = this;
				
		mRegistersList = (ListView) this.findViewById(R.id.registersList);
		
		//preenche cabecalho
		TextView tname = (TextView) this.findViewById(R.id.textViewRegistersName);
		TextView tbalance = (TextView) this.findViewById(R.id.textViewRegistersBalance);
		Cursor cursorContacts = MainActivity.cursorContacts;
		cursorContacts.moveToPosition((int) MainActivity.selected);
		tname.setText(cursorContacts.getString(1));
		long balance = MainActivity.registroDBAdapter.getBalanceOfContact((Long.parseLong(cursorContacts.getString(0))));
		tbalance.setText((new Long(balance)).toString());
		
		//prepara botoes
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_registers, menu);
		return true;
	}
	
	private void populateContactList() {
		// Build adapter with contact entries
		Cursor cursor = null;

		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.register_entry, cursor,
				fields, new int[] { R.id.contactEntryText });

		mRegistersList.setAdapter(adapter);
	}

}
