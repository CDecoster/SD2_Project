package Main;
import Utils.Util;

public class Fly {
	
	private	String  airline;
	
	private Airport source;
	
	private Airport dest;
	
	private Double distance;
	
	
	public Fly(String airline, Airport source, Airport dest) {
		super();
		this.airline = airline;
		this.source = source;
		this.dest = dest;
		this.distance = Util.distance(source.getLatitude(),source.getLongitude(),dest.getLatitude(),dest.getLongitude());
	}


	public String getAirline() {
		return airline;
	}


	public Double getDistance() {
		return distance;
	}


	public void setAirline(String airline) {
		this.airline = airline;
	}


	public Airport getSource() {
		return source;
	}


	public void setSource(Airport source) {
		this.source = source;
	}


	@Override
	public String toString() {
		//return "Fly [airline=" + airline + ", source=" + source.getName() + ", dest=" + dest.getName() + ", distance =" + distance+ "]";
		return "Fly [source=" + source.getIso2() + ", dest=" + dest.getIso2() + ", distance =" + distance+", airline = "+airline+ "]";
	}


	public Airport getDest() {
		return dest;
	}


	public void setDest(Airport dest) {
		this.dest = dest;
	}

}
