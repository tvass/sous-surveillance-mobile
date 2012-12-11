package org.kernel23.sous_surveillance_mobile;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapsActivity extends MapActivity {
	
	private MapView mapView;
	private MapController mc;
	private ProgressBar mProgressBar;
	
	public class MapOverlay extends com.google.android.maps.Overlay {

		public MapOverlay() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) 
	    {   
	        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
	        	UpdateMarkersOnMap(null);	
	        }                            
	        return false;
	    }    
	}
	
	List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapView = (MapView) findViewById(R.id.mapView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mc = mapView.getController();
        
        getCurrentLocation(); 
        
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
                
        mapView.postInvalidate();
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        
        getCurrentLocation();
        UpdateMarkersOnMap(null);
        
        mapView.invalidate();
        
        CheckLocalDB();
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
	
	public void getCurrentLocation(){   
		   
		}
	
	void UpdateMarkersOnMap(GeoPoint p) {
		if(p == null) {
			p = mapView.getMapCenter();
		}
		
    	double mylat =  p.getLatitudeE6() / 1E6;
    	String mylatString = Double.toString(mylat);
    	double mylong = p.getLongitudeE6() /1E6;
    	String mylongString = Double.toString(mylong);

		List<Camera> cameraList = new ArrayList<Camera>();
		CameraBDD cameraDB = new CameraBDD(this);
	    cameraDB.read();		
		cameraList=cameraDB.getCameraFromArea(mylatString, mylongString);
	    cameraDB.close();
	    
        Drawable drawable = this.getResources().getDrawable(R.drawable.cctv);
        PoiItemizedOverlay itemizedoverlay = new PoiItemizedOverlay(drawable,this);    

        for(Camera mycamera : cameraList )
        {
	    	String mylatmarker = mycamera.getLatitude();
		    String mylongmarker = mycamera.getLongitude(); 
		    Double myLat = Double.parseDouble(mylatmarker);
			Double myLong = Double.parseDouble(mylongmarker);
			GeoPoint geopoint = new GeoPoint((int)(myLat*1E6), (int)(myLong*1E6));
   			OverlayItem overlayitem = new OverlayItem(geopoint, mycamera.getSsid(), null);
   			itemizedoverlay.addOverlay(overlayitem);
        }
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
                
        if(itemizedoverlay.size() > 0) {
        	listOfOverlays.add(itemizedoverlay);
        }
        MapOverlay mapOverlay = new MapOverlay();
        listOfOverlays.add(mapOverlay);
                      
	    mapView.invalidate();	    
	   	}
	
	public void CheckLocalDB() {
		int total;
		CameraBDD cameraDB = new CameraBDD(this);
	    cameraDB.read();		
	    total=cameraDB.getTotalCamera();
	    cameraDB.close();
	    if(total == 0) {
	    	 AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

	    	 builder.setTitle("INFORMATIONS");
	    	 builder.setMessage("Aucune caméra en base. Voulez vous mettre à jour ?");

	    	 builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

	    	     public void onClick(DialogInterface dialog, int which) {
	 				 UpdateFromWeb myupdate = new UpdateFromWeb(getApplicationContext(),MapsActivity.this.mProgressBar);
					 myupdate.execute();
	    	    	 dialog.dismiss();
	    	     }

	    	 });

	    	 builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

	    	     @Override
	    	     public void onClick(DialogInterface dialog, int which) {
	    	         // I do not need any action here you might
	    	         dialog.dismiss();
	    	     }
	    	 });

	    	 //alert = builder.create();
	    	 builder.show();
	    	
	    	
	    }else{
	    	Toast.makeText(MapsActivity.this, "Vous avez "+total+" caméras référencées en base.",Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		  
		  		float lat;
		  		float lng;
		  		String ville;
		  		GeoPoint gp;
		  
			switch (item.getItemId()) {
			
			case R.id.update:
				 AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

		    	 builder.setTitle("INFORMATIONS");
		    	 builder.setMessage("Voulez vous mettre à jour votre base de données ?");

		    	 builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

		    	     public void onClick(DialogInterface dialog, int which) {
		    	    	 UpdateFromWeb myupdate = new UpdateFromWeb(getApplicationContext(),mProgressBar);
		 				 myupdate.execute();
		    	    	 dialog.dismiss();
		    	     }

		    	 });

		    	 builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

		    	     @Override
		    	     public void onClick(DialogInterface dialog, int which) {
		    	         // I do not need any action here you might
		    	         dialog.dismiss();
		    	     }
		    	 });

		    	 //alert = builder.create();
		    	 builder.show();
				return true;
				
	        case R.id.maposition:
	        	//mc.animateTo(myLocationOverlay.getMyLocation()); 
	        	mc.setZoom(16);
	            return true;

	        case R.id.angers:
	        	lat = 47.473612f;
	        	lng = -0.554167f;
	        	ville = "Angers";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            
	        
	        case R.id.blois:
	        	lat = 47.593889f;
	        	lng = 1.328056f;
	        	ville = "Blois";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	
	            
	        case R.id.bourges:
	        	lat = 47.084444f;
	        	lng = 2.396389f;
	        	ville = "Bourges";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            

	        case R.id.clermontferrand:
	        	lat = 45.783088f;
	        	lng = 3.082352f;
	        	ville = "Clermont-Ferrand";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            

	        case R.id.dijon:
	        	lat = 47.32167f;
	        	lng = 5.04139f;
	        	ville = "Dijon";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            

	        case R.id.luxembourg:
	        	lat = 49.61f;
	        	lng = 6.13333f;
	        	ville = "Luxembourg";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	
	            
	        case R.id.lyon:
	        	lat = 45.759723f;
	        	lng = 4.842223f;
	        	ville = "Lyon";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;

	        case R.id.marseille:
	        	lat = 43.302778f;
	        	lng = 5.381111f;
	        	ville = "Marseille";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            

	        case R.id.nimes:
	        	lat = 43.837778f;
	        	lng = 4.360833f;
	        	ville = "Nîmes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	
	            
	        case R.id.paris:
	        	lat = 48.856578f;
	        	lng = 2.351828f;
	        	ville = "Paris";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	            

	        case R.id.rennes:
	        	lat = 48.114722f;
	        	lng = -1.679444f;
	        	ville = "Rennes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	
	            
	        case R.id.toulouse:
	        	lat = 43.611111f;
	        	lng = 1.453611f;
	        	ville = "Toulouse";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	            

	        case R.id.tours:
	        	lat = 47.393611f;
	        	lng = 0.689167f;
	        	ville = "Tours";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	

	        case R.id.troyes:
	        	lat = 48.297419f;
	        	lng = 4.074263f;
	        	ville = "Troyes";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;	

	        case R.id.valdisere:
	        	lat = 45.450f;
	        	lng = 6.967f;
	        	ville = "Val d'Isère";
	        	gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	        	Toast.makeText(MapsActivity.this, "Direction "+ville,Toast.LENGTH_LONG).show();
	        	mc.animateTo(gp);
	        	UpdateMarkersOnMap(gp);
	            return true;		            
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	   
	
}
