package austinhu_CSCI201L_Assignment1;

import java.util.ArrayList;
import java.util.Comparator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum{
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("address")
	@Expose
	private String address;
	@SerializedName("latitude")
	@Expose
	private Double latitude;
	@SerializedName("longitude")
	@Expose
	private Double longitude;
	@SerializedName("menu")
	@Expose
	private ArrayList<String> menu = null;
	public transient double distance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public ArrayList<String> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<String> menu) {
		this.menu = menu;
	}

}

class Sortbyname implements Comparator<Datum> 
{  
    public int compare(Datum a, Datum b) 
    { 
        return a.getName().compareTo(b.getName()); 
    } 
} 

class Sortbydistance implements Comparator<Datum> 
{  

    public int compare(Datum a, Datum b) 
    { 
    	if (a.distance < b.distance) return -1;
        if (a.distance > b.distance) return 1;
        return 0;
    } 
} 