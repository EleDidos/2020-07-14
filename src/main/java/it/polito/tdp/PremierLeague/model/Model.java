package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedWeightedGraph <Team, DefaultWeightedEdge> graph;
	private Map <Integer, Team> idMap;
	private PremierLeagueDAO dao;
	private Simulatore sim;
	private Map <Integer,Team> classifica;
	
	public Model() {
		
		dao = new PremierLeagueDAO();
		idMap = new HashMap <Integer, Team> ();
		
		this.dao.listAllTeams(idMap);
	}
	
	

	public void creaGrafo() {
		graph = new  SimpleDirectedWeightedGraph <>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, idMap.values());
		
		//registro partite di ogni squadra
		//le calcolo i punti a fine stagione
		for(Team t: graph.vertexSet()) {
			this.dao.listAllMatches(t);
			this.findPunti(t);
		}
		
		//per ogni coppia creo EDGE in base ai punti dei vertici
		//arco da quella con più punti a quella con meno
		for(Arco a : this.dao.listAllPairs(idMap)) {
			int punti1 = a.getT1().getPunti();
			int punti2= a.getT2().getPunti();;
			if(punti1>punti2)
				Graphs.addEdgeWithVertices(graph,a.getT1(), a.getT2(), punti1-punti2);
			else if(punti2>punti1)
				Graphs.addEdgeWithVertices(graph,a.getT2(), a.getT1(), punti2-punti1);
			else //punti uguali
				continue;
		}
			
	
	}
	
	
	public void findPunti(Team t) {
		int points=0;
		for(Match m: t.getMatches()) {
			if(m.getTeamHomeID()==t.getTeamID() & m.getResultOfTeamHome()==1)
				points+=3;
			else if(m.getTeamAwayID()==t.getTeamID() & m.getResultOfTeamHome()==-1)
				points+=3;
			else if(m.getResultOfTeamHome()==0)
				points+=1;
			else
				points+=0;
		}
		t.setPunti(points);
	}
	
	public int getNVertici() {
		return graph.vertexSet().size();
	}
	
	public int getNArchi() {
		return graph.edgeSet().size();
	}
	
	public List <Team> getSquadre(){
		List <Team> teams = new ArrayList <Team>();
		for(Team t: graph.vertexSet())
			teams.add(t);
		Collections.sort(teams);
		return teams;
	}



	public SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
			//punti
	public Map<Integer, Team> getClassifica(){
		if(graph!=null) {
			classifica = new TreeMap <Integer,Team>();
			for(Team t: graph.vertexSet())
				classifica.put(t.getPunti(), t);
			return classifica;
		}
		return null;
	}
	
	
	public void simula(Integer N) {
		sim= new Simulatore();
		this.sim.run(N,this.dao.listAllMatches(), graph, idMap, classifica);
	}
	
	
	/**
	 * Media dei reporter che hanno assistito a ogni partita
	 */
	public double getAVGReporters() {
		
		double tot=0.0;
		for(Match m: this.dao.listAllMatches())
			tot+=m.getReportersMatch().size();
		return tot/this.dao.listAllMatches().size();
			
	}



	public Integer getNPartiteSottoSoglia(Integer x) {
		int cnt=0;
		for(Match m: this.dao.listAllMatches())
			if(m.getReportersMatch().size()<x)
				cnt++;
		return cnt;
	}
	
}
