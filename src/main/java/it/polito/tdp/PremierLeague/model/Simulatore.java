package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Simulatore {
	
	private int N; //reporters per squadra all'inizio
	private List<Match> matches;
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> graph;
	private int index=0; //dei reporter creati
	private int cnt=0; //mi permette di mettere N reporter per ogni team
	private Map <Integer, Team> idMap;
	private Map <Integer,Team> classifica;
	
	public void run(Integer n, List<Match> listAllMatches, SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> graph, 
			Map <Integer, Team> idMap, Map <Integer,Team> classifica) {
		this.N=n;
		this.matches=listAllMatches;
		this.graph=graph;
		this.idMap=idMap;
		this.classifica=classifica;
		
		Collections.sort(matches); //ordine cronologico
		
		//creo N reporters per ogni team
		for(Team t: graph.vertexSet()) {
			cnt=0;
			while(cnt<N) {
				Reporter r = new Reporter(index++);
				t.addReporter(r);
				cnt++;
			}
		}
			
		/////////// PER OGNI PARTITA IN ORDINE DI DATA ///////////////////////
		
		for(Match m: matches) {
			Integer IDVincente = m.IDVincente(); 
			Integer IDPerdente = m.IDPerdente();
			
			if(IDVincente==0) { //se PAREGGIO cambio match --> rimane tutto così
				continue;
			}
				
			Team vincente = idMap.get(IDVincente);
			Team perdente = idMap.get(IDPerdente);
			
			//associo reporters delle squadre al match
			for(Reporter r: vincente.getReportersTeam()  )
				m.addReporter(r);
			for(Reporter r: perdente.getReportersTeam()  )
				m.addReporter(r);
			
			//al termine del match --> promozione o bocciature
			double prob;
			
			//VINCITORE: prendo 1 reporter
			for(Reporter r: vincente.getReportersTeam() ) {
				prob=Math.random();
				if(prob<=0.5) {//tolgo il r da questa squadra e lo metto in una migliore
					vincente.removeReporter(r);
					this.cambiaTeamAReporter(r,1, vincente);
				}	
				break;
			}
			
			//PERDENTE: prendo 1 o più reporter e li riassegno
			prob=Math.random();
			if(prob<=0.2) {
				int nReporterPerdente = perdente.getReportersTeam().size();
				//int probReporter = (int)Math.random()*nReporterPerdente;
////PROBLEMA ---> 0,......		
				int probReporter = 2;
				//sposto un numero di reporter pari a "probReporter"
				
				
				for(int j=0; j<probReporter;j++) {
					Reporter r = perdente.getReportersTeam().get(j);
					perdente.removeReporter(r);
					this.cambiaTeamAReporter(r,-1, perdente);
				}
			}
			
			
		}//for per ogni partita
		
	} //funzione

	
	/**
	 * Se i=1 --> lo sposto in una squadra migliore
	 * Se i=-1 --> lo sposto in una squadra peggiore
	 * @param r
	 * @param i
	 */
	private void cambiaTeamAReporter(Reporter r, int i, Team team) {
		switch(i) {
			case 1:
				for(Integer punti: classifica.keySet())
					if(punti>team.getPunti()) {
						team.addReporter(r);
						break;
					}
			case -1:
				for(Integer punti: classifica.keySet())
					if(punti<team.getPunti()) {
						team.addReporter(r);
						break;
					}
			default:
				break;
		}
		
	}
	

}
