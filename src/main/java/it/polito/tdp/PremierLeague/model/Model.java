package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedGraph< Team , DefaultWeightedEdge>graph;
	private Map <Integer,Team   > idMap;
	private PremierLeagueDAO dao;
	private Simulatore sim;
	
	public Model() {
		idMap= new HashMap <Integer, Team >();
		dao=new PremierLeagueDAO();
	}
	
	public void creaGrafo() {
		graph= new SimpleDirectedGraph<>(DefaultWeightedEdge.class);
		
		dao.loadAllVertici(idMap);
		Graphs.addAllVertices(graph, idMap.values());
		
		//crea lista di partite per ogni vertice
		for(Team t: graph.vertexSet())
			t.setMatches(dao.getMatches(t));
		
		for(Team t1: graph.vertexSet())
			for(Team t2: graph.vertexSet())
				if(t1!=t2 && t1.getPunti()-t2.getPunti()!=0) {
					if(t1.getPunti()>t2.getPunti()) {
						double peso= (double)(t1.getPunti()-t2.getPunti());
						Graphs.addEdge (graph,t1,t2,peso);
					}else {
						Graphs.addEdge (graph,t2,t1,(double)(t2.getPunti()-t1.getPunti()));
					}
				}
		
		
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	public List <Team> getVertici(){
		List <Team> vertici = new ArrayList <Team>();
		for( Team t    : graph.vertexSet())
			vertici.add( t );
		Collections.sort(vertici);
		return vertici;
	}

	public SimpleDirectedGraph< Team , DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	
	//CLASSIFICA
	public List <Team> getClassifica(){
		List<Team> classifica = new ArrayList <Team>(graph.vertexSet());
		Collections.sort(classifica,new ComparatoreDiTeams());
		return classifica;
	}
	
	public class ComparatoreDiTeams implements Comparator <Team>{
		public int compare (Team t1, Team t2) {
			return t2.getPunti()-t1.getPunti();
		}
	}
	
	
	//SIMULATORE 
	public void simula(Integer N, Integer X) {
		sim= new Simulatore (graph,  N,  X, dao.listAllMatches(), idMap, this.getClassifica());
		sim.run();
		
	}
	
	public int getPartiteCritiche() {
		return sim.getPartiteCritiche();
	}


	public double getAvgReporterAMatch() {
		List<Integer> ReportersPerMatch= sim.getReporterPerMatchList();
		int totale=0;
		for(Integer i:ReportersPerMatch)
			totale+=i;
		return totale/ReportersPerMatch.size();
	}
	
	
	
	
	
}
