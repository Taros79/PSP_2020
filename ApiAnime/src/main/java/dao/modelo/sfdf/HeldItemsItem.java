package dao.modelo.sfdf;

import java.util.List;

public class HeldItemsItem{
	private Item item;
	private List<VersionDetailsItem> versionDetails;

	public Item getItem(){
		return item;
	}

	public List<VersionDetailsItem> getVersionDetails(){
		return versionDetails;
	}
}