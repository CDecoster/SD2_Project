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
		HashMap<Airport,Fly> path = new HashMap<Airport, Fly>();
		//initialisation des liens airports vols
		HashMap<Airport,HashSet<Fly>> outGoingFlies = new HashMap<>();
		for (String iso : airportMap.keySet()) {
			outGoingFlies.put(airportMap.get(iso),new HashSet<>());
		}
		for (Fly fly : flySet) {
			outGoingFlies.get(fly.getSource()).add(fly);
		}	
		
		//comparator pour rentrer les couts les sommets un par un dans le TreeSet et les ordonner directement
		Comparator<Airport> comparateur  = new Comparator<Airport> () {
			public int compare(Airport o1, Airport o2) {
				int delta = Double.compare(o1.getCout(), o2.getCout());
				if (delta==0) return o1.getIso2().compareTo(o2.getIso2());
				return delta;
			}
		};
		
		//initialisation du TreeSet sur base du comparateur cr�er si dessus temporaire
		TreeSet<Airport> sommetsTemp = new TreeSet<Airport>(comparateur);
		
		//sommet definitif
		HashSet<Airport> sommetsDef = new HashSet<>();
		
		//cout de la source initialis� � 0
		Airport airportSource=airportMap.get(source);
		airportSource.setCout(0);		
		//ajout de la source directement dans sommet def
		sommetsDef.add(airportSource);
		
		//Destination
		Airport destination = airportMap.get(dest);;
		
		Airport current = airportSource;
		
		while(!current.equals(destination)) { //change here destination airport temp
			
			//r�cuperer la distance entre airportTemps et ses destinations not� la distance
			for(Fly fly : outGoingFlies.get(current)) { //change here destination airport temp
				
				//verifier si d�j� plac� dans definitif
				current = fly.getDest();
				if(!sommetsDef.contains(current)) {
					double distanceTrajet = fly.getDistance();
					
					double coutActuel = current.getCout();
					if(coutActuel== Double.MAX_VALUE) coutActuel = 0;
					double nouvelleDistance = distanceTrajet+coutActuel;
					
					//check si d�j� dans les sommets temporaires
					if(sommetsTemp.contains(current)) {
						
						if(destination.getCout() > nouvelleDistance ) {
							sommetsTemp.remove(current);
							current.setCout(nouvelleDistance);
							sommetsTemp.add(current);
							
							path.put(current, fly);
						//	System.out.println(path.get(current));
						}
					}else {
						//pas dans les sommets temp
						current.setCout(nouvelleDistance);
						sommetsTemp.add(current);
						
						path.put(current, fly);
					//	System.out.println(path.get(current));
					}
				}
			}
			
			if(sommetsTemp.isEmpty()) {
				break;
			}
			//r�cuperer le sommet le plus court
			current = sommetsTemp.first();
			sommetsDef.add(current);
			sommetsTemp.remove(current);
				
		}
		 
		
		//distance totale
		double distanceTotale=0;
		ArrayList<Fly> trajet = new ArrayList<>();
		Airport airport = airportMap.get(dest);
		//System.out.println((Fly) outGoingFlies.get(airport).toArray()[0]);
		//trajet.add((Fly) outGoingFlies.get(airport).toArray()[0]);
		//System.out.println(path.get(airportMap.get("LHR")));
		while(airport != null) {
			
			if(airport == airportSource) {
				break;
			}
			trajet.add(path.get(airport));
		//	System.out.println(trajet.size());
			//System.out.println(path.get(airport));
			airport = trajet.get(trajet.size()-1).getSource();
			
	
		}
		for(Fly fly :trajet) {
			distanceTotale += fly.getDistance();
			System.out.println(fly);

		}
		System.out.println(distanceTotale);
	}
		

	
	
	
	public Airport InstanciateAirportFromLine(String line) {
		String[] tab = line.split(",");
		Airport airport = new Airport(tab[0],tab[1],tab[2],tab[3],Double.parseDouble(tab[4]),Double.parseDouble(tab[5]));		
		return airport;
	}

	public Fly  InstanciateFlyFromLine(String line) {
		String[] tab = line.split(",");
		Airport src = airportMap.get(tab[1]);
		Airport dest = airportMap.get(tab[2]);
		Fly fly = new Fly(tab[0],src,dest);	
		return fly;
	}

}




