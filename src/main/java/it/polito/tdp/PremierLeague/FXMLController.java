/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
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
    	
    	//CONTROLLA SE ESISTE GRAFO
    	if(model.getGraph()==null) {
    		txtResult.setText("Devi prima creare il grafo!");
    		return;
    	}
    	
    	Team scelto;
    	
    	try {
    		scelto=cmbSquadra.getValue();
    		if(scelto==null) {
    			txtResult.setText("Scegli una squadra");
    			return;
    		}
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegli una squadra");
    		return;
    	}
    		int indice=0;
    		List <Team> classifica= model.getClassifica();
    		
    		for(int i=0;i<classifica.size();i++)
    			if(classifica.get(i).equals(scelto))
    				indice=i;
    		
    		int puntiScelto=scelto.getPunti();
    				
    		txtResult.appendText("\n\nLe squadre che hanno totalizzato MENO PUNTI della prescelta sono:\n");
    		for(int i=indice+1;i<classifica.size();i++)
    			txtResult.appendText(classifica.get(i)+"( "+(puntiScelto-classifica.get(i).getPunti())+" )\n");
    		
    		txtResult.appendText("\n\nLe squadre che hanno totalizzato PIU' PUNTI della prescelta sono:\n");
    		for(int i=0;i<indice;i++)
    			txtResult.appendText(classifica.get(i)+"( "+(classifica.get(i).getPunti()-puntiScelto)+" )\n");
    		
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	model.creaGrafo();
    	txtResult.appendText("Caratteristiche del grafo:\n#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
    	
    	cmbSquadra.getItems().addAll(model.getVertici());
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	if(model.getGraph()==null) {
    		txtResult.setText("Devi prima creare il grafo!");
    		return;
    	}
    	
    	Integer N=0;
    	Integer X=0;
    	
    	try {
    		N=Integer.parseInt(txtN.getText());
    		X=Integer.parseInt(txtX.getText());
    		if(N==null || X==null) {
    			txtResult.setText("Inserisci un numero intero di reporter e una soglia di criticità");
    			return;
    		}
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserisci un numero intero di reporter e una soglia di criticità");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero intero di reporter e una soglia di criticità");
    		return;
    	}
    	
    	model.simula(N, X);
    	txtResult.appendText("\n\nIl numero medio di reporter per partita è: "+model.getAvgReporterAMatch());
    	txtResult.appendText("\nIl numero di partite con un n° di reporter sotto la soglia critica è: "+model.getPartiteCritiche());
    	
    	
    	
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
