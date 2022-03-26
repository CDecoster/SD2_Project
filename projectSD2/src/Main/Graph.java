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
		HashMap<Airport,Fly> path = new HashMap<Airport, Fly>();
		System.out.println("coucou");
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
		
		//initialisation du TreeSet sur base du comparateur créer si dessus temporaire
		TreeSet<Airport> sommetsTemp = new TreeSet<Airport>(comparateur);
		
		//sommet definitif
		HashSet<Airport> sommetsDef = new HashSet<>();
		
		//cout de la source initialisé à 0
		Airport airportSource=airportMap.get(source);
		airportSource.setCout(0);
		
		//ajout de la source directement dans sommet def
		sommetsDef.add(airportSource);
		
		Airport destination = airportSource;
		
	
		
		while(destination.getIso2()!= dest) { //change here destination airport temp
			
			//récuperer la distance entre airportTemps et ses destinations noté la distance
			for(Fly fly : outGoingFlies.get(destination)) { //change here destination airport temp
				
				//verifier si déjà placé dans definitif
				destination = fly.getDest();
				if(!sommetsDef.contains(destination)) {
					double distanceTrajet = fly.getDistance();
					double coutActuel = destination.getCout();
					if(coutActuel== Integer.MAX_VALUE) coutActuel = 0;
					double nouvelleDistance = distanceTrajet+coutActuel;
					
					//check si déjà dans les sommets temporaires
					if(sommetsTemp.contains(destination)) {
						if(destination.getCout() > nouvelleDistance ) {
							sommetsTemp.remove(destination);
							destination.setCout(nouvelleDistance);
							sommetsTemp.add(destination);
							System.out.println("si contains"+fly);
							path.put(destination, fly);
						}
					}else {
						//pas dans les sommets temp
						destination.setCout(nouvelleDistance);
						sommetsTemp.add(destination);
						
						path.put(destination, fly);
					}
				}
			}
			
			if(sommetsTemp.isEmpty()) {
				break;
			}
			//récuperer le sommet le plus court
			Airport airportLowest = sommetsTemp.first();
			sommetsDef.add(airportLowest);
			sommetsTemp.remove(airportLowest);
				
		}
		 
		
		//distance totale
		double distanceTotale=0;
		ArrayList<Fly> trajet = new ArrayList<>();
		Airport airport = airportMap.get(dest);
		//System.out.println((Fly) outGoingFlies.get(airport).toArray()[0]);
		//trajet.add((Fly) outGoingFlies.get(airport).toArray()[0]);
		
		while(airport != null) {

			if(airport == airportSource) {
				break;
			}
			trajet.add(path.get(airport));
			Fly fly = trajet.get(trajet.size()-1);
			airport = fly.getSource();
	
		}
		for(Fly fly :trajet) {
			System.out.println("coucou");

		}
		

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




