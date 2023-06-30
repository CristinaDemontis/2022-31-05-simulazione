/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;

import java.util.*;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Quartiere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<String> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<?, ?> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<?, ?> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<?> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String provider = cmbProvider.getValue(); 
    	if(provider == null) {
    		txtResult.setText("Scegliere un provider dal menu a tendina!");
    		return; 
    	}else {
    		this.model.creaGrafo(provider);
    		txtResult.setText("Grafo Creato! \n");
    		txtResult.appendText("- Vertici: "+ this.model.getNVertici()+"\n");
    		txtResult.appendText("- Archi: "+ this.model.getNArchi()+"\n");
    		
    		List<Quartiere> citta = this.model.getVertici();  
    		for(Quartiere q: citta) {
    			cmbQuartiere.getItems().add(q.getCitta()); 
    		}

    	}
    	
    	
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	
    	String quartiere = cmbQuartiere.getValue(); 
    	if(quartiere == null) {
    		txtResult.setText("Scegliere un quartiere dal menu a tendina!");
    		return; 
    	}else {
    		List<String> result = this.model.analizzaGrafo(quartiere); 
    		txtResult.appendText("QUARTIERI ADIACENTI: \n");
    		for(String s: result) {
    			txtResult.appendText(s+ "\n");
    			
    		}
    	}
    	
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	List<String> providers = this.model.getAllProvider(); 
    	for(String s: providers) {
    		cmbProvider.getItems().add(s); 
    	}
    }

}
