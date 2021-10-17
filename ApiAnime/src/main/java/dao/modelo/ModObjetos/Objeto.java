package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Objeto {

    @SerializedName("cost")
    private int cost;

    @SerializedName("fling_power")
    private int flingPower;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("fling_effect")
    private FlingEffect flingEffect;

    @SerializedName("effect_entries")
    private List<EffectEntriesItem> effectEntries;

    @SerializedName("game_indices")
    private List<GameIndicesItem> gameIndices;

    @SerializedName("names")
    private List<NamesItem> names;

    @SerializedName("baby_trigger_for")
    private BabyTriggerFor babyTriggerFor;

    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntriesItem> flavorTextEntries;

    @SerializedName("name")
    private String name;

    @SerializedName("attributes")
    private List<AttributesItem> attributes;

    @SerializedName("id")
    private int id;

    @SerializedName("category")
    private Category category;

    @SerializedName("held_by_pokemon")
    private List<HeldByPokemonItem> heldByPokemon;

    public int getCost() {
        return cost;
    }

    public int getFlingPower() {
        return flingPower;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public FlingEffect getFlingEffect() {
        return flingEffect;
    }

    public List<EffectEntriesItem> getEffectEntries() {
        return effectEntries;
    }

    public List<GameIndicesItem> getGameIndices() {
        return gameIndices;
    }

    public List<NamesItem> getNames() {
        return names;
    }

    public BabyTriggerFor getBabyTriggerFor() {
        return babyTriggerFor;
    }

    public List<FlavorTextEntriesItem> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public String getName() {
        return name;
    }

    public List<AttributesItem> getAttributes() {
        return attributes;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public List<HeldByPokemonItem> getHeldByPokemon() {
        return heldByPokemon;
    }

    @Override
    public String toString() {
        return
                "Item{" +
                        "cost = '" + cost + '\'' +
                        ",fling_power = '" + flingPower + '\'' +
                        ",sprites = '" + sprites + '\'' +
                        ",fling_effect = '" + flingEffect + '\'' +
                        ",effect_entries = '" + effectEntries + '\'' +
                        ",game_indices = '" + gameIndices + '\'' +
                        ",names = '" + names + '\'' +
                        ",baby_trigger_for = '" + babyTriggerFor + '\'' +
                        ",flavor_text_entries = '" + flavorTextEntries + '\'' +
                        ",name = '" + name + '\'' +
                        ",attributes = '" + attributes + '\'' +
                        ",id = '" + id + '\'' +
                        ",category = '" + category + '\'' +
                        ",held_by_pokemon = '" + heldByPokemon + '\'' +
                        "}";
    }
}