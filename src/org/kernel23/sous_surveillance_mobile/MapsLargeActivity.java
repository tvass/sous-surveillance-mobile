package org.kernel23.sous_surveillance_mobile;
/*
 * Ce fichier utilise une présentation de la carte avec
 * une clusterisation des markers (remplacement de XX 
 * markers par un point plus gros et le total écrit
 * à l'interieur).
 * 
 * Je n'ai pour l'instant retenu cette solution car
 * finalement pas super performant non plus...
 * 
 * Le code ci-dessous est fonctionnel. Pour tester
 * cette ui, utiliser startActivity() ...
 * 
 * https://github.com/weakwire/AndroidMapCluster
 * 
 */
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;

import com.weakwire.mapviewcluster.CMapView;

public class MapsLargeActivity extends MapActivity {	
	
private MapController mc;
private MyLocationOverlay myLocationOverlay ;

List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CMapView mapView = new CMapView(this, "----- API KEY MAPS -----");
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
         mc.setZoom(8);
            }
        });
    
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
    	
    	List<Camera> cameraList = new ArrayList<Camera>();
    	CameraBDD cameraDB = new CameraBDD(MapsLargeActivity.this);
        cameraDB.open();		
    	cameraList=cameraDB.getAllCamera();
        cameraDB.close();
          

        for(Camera mycamera : cameraList )
        {
        	String mylatmarker = mycamera.getLatitude();
    	    String mylongmarker = mycamera.getLongitude(); 
    	    Double myLat = Double.parseDouble(mylatmarker);
    		Double myLong = Double.parseDouble(mylongmarker);
    		GeoPoint geopoint = new GeoPoint((int)(myLat*1E6), (int)(myLong*1E6));
    		
    		geoPoints.add(geopoint);

        }
        mapView.setPoints(geoPoints).setMaxPoints(70);	        	
        mapView.invalidate();
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
}