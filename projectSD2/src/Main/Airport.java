package Main;

public class Airport {
	
	private String iso2;
	private String name;
	private String city;
	private String country;
	private double latitude;
	private double longitude;
	private double cout;
	
	
	public Airport(String iso2, String name, String city, String country, double latitude, double longitude) {
		super();
		this.iso2 = iso2;
		this.name = name;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.cout = Double.MAX_VALUE;
		
	}
	
	public double getCout() {
		return this.cout;
	}

	public void setCout(double cout) {
		this.cout=cout;
	}

	public String getIso2() {
		return iso2;
	}


	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	@Override
	public String toString() {
		return "Airport [iso2=" + iso2 + ", name=" + name + ", city=" + city + ", country=" + country + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
