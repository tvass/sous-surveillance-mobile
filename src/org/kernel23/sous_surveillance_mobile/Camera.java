package org.kernel23.sous_surveillance_mobile;

public class Camera {
	private String	ssid;
	private String 	latitude;
	private String 	longitude; 

	@Override
	public String toString ( ){
		return this.ssid+" "+this.latitude+" "+this.longitude;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String string) {
		this.ssid = string;
	}	

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}

