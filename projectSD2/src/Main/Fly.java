package Main;

public class Fly {
	
	private String  airline;
	// ISO2 format
	private String source;
	// ISO2 format
	private String dest;
	
	
	public Fly(String airline, String source, String dest) {
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


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	@Override
	public String toString() {
		return "Fly [airline=" + airline + ", source=" + source + ", dest=" + dest + "]";
	}


	public String getDest() {
		return dest;
	}


	public void setDest(String dest) {
		this.dest = dest;
	}

}
