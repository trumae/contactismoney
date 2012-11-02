package com.cmsoftwares.contactismoney;

import java.util.Vector;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

public class MyCursor implements Cursor {
	protected Vector<String> columns;
	protected Vector<Vector<String>> dados;
	protected int corrente = 0;

	public MyCursor(Cursor c) {
		corrente = 0;
		// copia colunas
		columns = new Vector<String>();
		String[] cols = c.getColumnNames();
		for (int i = 0; i < cols.length; i++) {
			columns.add(cols[i]);
		}

		// copia valores
		dados = new Vector<Vector<String>>();
		if (c.moveToFirst())
			do {
				Vector<String> linha = new Vector<String>();
				for (int i = 0; i < cols.length; i++) {
					linha.add(c.getString(i));
				}
				dados.add(linha);
			} while (c.moveToNext());
	}

	public void close() {
	}

	public void copyStringToBuffer(int arg0, CharArrayBuffer arg1) {
		// TODO
	}

	public void deactivate() {
	}

	public byte[] getBlob(int arg0) {
		return null;
	}

	public int getColumnCount() {
		return columns.size();
	}

	public int getColumnIndex(String arg0) {
		if (columns.contains(arg0)) {
			return columns.indexOf(arg0);
		}
		return -1;
	}

	public int getColumnIndexOrThrow(String arg0)
			throws IllegalArgumentException {
		int index = 0;
		if ((index = getColumnIndex(arg0)) == -1)
			throw new IllegalArgumentException();
		return index;
	}

	public String getColumnName(int arg0) {
		return columns.elementAt(arg0);
	}

	public String[] getColumnNames() {
		String[] ss = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			ss[i] = columns.elementAt(i);
		}
		return ss;
	}

	public int getCount() {
		return dados.size();
	}

	public double getDouble(int arg0) {
		return 0;
	}

	public Bundle getExtras() {
		return null;
	}

	public float getFloat(int arg0) {
		return 0;
	}

	public int getInt(int arg0) {
		return 0;
	}

	public long getLong(int arg0) {
		return 0;
	}

	public int getPosition() {
		return corrente;
	}

	public short getShort(int arg0) {
		return 0;
	}

	public String getString(int arg0) {		
		return dados.elementAt(corrente).elementAt(arg0);
	}

	public boolean getWantsAllOnMoveCalls() {
		return false;
	}

	public boolean isAfterLast() {
		return corrente >= dados.size();
	}

	public boolean isBeforeFirst() {
		return corrente < dados.size();
	}

	public boolean isClosed() {
		return false;
	}

	public boolean isFirst() {
		return corrente == 0;
	}

	public boolean isLast() {
		return corrente == (dados.size() - 1);
	}

	public boolean isNull(int arg0) {

		return false;
	}

	public boolean move(int arg0) {
		int c = corrente + arg0;
		if (c >= dados.size())
			return false;
		if (c < 0)
			return false;

		corrente = c;
		return true;
	}

	public boolean moveToFirst() {
		corrente = 0;
		return true;
	}

	public boolean moveToLast() {
		corrente = dados.size() - 1;
		return true;
	}

	public boolean moveToNext() {
		return move(1);
	}

	public boolean moveToPosition(int arg0) {
		corrente = arg0;
		return true;
	}

	public boolean moveToPrevious() {
		return move(-1);
	}

	public void registerContentObserver(ContentObserver arg0) {

	}

	public void registerDataSetObserver(DataSetObserver arg0) {

	}

	public boolean requery() {
		return false;
	}

	public Bundle respond(Bundle arg0) {
		return null;
	}

	public void setNotificationUri(ContentResolver arg0, Uri arg1) {
	}

	public void unregisterContentObserver(ContentObserver arg0) {

	}

	public void unregisterDataSetObserver(DataSetObserver arg0) {

	}

}
