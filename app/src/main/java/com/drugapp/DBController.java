package com.drugapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {

	public DBController(Context applicationcontext) {
        super(applicationcontext,"user.db", null, 1);
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		    query = "CREATE TABLE userdetails (id INTEGER PRIMARY KEY ASC,name TEXT,contactnum TEXT,Location TEXT,email TEXT,designation TEXT,password TEXT)";
        database.execSQL(query);

		String query_new;
		query_new = "CREATE TABLE pharmacy (name TEXT,location TEXT,latitude TEXT,longitude TEXT)";
		database.execSQL(query_new);

        
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS userdetails";
		database.execSQL(query);

		String query_new;
		query_new = "DROP TABLE IF EXISTS pharmacy";
		database.execSQL(query_new);
        onCreate(database);
	}
	
	
	/**
	 * Inserts User into SQLite DB
	 * @param queryValues
	 */
	//HashMap<String, String>
	public long insertUser( ContentValues queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
	//	ContentValues values = new ContentValues();

	/*	values.put("name", queryValues.get("name"));
		values.put("contactnum", queryValues.get("contactnum"));
		values.put("Location", queryValues.get("Location"));
		values.put("email", queryValues.get("email"));
		values.put("idproof", queryValues.get("idproof"));
		values.put("password", queryValues.get("password"));*/
		long r=database.insert("userdetails", null, queryValues);
		database.close();
		return r;
	}



	public String getPassword(HashMap<String, String> queryValues) {
		String pass;
		String userid=queryValues.get("email");
		String selectQuery = "SELECT * FROM userdetails where email='"+userid+"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

			 pass=cursor.getString(6);
				return pass;
			} while (cursor.moveToNext());

		}else{
			database.close();
			return null;

		}


	}


	public String getDesignation(HashMap<String, String> queryValues) {
		String pass;
		String userid=queryValues.get("email");
		String selectQuery = "SELECT * FROM userdetails where email='"+userid+"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				pass=cursor.getString(5);
				return pass;
			} while (cursor.moveToNext());

		}else{
			database.close();
			return null;

		}


	}

	public void insertPharmacy(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", queryValues.get("name"));
		values.put("location", queryValues.get("location"));
		values.put("latitude", queryValues.get("latitude"));
		values.put("longitude", queryValues.get("longitude"));
		database.insert("pharmacy", null, values);
		database.close();
	}

	public ArrayList<Pharmacy> getPharmacies() {
		ArrayList<Pharmacy> pharList;
		pharList = new ArrayList<Pharmacy>();
		String selectQuery = "SELECT * FROM pharmacy";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Pharmacy phar = new Pharmacy();
				phar.setName(cursor.getString(0));
				phar.setLocation(cursor.getString(1));
				phar.setLat(cursor.getString(2));
				phar.setLongitude(cursor.getString(3));

				pharList.add(phar);
			} while (cursor.moveToNext());
		}
		database.close();
		return pharList;
	}
	
}
