package dao.modelo.sfdf;

public class VersionGroupDetailsItem{
	private int levelLearnedAt;
	private VersionGroup versionGroup;
	private MoveLearnMethod moveLearnMethod;

	public int getLevelLearnedAt(){
		return levelLearnedAt;
	}

	public VersionGroup getVersionGroup(){
		return versionGroup;
	}

	public MoveLearnMethod getMoveLearnMethod(){
		return moveLearnMethod;
	}
}
