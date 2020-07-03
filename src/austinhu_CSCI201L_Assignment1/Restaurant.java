package austinhu_CSCI201L_Assignment1;

import java.util.ArrayList;


public class Restaurant {
	ArrayList<Datum> data = new ArrayList<>();
	
	public double findDist(double lat1, double lat2, double long1, double long2){
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		long1 = Math.toRadians(long1);
		long2 = Math.toRadians(long2);
		double d = 3963.0 * Math.acos((Math.sin(lat1) * Math.sin(lat2)) 
				+ Math.cos(lat1) * Math.cos(lat2)
		        * Math.cos(long2 - long1));
		d = Math.round(d * 10)/10.0;
		return d;
	}
}
