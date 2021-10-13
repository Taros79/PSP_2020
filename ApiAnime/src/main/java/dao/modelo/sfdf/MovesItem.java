package dao.modelo.sfdf;

import java.util.List;

public class MovesItem{
	private List<VersionGroupDetailsItem> versionGroupDetails;
	private Move move;

	public List<VersionGroupDetailsItem> getVersionGroupDetails(){
		return versionGroupDetails;
	}

	public Move getMove(){
		return move;
	}
}