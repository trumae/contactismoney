package com.cmsoftwares.contactismoney;

import android.database.Cursor;

public class ContactsCursor extends MyCursor {
	public ContactsCursor(Cursor c) {
	    super(c);
	    columns.add("balance");
	}
	
	@Override
	public String getString(int arg0) {
		if (arg0 == 4) {
			long id = Long.parseLong(dados.elementAt(corrente).elementAt(0));
			return MainActivity.moedaFormatter.format(MainActivity.registroDBAdapter.getBalanceOfContact(id));
		}
		return super.getString(arg0);
	}
}

