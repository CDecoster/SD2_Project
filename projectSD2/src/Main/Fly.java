package Main;

public class Fly {
	
	private	String  airline;
	// ISO2 format
	private Airport source;
	// ISO2 format
	private Airport dest;
	
	
	public Fly(String airline, Airport source, Airport dest) {
		super();
		this.airline = airline;
		this.source = source;
		this.dest = dest;
	}


	public String getAirline() {
		return airline;
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
		return "Fly [airline=" + airline + ", source=" + source.getIso2() + ", dest=" + dest.getIso2() + "]";
	}


	public Airport getDest() {
		return dest;
	}


	public void setDest(Airport dest) {
		this.dest = dest;
	}

}
