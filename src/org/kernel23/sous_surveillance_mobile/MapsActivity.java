package org.kernel23.sous_surveillance_mobile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.weakwire.mapviewcluster.CMapView;

public class MapsActivity extends MapActivity {
	
	private MapController mc;
	private MyLocationOverlay myLocationOverlay ;
	
	List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CMapView mapView = new CMapView(this, "---API KEY HERE ---");
        setContentView(mapView);
        
        
        mc = mapView.getController();
        
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        mapView.postInvalidate();
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
        	mc.animateTo(myLocationOverlay.getMyLocation()); 	
        	mc.setZoom(16);
            }
        });
    
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(false);
        
        InputStream inputStream = getResources().openRawResource(R.raw.sous_surveillance_gps_database);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        String myStr = "";
        int in;
        
        try {
        	in = inputStream.read();
        	while (in != -1)
        	{
        		byteArrayOutputStream.write(in);
        		in = inputStream.read();
        	}
        	inputStream.close();
        	myStr = byteArrayOutputStream.toString();
        }catch (IOException e) {
        	   // TODO Auto-generated catch block
        	   e.printStackTrace();
        	  }
        

        JSONObject fullJObj;
        JSONObject  geoJson;
        JSONArray coordJson; 
        
		try {
			fullJObj = new JSONObject(myStr);
	        JSONArray jArr = fullJObj.getJSONArray("features");
	        for (int i = 0; i < jArr.length(); ++i ) {
	            JSONObject jObj = jArr.getJSONObject(i);
	            
	            String geometry = jObj.getString("geometry");	    	    
	    	    geoJson = new JSONObject(geometry);
	    	    coordJson = geoJson.getJSONArray("coordinates");
	    	    
	    	    String mylong = coordJson.getString(1);
	    	    String mylat  = coordJson.getString(0);
	    	    
	    	    
	    		Double myLat = Double.parseDouble(mylong);
   				Double myLong = Double.parseDouble(mylat);
   				GeoPoint geopoint = new GeoPoint((int)(myLat*1E6), (int)(myLong*1E6));
   				
   				geoPoints.add(geopoint);
   				
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mapView.setPoints(geoPoints).setMaxPoints(40);
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_maps, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		  
		  		float lat;
		  		float lng;
		  		String ville;
		  		GeoPoint gp;
		  
			switch (item.getItemId()) {
	        case R.id.maposition:
	        	mc.animateTo(myLocationOverlay.getMyLocation()); 
	        	mc.setZoom(16);
	            return true;

	        case R.id.angers:
	        	lat = 47.473612f;
	        	lng = -0.554167f;
	        	ville = "Angers";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            
	        
	        case R.id.blois:
	        	lat = 47.593889f;
	        	lng = 1.328056f;
	        	ville = "Blois";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	
	            
	        case R.id.bourges:
	        	lat = 47.084444f;
	        	lng = 2.396389f;
	        	ville = "Bourges";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            

	        case R.id.clermontferrand:
	        	lat = 45.783088f;
	        	lng = 3.082352f;
	        	ville = "Clermont-Ferrand";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            

	        case R.id.dijon:
	        	lat = 47.32167f;
	        	lng = 5.04139f;
	        	ville = "Dijon";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            

	        case R.id.luxembourg:
	        	lat = 49.61f;
	        	lng = 6.13333f;
	        	ville = "Luxembourg";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	
	            
	        case R.id.lyon:
	        	lat = 45.759723f;
	        	lng = 4.842223f;
	        	ville = "Lyon";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;

	        case R.id.marseille:
	        	lat = 43.302778f;
	        	lng = 5.381111f;
	        	ville = "Marseille";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            

	        case R.id.nimes:
	        	lat = 43.837778f;
	        	lng = 4.360833f;
	        	ville = "N”mes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	
	            
	        case R.id.paris:
	        	lat = 48.856578f;
	        	lng = 2.351828f;
	        	ville = "Paris";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	            

	        case R.id.rennes:
	        	lat = 48.114722f;
	        	lng = -1.679444f;
	        	ville = "Rennes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	
	            
	        case R.id.toulouse:
	        	lat = 43.611111f;
	        	lng = 1.453611f;
	        	ville = "Toulouse";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	            

	        case R.id.tours:
	        	lat = 47.393611f;
	        	lng = 0.689167f;
	        	ville = "Tours";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	

	        case R.id.troyes:
	        	lat = 48.297419f;
	        	lng = 4.074263f;
	        	ville = "Troyes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;	

	        case R.id.valdisere:
	        	lat = 45.450f;
	        	lng = 6.967f;
	        	ville = "Val d'Isre";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	            return true;		            
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	   
	
}
