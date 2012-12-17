package org.kernel23.sous_surveillance_mobile;

/*
 * Contient les fonctions de création ou mise à jour de la base de données.
 * 
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {
			 
			private static final String TABLE_CAMERA = "table_camera";
			private static final String COL_LAT = "latitude";
			private static final String COL_LONG = "longitude";
			private static final String COL_SSID = "ssid";

		 
			private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CAMERA + " ("
			+ COL_LAT + " TEXT NOT NULL, "
			+ COL_LONG + " TEXT NOT NULL, "
			+ COL_SSID + ","
			+ "PRIMARY KEY ("+COL_LAT+", "+COL_LONG+"));";
		 
			public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
				super(context, name, factory, version);
			}
		 
			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL(CREATE_BDD);
			}
		 
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				db.execSQL("DROP TABLE " + TABLE_CAMERA + ";");
				onCreate(db);
			}
		 
		}