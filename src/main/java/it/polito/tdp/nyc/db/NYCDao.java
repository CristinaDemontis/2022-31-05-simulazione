package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.nyc.model.Citta;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.Quartiere;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllProvider(){
		String sql = "SELECT DISTINCT n.Provider "
				+ "FROM nyc_wifi_hotspot_locations n ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Provider"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public Map<String, Quartiere>  getAllCity(String provider){
		String sql = "SELECT n.City, n.Latitude, n.Longitude "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider = ? "
				+ "ORDER BY n.City ";
		
		Map<String, Quartiere> result = new HashMap<>();  
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Quartiere q = result.get(res.getString("City")); 
				if(q == null) {
					Set<LatLng> coordinate = new HashSet<>(); 
					coordinate.add(new LatLng(res.getDouble("Latitude"),res.getDouble("Longitude"))); 
					Quartiere quartiere = new Quartiere(res.getString("City"), 1, coordinate); 
					result.put(quartiere.getCitta(), quartiere); 
				}else {
					q.setNumeroHotspot(q.getNumeroHotspot()+1);
					q.getCoordinate().add(new LatLng(res.getDouble("Latitude"),res.getDouble("Longitude")));
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllCitta(){
	
		String sql = "SELECT DISTINCT n.City "
				+ "FROM nyc_wifi_hotspot_locations n ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("City"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	
	
	
	
	
}
