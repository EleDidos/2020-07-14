package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable <Team>{
	private Integer teamID;
	private String name;
	private List <Match> matches = new ArrayList <Match>();
	private int punti=0;
	private List <Reporter> reportersTeam;
	

	public List<Reporter> getReportersTeam() {
		return reportersTeam;
	}

	/**
	 * se ci sono dei reporter ne tolgo uno
	 * @return
	 */
	public Reporter getOneReporter() {
		if(reportersTeam==null)
			return null;
		Reporter change = reportersTeam.remove(0); //rimuovo il primo
		return change;
	}

	

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
		//calcola i punti
		for(Match m : matches) {
			if(m.getResultOfTeamHome()==0)
				punti++; //pareggio
			else if(m.getTeamHomeID().equals(this.teamID) && m.getResultOfTeamHome()==1)
				punti+=3;
			else if(m.getTeamAwayID().equals(this.teamID) && m.getResultOfTeamHome()==-1)
				punti+=3;
		}
	}

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		reportersTeam = new ArrayList <Reporter>();
	}
	
	public void addReporter(Reporter r) {
		reportersTeam.add(r);
	}
	
	public void removeReporter(Reporter r) {
				reportersTeam.remove(r);
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}
	
	public int compareTo(Team other) {
		return this.name.compareTo(other.name);
	}
	
	public Integer getPunti() {
		
		return punti;
	}
}
