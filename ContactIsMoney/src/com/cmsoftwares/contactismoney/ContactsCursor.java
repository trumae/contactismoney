package com.cmsoftwares.contactismoney;

import android.database.Cursor;

public class ContactsCursor extends MyCursor {
	public ContactsCursor(Cursor c) {
	    super(c);
	    columns.add("balance");
	    columns.add("balanceNotFormated");
	}
	
	@Override
	public String getString(int arg0) {
		if (arg0 == 4) { //balance formatted
			long id = Long.parseLong(dados.elementAt(corrente).elementAt(0));
			return MainActivity.moedaFormatter.format(MainActivity.registroDBAdapter.getBalanceOfContact(id));
		}
		if (arg0 == 5) { //balance not formated
			long id = Long.parseLong(dados.elementAt(corrente).elementAt(0));
			return (new Double(MainActivity.registroDBAdapter.getBalanceOfContact(id))).toString();
		}
		return super.getString(arg0);
	}
}

