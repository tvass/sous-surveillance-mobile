package org.kernel23.sous_surveillance_mobile;

/*
 * Class PoiItemizedOverlay
 * Contient les méthodes pour les markers sur la carte.
 * 
 */

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PoiItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

 public PoiItemizedOverlay(Drawable defaultMarker, Context context)
 {
	 super(boundCenterBottom(defaultMarker));
	 mContext = context;
 }

 public void addOverlay(OverlayItem overlay)
 {
	 mOverlays.add(overlay);
 	 populate();
 }
 
@Override
 protected OverlayItem createItem(int i)
 {
	 return mOverlays.get(i);
 }
 
@Override
 public int size()
 {
	 return mOverlays.size();
 }
 

/*
 * Régle le comportement lors d'un clic (tap) sur
 * un marker.
 * 
 */

@Override
 protected boolean onTap(int index)
 {
	 OverlayItem item = mOverlays.get(index);
	 final String ssid = item.getTitle();
	 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

	 builder.setTitle("INFORMATIONS");
	 builder.setMessage("Ouvrir la fiche "+ssid+" ?");

	 builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

	     @Override
		public void onClick(DialogInterface dialog, int which) {
	    	 Uri uri = Uri.parse("http://lyon.sous-surveillance.net/?camera"+ssid );
        	 mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
	         dialog.dismiss();
	     }

	 });

	 builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

	     @Override
	     public void onClick(DialogInterface dialog, int which) {
	         dialog.dismiss();
	     }
	 });

	 builder.show();
	 
	 return true;
 }
}