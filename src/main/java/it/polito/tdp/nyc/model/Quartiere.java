package it.polito.tdp.nyc.model;

import java.util.*;

import com.javadocmd.simplelatlng.LatLng;


public class Quartiere {
	
	private String citta; 
	private int numeroHotspot; 
	private Set<LatLng> coordinate;
	public Quartiere(String citta, int numeroHotspot, Set<LatLng> coordinate) {
		super();
		this.citta = citta;
		this.numeroHotspot = numeroHotspot;
		this.coordinate = coordinate;
	}
	public String getCitta() {
		return citta;
	}
	public int getNumeroHotspot() {
		return numeroHotspot;
	}
	public Set<LatLng> getCoordinate() {
		return coordinate;
	} 
	
	public LatLng calcolaCoordinataMedia() {
		double lat = 0; 
		double log = 0; 
		
		for(LatLng l: this.coordinate) {
			lat = lat +  l.getLatitude(); 
			log =log + l.getLongitude(); 
		}
		double latMedia = lat/this.coordinate.size(); 
		double logMedia = log/this.coordinate.size();
		
		
		return new LatLng(lat, log); 
	}
	public void setNumeroHotspot(int numeroHotspot) {
		this.numeroHotspot = numeroHotspot;
	}
	
	
	
}
