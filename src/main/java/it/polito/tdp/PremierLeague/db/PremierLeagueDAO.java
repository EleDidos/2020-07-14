package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllTeams(Map <Integer, Team> idMap){
		String sql = "SELECT * FROM Teams";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("TeamID"))) {
					Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
					idMap.put(res.getInt("TeamID"), team);
			}
			conn.close();
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void loadAllVertici(Map <Integer, Team> idMap){
		
		
		String sql = "SELECT * "
				+ "FROM Teams" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getInt("TeamID"))) {
					Team t = new Team(res.getInt("TeamID"),res.getString("Name"));
					idMap.put(res.getInt("TeamID"),t);
					System.out.println();
				}//if
				
			}//while
			
			conn.close();
			return  ;

		} catch (SQLException e) {
			e.printStackTrace();
			return  ;
		}

	}



	public List<Match> getMatches(Team t) {
		String sql = "SELECT * "
				+ "FROM Matches as m "
				+ "WHERE TeamHomeID=? or TeamAwayID=?";

		Connection conn = DBConnect.getConnection();
		List <Match> matches = new ArrayList <Match>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			st.setInt(2, t.getTeamID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
						res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime());
				matches.add(match);
			

			}
			conn.close();
			return matches;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Match> listAllMatches() {
		String sql = "SELECT * "
				+ "FROM Matches as m ";

		Connection conn = DBConnect.getConnection();
		List <Match> matches = new ArrayList <Match>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
						res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime());
				matches.add(match);
			

			}
			conn.close();
			return matches;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
