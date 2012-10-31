package com.cmsoftwares.contactismoney;

import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class RegistroDBAdapter {
	private static final String DATABASE_NAME = "contactismoney.db";
	private static final String DATABASE_TABLE = "registro";
	private static final int DATABASE_VERSION = 1;

	// The index (key) column name for use in where clauses.
	public static final String KEY_ID = "_id";
	// The name and column index of each column in your database.
	public static final String KEY_IDCONTACT = "idcontact";
	public static final int IDCONTACT_COLUMN = 1;

	public static final String KEY_TIPO = "tipo";
	public static final int TIPO_COLUMN = 2;
	public static final String KEY_DATA = "data";
	public static final int DATA_COLUMN = 3;
	public static final String KEY_CRIADOEM = "criadoem";
	public static final int CRIADOEM_COLUMN = 4;
	public static final String KEY_DESCRICAO = "descricao";
	public static final int DESCRICAO_COLUMN = 5;
	public static final String KEY_VALOR = "valor";
	public static final int VALOR_COLUMN = 6;
	
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + 
			KEY_IDCONTACT + " text not null," +
			KEY_TIPO + " text not null, " +
			KEY_DATA + " long, " +
			KEY_CRIADOEM + " long, " +
			KEY_DESCRICAO + " text, " +
			KEY_VALOR + " long " +
			");";

	// Variable to hold the database instance
	private SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;

	// Database open/upgrade helper
	private RegistroDbHelper dbHelper;

	public RegistroDBAdapter(Context _context) {
		context = _context;
		dbHelper = new RegistroDbHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public RegistroDBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	/*
	 * public int insertEntry(MyObject _myObject) { // TODO: Create a new
	 * ContentValues to represent my row // and insert it into the database.
	 * return index; } public boolean removeEntry(long _rowIndex) { return
	 * db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0; } public
	 * Cursor getAllEntries () { return db.query(DATABASE_TABLE, new String[]
	 * {KEY_ID, KEY_NAME}, null, null, null, null, null); } public MyObject
	 * getEntry(long _rowIndex) { // TODO: Return a cursor to a row from the
	 * database and // use the values to populate an instance of MyObject return
	 * objectInstance; } public boolean updateEntry(long _rowIndex, MyObject
	 * _myObject) { // TODO: Create a new ContentValues based on the new object
	 * // and use it to update a row in the database. return true; }
	 */

	private static class RegistroDbHelper extends SQLiteOpenHelper {
		public RegistroDbHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// Called when no database exists in disk and the helper class needs
		// to create a new one.
		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);
		}

		// Called when there is a database version mismatch meaning that the
		// version
		// of the database on disk needs to be upgraded to the current version.
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			// Log the version upgrade.
			Log.w("RegistroDBAdapter", "Upgrading from version " + _oldVersion
					+ " to " + _newVersion
					+ ", which will destroy all old data");

			// Upgrade the existing database to conform to the new version.
			// Multiple
			// previous versions can be handled by comparing _oldVersion and
			// _newVersion
			// values.
			// The simplest case is to drop the old table and create a new one.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(_db);
		}
	}
}
