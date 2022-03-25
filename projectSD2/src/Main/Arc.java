package Main;

public class Arc {
	
	private String  airline;
	// ISO2 format
	private String source;
	// ISO2 format
	private String dest;
	
	
	public Arc(String airline, String source, String dest) {
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


	public String getDest() {
		return dest;
	}


	public void setDest(String dest) {
		this.dest = dest;
	}

}
