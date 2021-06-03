/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	
    	if(this.model.getGraph()==null) {
    		txtResult.appendText("Devi prima creare il grafo");
    		return;
    	}
    	Team scelto;
    	try {
    		scelto=cmbSquadra.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.appendText("Devi selezionare una squadra");
    		return;
    	}
    	int soglia=scelto.getPunti();
    	Map <Integer,Team> classifica = this.model.getClassifica();
    	
    	txtResult.appendText("\n\nLe squadre che hanno totalizzato più punti sono:\n ");
    	for(Integer punti: classifica.keySet())
    		if(punti>soglia)
    			txtResult.appendText(classifica.get(punti)+" "+punti+"\n");
    	
     	txtResult.appendText("\nLe squadre che hanno totalizzato meno punti sono:\n ");
    	for(Integer punti: classifica.keySet())
    		if(punti<soglia)
    			txtResult.appendText(classifica.get(punti)+" "+punti+"\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	this.model.creaGrafo();
    	cmbSquadra.getItems().addAll(this.model.getSquadre());
    	txtResult.appendText("Caratteristiche del GRAFO creato:\n#VERTICI = "+this.model.getNVertici()+"\n#ARCHI = "+this.model.getNArchi());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	Integer N; //reporter per squadra
    	Integer X; //min n° di reporter per partita
    	try {
    		N=Integer.parseInt(txtN.getText());
    		X=Integer.parseInt(txtX.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Devi scrivere due numeri interi");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.appendText("Devi scrivere due numeri interi");
    		return;
    	}
    	
    	this.model.simula(N);
    	
    	txtResult.appendText("\nMediamente il numero di reporter che ha assistito a una partita"
    			+ "è: "+this.model.getAVGReporters()+"\n");
    	
    	txtResult.appendText("Il numero di partite per cui il numero di reporter è sotto la soglia è: "
    			+ " "+this.model.getNPartiteSottoSoglia(X)+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
