/**
 * @author Decoster Corentin, Declerck Axel
 *
 * 
 */

package Main;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;



import java.util.*;


import java.io.FileReader;
import java.io.IOException;



public class Graph {

	private HashMap<String,Airport> airportMap = new HashMap<>();
	private HashSet<Fly> flySet = new HashSet<>();

	//'Arc sortants'
	HashMap<Airport,HashSet<Fly>> outGoingFlies = new HashMap<>();



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

		for (String iso : airportMap.keySet()) {
			outGoingFlies.put(airportMap.get(iso),new HashSet<>());
		}
		for (Fly fly : flySet) {
			outGoingFlies.get(fly.getSource()).add(fly);

		}
	}







	public void calculerItineraireMinimisantNombreVol(String source,String dest) {
		//Source.
		Airport airportSrc = airportMap.get(source);		
		// Destination.
		Airport airportDest = airportMap.get(dest);
		//BFS file + add source in it.
		Deque<Airport> queue = new ArrayDeque<>();
		queue.add(airportSrc);
		// marked airport.
		Set<Airport> markAirport = new HashSet<>();
		markAirport.add(airportSrc);

		// Path taken.
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
			System.out.println("No way found.");
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
		// Path taken.
		HashMap<Airport,Fly> path = new HashMap<Airport, Fly>();

		//comparator Used for Temporary Set of summit. Allow us to find easily the lowest distance in the temporary set of summit
		Comparator<Airport> comparateur  = new Comparator<Airport> () {
			public int compare(Airport o1, Airport o2) {
				int delta = Double.compare(o1.getCout(), o2.getCout());
				if (delta==0) return o1.getIso2().compareTo(o2.getIso2());
				return delta;
			}
		};

		//Instantiate the temporary set of summit.
		TreeSet<Airport> tempSummits = new TreeSet<Airport>(comparateur);

		//Definitive Set of summit
		HashSet<Airport> defSummits = new HashSet<Airport>();


		Airport airportSource=airportMap.get(source);
		airportSource.setCout(0);

		//Add the airport source to the Temporary Set.
		tempSummits.add(airportSource);

		//Destination
		Airport destination = airportMap.get(dest);;

		Airport current = airportSource;

		while(!current.equals(destination)) { // While we are not at the destination.

			//find all fly who have for source the current airport
			for(Fly fly : outGoingFlies.get(current)) { 							
				Airport step = fly.getDest();
				if(!defSummits.contains(step)) {
					double travelDistance = fly.getDistance();

					double actualCost = current.getCout();
					if(actualCost== Double.MAX_VALUE) actualCost = 0;
					double newDistance = travelDistance+actualCost;

					//check if already in temporary set if yes check if distance must be changed
					if(tempSummits.contains(step)) {
						if(step.getCout() > newDistance ) {
							tempSummits.remove(step);
							step.setCout(newDistance);
							tempSummits.add(step);
							path.put(step, fly);
						}

						//not in temp, so we add it
					}else {
						step.setCout(newDistance);
						tempSummits.add(step);
						path.put(step, fly);
					}
				}
			}
			if(tempSummits.isEmpty()) {
				break;
			}

			//we find the lowest distance (with the treeset/comparator) and we add it in the definitive set
			current = tempSummits.first();
			defSummits.add(current);
			tempSummits.remove(current);

		}

		// Fill the itinerary list from destination to source.
		ArrayList<Fly> itinerary = new ArrayList<>();
		Airport airport = airportMap.get(dest);
		while(airport != null) {
			if(airport == airportSource) {
				break;
			}
			itinerary.add(path.get(airport));		
			airport = itinerary.get(itinerary.size()-1).getSource();
		}

		//Display the shortest way with total Distance.
		double distanceTotale=0;
		for(int i=itinerary.size()-1;i>=0;i--) {
			Fly fly = itinerary.get(i);
			distanceTotale += fly.getDistance();
			System.out.println(fly);

		}
		System.out.println(distanceTotale);
	}




	//Create an airport with a line of the txt doc we receive for Airports.
	public Airport InstanciateAirportFromLine(String line) {
		String[] tab = line.split(",");
		Airport airport = new Airport(tab[0],tab[1],tab[2],tab[3],Double.parseDouble(tab[4]),Double.parseDouble(tab[5]));		
		return airport;
	}
	//Create an fly with a line of the txt doc we receive for flies.
	public Fly  InstanciateFlyFromLine(String line) {
		String[] tab = line.split(",");
		Airport src = airportMap.get(tab[1]);
		Airport dest = airportMap.get(tab[2]);
		Fly fly = new Fly(tab[0],src,dest);	
		return fly;
	}

}




