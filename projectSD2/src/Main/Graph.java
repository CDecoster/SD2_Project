package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
	//private HashSet<Airport> airportSet = new HashSet<>();
	private HashMap<String,Airport> airportMap = new HashMap<>();
	private HashSet<Fly> flySet = new HashSet<>();
	


	public Graph(File...files) throws IOException {		
		for (File fileArg : files) {
			FileReader fr = new FileReader(fileArg);
			BufferedReader br = new BufferedReader(fr);  
			StringBuffer sb = new StringBuffer();    
			String line;

			while((line = br.readLine()) != null){	    												
				sb.append(line);
				if(fileArg.getName().equals("aeroports.txt")) {					
					
					Airport airport = InstanciateAirportFromLine(line);
					airportMap.put(airport.getIso2(), airport);	

				}else if(fileArg.getName().equals("vols.txt")) {
					
					Fly fly =  InstanciateFlyFromLine(line);
					flySet.add(fly);

				}
				sb.append("\n");     
			}
			fr.close();    
		}
	}







	public void calculerItineraireMinimisantNombreVol(String source,String dest) {
		//Source
		Airport airportSrc = airportMap.get(source);		
		// Destination
		Airport airportDest = airportMap.get(dest);
		//BFS file + add source in it
		Deque<Airport> queue = new ArrayDeque<>();
		queue.add(airportSrc);
		//When we find the dest , stop.
		Boolean stop = false;		
		HashMap<Airport,HashSet<Fly>> outGoingFlies = new HashMap<>();
		Deque<Airport> queueAirport = new ArrayDeque<>();
		
		
		
		while(!queue.isEmpty() && !stop) {
			Airport currentNode = queue.poll();
			queueAirport.add(currentNode);
			for (Fly fly : flySet) {				
				if(fly.getSource().equals(currentNode) && !queue.contains(currentNode)) {
					
					if(!outGoingFlies.containsKey(fly.getSource())) outGoingFlies.put(currentNode, new HashSet<>());
					System.out.println(outGoingFlies.get(currentNode));
					outGoingFlies.get(currentNode).add(fly);
					
										
					queue.add(fly.getDest());
					if(fly.getDest().equals(airportDest)) {						
						stop = true;
						break;
					}
					
				}
				
			}
			
		}
		Double totalDist;
		ArrayList<Fly> itinerary = new ArrayList<Fly>();
		while(!queueAirport.isEmpty()) {
		Airport origine = queueAirport.poll();
		for (Fly arc : outGoingFlies.get(origine)) {
			if(arc.getDest().equals(airportDest)) {
				itinerary.add(arc);
			}
		}
	
		
		}
		System.out.println(itinerary);
			
	}
	
	

	public void calculerItineraireMiniminantDistance(String source,String dest) {

	}
	
	public Airport InstanciateAirportFromLine(String line) {
		String[] tab = line.split(",");
		Airport airport = new Airport(tab[0],tab[1],tab[2],tab[3],Double.parseDouble(tab[4]),Double.parseDouble(tab[5]));
		//System.out.println(airport.toString());
		return airport;
	}

	public Fly  InstanciateFlyFromLine(String line) {
		String[] tab = line.split(",");
		Airport src = airportMap.get(tab[1]);
		Airport dest = airportMap.get(tab[2]);
		Fly fly = new Fly(tab[0],src,dest);
		//System.out.println(fly.toString());
		return fly;
	}

}




