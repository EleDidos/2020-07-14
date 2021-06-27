package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Simulatore {
	
	
	private SimpleDirectedGraph< Team , DefaultWeightedEdge> graph;
	private Integer N; //per squadra all'inizio
	private Integer X; //soglia
	private List <Match> matches;
	private Map <Integer,Team   > idMap;
	private List<Team> classifica;
	private int partiteCritiche;
	
	private List <Integer> reporterPerMatchList;
	
	
	public Simulatore(SimpleDirectedGraph< Team , DefaultWeightedEdge> graph, Integer N, Integer X, List <Match> matches,Map <Integer,Team   > idMap,List<Team> classifica) {
		
		//ordina le partite cronologicamente
		Collections.sort(matches);
		
		this.graph=graph;
		this.N=N;
		this.X=X;
		this.matches=matches;
		this.idMap=idMap;
		this.classifica=classifica;
		partiteCritiche=0;
		
		reporterPerMatchList = new ArrayList<Integer>();
		
		//N REPORTER PER TEAM
		int idReporter=1;
		for(Team t: graph.vertexSet())
			for(int i=1;i<=N;i++)
				t.addReporter(new Reporter(idReporter++));
		
		
	}
	
	
	public void run() {
		for (Match scelto: matches)
			
			processMatch(scelto);
		//for
	}
	

	double prob;
	
	private void processMatch(Match m) {
		switch(m.getResultOfTeamHome()) {
				
			case 0: //PAREGGIO: non succede niente
				Team team1 = idMap.get(m.getTeamHomeID());
				Team team2 =idMap.get(m.getTeamAwayID());
				
				//prendi il numero di reporter presenti alla partita
				int nReporterTotali0 = team1.getReportersTeam().size()+team2.getReportersTeam().size();
				reporterPerMatchList.add(nReporterTotali0);
				if(nReporterTotali0<X)
					partiteCritiche++;
				break;
				
			case 1: //VITTORIA di CASA
				
				Team winner = idMap.get(m.getTeamHomeID());
				Team loser =idMap.get(m.getTeamAwayID());
				
				//prendi il numero di reporter presenti alla partita
				int nReporterTotali = winner.getReportersTeam().size()+loser.getReportersTeam().size();
				reporterPerMatchList.add(nReporterTotali);
				if(nReporterTotali<X)
					partiteCritiche++;
				
				//////////////////////squadra di casa che vince
				 prob = Math.random();
				if(prob<=0.5) { //cambio un reporter
					Reporter change = winner.getOneReporter();
					if(change!=null) //ci sono dei reporter
						this.putInABetterTeam(change, winner);
				}
				/////////////////////squadra in trasferta che perde
				 prob = Math.random();
				 
					if(prob<=0.2) { //cambio uno o più reporter
						//n° di reporter da cambiare
						int nReporter = loser.getReportersTeam().size();
						if(nReporter>0) {
							double d = Math.random()*nReporter;
					    	double approssimato = Math.ceil(d); //devo cambiare almeno 1 reporter --> per eccesso
							int nReporterToChange = (int)approssimato; ;
							for(int i=1;i<=nReporterToChange;i++) {
								Reporter change = loser.getOneReporter();
								this.putInAWorseTeam(change, loser);
							}
						}//nReporter>0
						
					}//20%
				
				break;
				
				
			case -1: //VITTORIA TRAFSERTA
				
				Team winner2 = idMap.get(m.getTeamAwayID());
				Team loser2 =idMap.get(m.getTeamHomeID());
				
				//prendi il numero di reporter presenti alla partita
				int nReporterTotali2 = winner2.getReportersTeam().size()+loser2.getReportersTeam().size();
				reporterPerMatchList.add(nReporterTotali2);
				if(nReporterTotali2<X)
					partiteCritiche++;
				
				//////////////////////squadra trasferta che vince
				 prob = Math.random();
				if(prob<=0.5) { //tolgo un reporter da questa squadra e scelgo dove metterlo
					Reporter change = winner2.getOneReporter();
					if(change!=null) //ci sono dei reporter
						this.putInABetterTeam(change, winner2);
				}
				/////////////////////squadra in casa che perde
				 prob = Math.random();
				 
					if(prob<=0.2) { //cambio uno o più reporter
						//n° di reporter da cambiare
						int nReporter = loser2.getReportersTeam().size();
						if(nReporter>0) {
							double d = Math.random()*nReporter;
					    	double approssimato = Math.ceil(d); //devo cambiare almeno 1 reporter --> per eccesso
							int nReporterToChange = (int)approssimato; ;
							for(int i=1;i<=nReporterToChange;i++) {
								Reporter change = loser2.getOneReporter();
								this.putInAWorseTeam(change, loser2);
							}
						}//nReporter>0
						
					}//20%
				
				break;
				
			default:
				break;
		}
	}


	/**
	 * prende reporter passato dalla sua attuale suqadra
	 * e sceglie casualmente una suqdra migliore in cui metterlo
	 * @param change
	 */
	private void putInABetterTeam(Reporter change, Team attuale) {
		//posizione dell'attuale team
		int posizione=0;
		for(int i=0;i<classifica.size();i++)
			if(classifica.get(i).equals(attuale))
				posizione=i+1;
		//se è già in prima squadra rimane li
		if(posizione==1)
			return;
		//da 0 al numero di squadre migliori di attuale
		double probabilityD = (Math.random()*(posizione-1));
		int probability= (int)probabilityD;
		//scegli tra le migliori
		Team nuovo = classifica.get(probability);
		//aggiungi il reporter
		nuovo.addReporter(change);
		
	}
	
	
	/**
	 * prende reporter passato dalla sua attuale suqadra
	 * e sceglie casualmente una suqdra peggiore in cui metterlo
	 * @param change
	 */
	private void putInAWorseTeam(Reporter change, Team attuale) {
		//posizione dell'attuale team
		int posizione=0;
		for(int i=0;i<classifica.size();i++)
			if(classifica.get(i).equals(attuale))
				posizione=i+1;
		//se è già in ultima squadra rimane li
		if(posizione==classifica.size())
			return;
		
		//lista di peggiori
		List <Team> peggiori=new ArrayList <Team>();
		for(int i=classifica.size()-1;i>=posizione;i--)
			peggiori.add(classifica.get(i));
		//da 0 al numero di squadre peggiori di attuale
		int probability= (int)(Math.random()*(peggiori.size()));
		//scegli tra le peggiori
		Team nuovo = peggiori.get(probability);
		//aggiungi il reporter
		nuovo.addReporter(change);
		
	}


	public int getPartiteCritiche() {
		return partiteCritiche;
	}




	public List<Integer> getReporterPerMatchList() {
		return reporterPerMatchList;
	}

	
	
	

}
