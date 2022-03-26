package Main;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.io.FileReader;
import java.io.IOException;



public class Graph {
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
		// marked airport
		Set<Airport> markAirport = new HashSet<>();
		markAirport.add(airportSrc);
						
		//'Arc sortants'
		HashMap<Airport,HashSet<Fly>> outGoingFlies = new HashMap<>();
		for (String iso : airportMap.keySet()) {
			outGoingFlies.put(airportMap.get(iso),new HashSet<>());
		}
		for (Fly fly : flySet) {
			outGoingFlies.get(fly.getSource()).add(fly);
			
		}
		
		// Save the way		
		HashMap<Airport,Fly>  path = new HashMap<>();
		
		
		while(!queue.isEmpty()&& !queue.getFirst().equals(airportDest)) {
			Airport currentNode = queue.poll();				
			for (Fly fly : outGoingFlies.get(currentNode)) {
				Airport arrivee = fly.getDest();
					if(!markAirport.contains(arrivee)) {
						path.put(arrivee, fly);
						markAirport.add(arrivee);						
						queue.add(arrivee);
					}										
							
			}
			
		}
		if (queue.isEmpty()) {
			System.out.println("Chemin impossible.");
			System.exit(1);
		}
		
				
		LinkedList<Fly> itinerary = new LinkedList<Fly>();
		Double totalDist = 0.0;		
		Airport destination = airportDest;					
		while(destination !=null) {
			if (destination == airportSrc ) {
				break;
			}
			
			itinerary.addFirst(path.get(destination));					
			destination = itinerary.get(0).getSource();
		}
				
		for (Fly fly : itinerary) {
			System.out.println(fly);
			totalDist += fly.getDistance();
		}
		System.out.println("Distance totale : "+totalDist);
		
		
	
		
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




