package org.kernel23.sous_surveillance_mobile;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CameraBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "sous-surveillance.db";
	private static final String TABLE_CAMERA = "table_camera";
	
	private static final String COL_LAT = "latitude";
	private static final int NUM_COL_LAT = 0;
	
	private static final String COL_LONG = "longitude";
	private static final int NUM_COL_LONG = 1;
	
	private static final String COL_SSID = "ssid";
	private static final int NUM_COL_SSID = 2;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public CameraBDD(Context context){
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open(){
		bdd = maBaseSQLite.getWritableDatabase();
	}
	
	public void read(){
		bdd = maBaseSQLite.getReadableDatabase();
	}

	public void close(){
		bdd.close();
	}

	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public long insertCamera(Camera camera){
		ContentValues values = new ContentValues();
		values.put(COL_LAT, camera.getLatitude());
		values.put(COL_LONG, camera.getLongitude());
		values.put(COL_SSID, camera.getSsid());
		return bdd.insert(TABLE_CAMERA, null, values);
	}

	public int getTotalCamera() {
		int total = 0;
		String selectQuery = "SELECT COUNT (*) FROM TABLE_CAMERA";
	    Cursor cursor = bdd.rawQuery(selectQuery, null);
	    cursor.moveToFirst();
	    total = cursor.getInt(0);
	    return total;
		
	}
	
	
	public  List<Camera> getCameraFromArea(String current_latitude, String current_longitude){
		List<Camera> cameraList = new ArrayList<Camera>();
	    
		String selectQuery = "SELECT * FROM TABLE_CAMERA ORDER BY abs(latitude - ("+current_latitude+")) + abs( longitude - ("+current_longitude+")) LIMIT 150";
	    Cursor cursor = bdd.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	
	            Camera camera = new Camera();
	            camera.setLatitude(cursor.getString(NUM_COL_LAT));
	            camera.setLongitude(cursor.getString(NUM_COL_LONG));
	            camera.setSsid(cursor.getString(NUM_COL_SSID));
	            cameraList.add(camera);
	            
	        } while (cursor.moveToNext());
	    }
	    return cameraList;	
	}
	
	public  List<Camera> getAllCamera(){
		List<Camera> cameraList = new ArrayList<Camera>();
	    
		String selectQuery = "SELECT * FROM TABLE_CAMERA";
	    Cursor cursor = bdd.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	
	            Camera camera = new Camera();
	            camera.setLatitude(cursor.getString(NUM_COL_LAT));
	            camera.setLongitude(cursor.getString(NUM_COL_LONG));
	            camera.setSsid(cursor.getString(NUM_COL_SSID));
	            cameraList.add(camera);
	            
	        } while (cursor.moveToNext());
	    }
	    return cameraList;	
	}	
	
	
}