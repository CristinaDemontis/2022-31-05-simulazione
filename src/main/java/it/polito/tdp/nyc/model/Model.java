package it.polito.tdp.nyc.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private Graph<Quartiere, DefaultWeightedEdge> grafo; 
	private NYCDao dao; 
	private List<Quartiere> vertici;
	private Map<String, Quartiere> mappaQuartieri; 
	
	public Model() {
		this.dao = new NYCDao(); 
	} 
	
	public List<String> getAllProvider(){
		return this.dao.getAllProvider(); 
	}
	
	public void creaGrafo(String provider) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		this.mappaQuartieri = this.dao.getAllCity(provider); 
		this.vertici = new ArrayList<>(mappaQuartieri.values()); 
		
		Graphs.addAllVertices(this.grafo, this.vertici); 
		
		for(Quartiere q1: vertici) {
			for(Quartiere q2: vertici ) {
				if(!q1.getCitta().equals(q2.getCitta()) && q1.getNumeroHotspot()>0 && q2.getNumeroHotspot()>0) {
					LatLng lQ1 = q1.calcolaCoordinataMedia();
					LatLng lQ2 = q2.calcolaCoordinataMedia(); 
					double distanza = LatLngTool.distance(lQ1, lQ2, LengthUnit.KILOMETER);
					if(distanza > 0) {
						Graphs.addEdgeWithVertices(this.grafo, q1, q2, distanza); 
					}
				}
			}
		}
		
		System.out.println(this.grafo.vertexSet().size());
		System.out.println(this.grafo.edgeSet().size());
		
	}
	
	
	public List<String> analizzaGrafo(String citta){
		
		ConnectivityInspector<Quartiere, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo); 
		Map<String, Quartiere> mappa = new HashMap<>(this.mappaQuartieri); 
		Set<Quartiere> set = ci.connectedSetOf(mappa.get(citta)); 
		List<String> result = new ArrayList<>(); 
		for(Quartiere q: set) {
			Quartiere input = mappa.get(citta); 
			DefaultWeightedEdge e = this.grafo.getEdge(input, q); 
			double peso = this.grafo.getEdgeWeight(e); 
			String nomeCitta2 = q.getCitta(); 
			String s = "Quartiere: "+ nomeCitta2 + " con distanza: " +peso; 
			result.add(s); 
		}
		
		return result; 
		
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size(); 
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size(); 
	} 
	
	public List<String> getAllCitta(){
		return this.dao.getAllCitta(); 
	}

	public List<Quartiere> getVertici() {
		return vertici;
	}
	
	
	
	
	
	
	
}
