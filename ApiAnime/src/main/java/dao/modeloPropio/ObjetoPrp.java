package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;
import dao.modelo.ModObjetos.AttributesItem;
import dao.modelo.ModObjetos.FlavorTextEntriesItem;
import dao.modelo.ModObjetos.Sprites;

import java.util.List;

public class ObjetoPrp {

    @SerializedName("cost")
    private int cost;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntriesItem> flavorTextEntries;

    @SerializedName("name")
    private String name;

    @SerializedName("attributes")
    private List<AttributesItem> attributes;

    @SerializedName("id")
    private int id;

    public int getCost() {
        return cost;
    }

    public Sprites getSprites() {
        return sprites;
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

    @Override
    public String toString() {
        return "PRECIO [" + cost +
                "] Pokedolares \nATRIBUTOS " + attributes;
    }
}