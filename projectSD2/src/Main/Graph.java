package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
	//private HashSet<Airport> airportSet = new HashSet<>();
	private HashMap<String,Airport> airportMap = new HashMap<>();
	private HashSet<Fly> flySet = new HashSet<>();



	public Graph(File airportTxt,File flyTxt) throws IOException {

		// Read File Aiport 
		FileReader fr = new FileReader(airportTxt);
		BufferedReader br = new BufferedReader(fr);  
		StringBuffer sb = new StringBuffer();    
		String line;
		while((line = br.readLine()) != null){	    	
			// ajoute la ligne au buffer
			sb.append(line);
			Airport airport = formattedAirportFromLine(line);
			airportMap.put(airport.getIso2(), airport);


			sb.append("\n");     
		}
		fr.close();    


		// Read file Fly
		fr = new FileReader(flyTxt);
		br = new BufferedReader(fr);  
		sb = new StringBuffer();    		     
		while((line = br.readLine()) != null){	    			        
			sb.append(line);
			Fly fly =  formattedFlyFromLine(line);
			flySet.add(fly);
			sb.append("\n");     
		}
		fr.close();     
	}



	public Airport formattedAirportFromLine(String line) {
		String[] tab = line.split(",");
		Airport airport = new Airport(tab[0],tab[1],tab[2],tab[3],Double.parseDouble(tab[4]),Double.parseDouble(tab[5]));
		//System.out.println(airport.toString());
		return airport;
	}

	public Fly  formattedFlyFromLine(String line) {
		String[] tab = line.split(",");
		Fly fly = new Fly(tab[0],tab[1],tab[2]);
		//System.out.println(fly.toString());
		return fly;
	}

	public void calculerItineraireMinimisantNombreVol(String source,String dest) {

	}

	public void calculerItineraireMiniminantDistance(String source,String dest) {

	}

}


