package it.polito.tdp.PremierLeague.model;

public class Reporter {
	
	private int id;

	public int getId() {
		return id;
	}

	public Reporter(int id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Reporter other = (Reporter) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
