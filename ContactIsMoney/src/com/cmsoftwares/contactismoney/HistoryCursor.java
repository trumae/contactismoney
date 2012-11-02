package com.cmsoftwares.contactismoney;

import java.util.Date;

import android.database.Cursor;

public class HistoryCursor extends MyCursor {
	public HistoryCursor(Cursor c) {
		super(c);
	}

	@Override
	public String getString(int arg0) {
		if (arg0 == getColumnIndex(RegistroDBAdapter.KEY_VALUE)) {
			long id = Long.parseLong(dados.elementAt(corrente).elementAt(arg0));
			return MainActivity.moedaFormatter
					.format(id);
		}
		if (arg0 == getColumnIndex(RegistroDBAdapter.KEY_DATA)) {
			long id = Long.parseLong(dados.elementAt(corrente).elementAt(arg0));
			java.util.Date date = new Date(id);
			return MainActivity.dataFormatter.format(date);
		}
		return super.getString(arg0);
	}
}