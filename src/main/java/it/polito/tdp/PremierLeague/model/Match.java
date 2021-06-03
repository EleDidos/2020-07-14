package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Match implements Comparable <Match>{
	Integer matchID;
	Integer teamHomeID;
	Integer teamAwayID;
	Integer teamHomeFormation;
	Integer teamAwayFormation;
	Integer resultOfTeamHome;
	
	

	LocalDateTime date;
	
	List <Reporter> reportersMatch;
	
	/**
	 * 0 = in caso di pareggio
	 * @return
	 */
	public Integer IDVincente() {
		if(resultOfTeamHome==1)
			return teamHomeID;
		else if (resultOfTeamHome==-1)
			return teamAwayID;
		else
			return 0;
	}
	
	/**
	 * 0 = in caso di pareggio
	 * @return
	 */
	public Integer IDPerdente() {
		if(resultOfTeamHome==1)
			return teamAwayID;
		else if (resultOfTeamHome==-1)
			return teamHomeID;
		else
			return 0;
	}
	
	
	public Match(Integer matchID, Integer teamHomeID, Integer teamAwayID, Integer teamHomeFormation,
			Integer teamAwayFormation, Integer resultOfTeamHome, LocalDateTime date) {
		
		this.matchID = matchID;
		this.teamHomeID = teamHomeID;
		this.teamAwayID = teamAwayID;
		this.teamHomeFormation = teamHomeFormation;
		this.teamAwayFormation = teamAwayFormation;
		this.resultOfTeamHome = resultOfTeamHome;
		
		this.date = date;
		reportersMatch= new ArrayList <Reporter>();
	}
	
	public Integer getMatchID() {
		return matchID;
	}
	public void setMatchID(Integer matchID) {
		this.matchID = matchID;
	}
	public Integer getTeamHomeID() {
		return teamHomeID;
	}
	public void setTeamHomeID(Integer teamHomeID) {
		this.teamHomeID = teamHomeID;
	}
	public Integer getTeamAwayID() {
		return teamAwayID;
	}
	public void setTeamAwayID(Integer teamAwayID) {
		this.teamAwayID = teamAwayID;
	}
	public Integer getTeamHomeFormation() {
		return teamHomeFormation;
	}
	public void setTeamHomeFormation(Integer teamHomeFormation) {
		this.teamHomeFormation = teamHomeFormation;
	}
	public Integer getTeamAwayFormation() {
		return teamAwayFormation;
	}
	public void setTeamAwayFormation(Integer teamAwayFormation) {
		this.teamAwayFormation = teamAwayFormation;
	}
	public Integer getResultOfTeamHome() {
		return resultOfTeamHome;
	}
	public void setResultOfTeamHome(Integer resultOfTeamHome) {
		this.resultOfTeamHome = resultOfTeamHome;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchID == null) ? 0 : matchID.hashCode());
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
		Match other = (Match) obj;
		if (matchID == null) {
			if (other.matchID != null)
				return false;
		} else if (!matchID.equals(other.matchID))
			return false;
		return true;
	}
	
	public void addReporter(Reporter r) {
		reportersMatch.add(r);
	}
	
	public int compareTo (Match other) {
		return this.date.compareTo(other.date);
	}

	public List<Reporter> getReportersMatch() {
		return reportersMatch;
	}
	
	
}
