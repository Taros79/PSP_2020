package dao.modelo.sfdf;

import lombok.ToString;

import java.util.List;

@ToString
public class Pokemon{
	private String locationAreaEncounters;
	private List<TypesItem> types;
	private int baseExperience;
	private List<HeldItemsItem> heldItems;
	private int weight;
	private boolean isDefault;
	private Sprites sprites;
	private List<AbilitiesItem> abilities;
	private List<GameIndicesItem> gameIndices;
	private Species species;
	private List<StatsItem> stats;
	private List<MovesItem> moves;
	private String name;
	private int id;
	private List<FormsItem> forms;
	private int height;
	private int order;

	public String getLocationAreaEncounters(){
		return locationAreaEncounters;
	}

	public List<TypesItem> getTypes(){
		return types;
	}

	public int getBaseExperience(){
		return baseExperience;
	}

	public List<HeldItemsItem> getHeldItems(){
		return heldItems;
	}

	public int getWeight(){
		return weight;
	}

	public boolean isIsDefault(){
		return isDefault;
	}

	public Sprites getSprites(){
		return sprites;
	}

	public List<AbilitiesItem> getAbilities(){
		return abilities;
	}

	public List<GameIndicesItem> getGameIndices(){
		return gameIndices;
	}

	public Species getSpecies(){
		return species;
	}

	public List<StatsItem> getStats(){
		return stats;
	}

	public List<MovesItem> getMoves(){
		return moves;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public List<FormsItem> getForms(){
		return forms;
	}

	public int getHeight(){
		return height;
	}

	public int getOrder(){
		return order;
	}
}