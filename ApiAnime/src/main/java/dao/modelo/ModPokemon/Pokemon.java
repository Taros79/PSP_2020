package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {

    @SerializedName("location_area_encounters")
    private String zonasAparicion;

    @SerializedName("types")
    private List<TypesItem> tipo;

    @SerializedName("base_experience")
    private int experienciaBase;

    @SerializedName("held_items")
    private List<HeldItemsItem> itemsList;

    @SerializedName("weight")
    private int peso;

    @SerializedName("is_default")
    private boolean isDefault;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("abilities")
    private List<AbilitiesItem> habilidades;

    @SerializedName("game_indices")
    private List<GameIndicesItem> indicesJuego;

    @SerializedName("species")
    private Species especie;

    @SerializedName("stats")
    private List<StatsItem> stats;

    @SerializedName("moves")
    private List<MovesItem> movimientos;

    @SerializedName("name")
    private String nombre;

    @SerializedName("id")
    private int id;

    @SerializedName("forms")
    private List<FormsItem> formas;

    @SerializedName("height")
    private int altura;

    @SerializedName("order")
    private int orden;


    public String getZonasAparicion() {
        return zonasAparicion;
    }

    public List<TypesItem> getTipo() {
        return tipo;
    }

    public int getExperienciaBase() {
        return experienciaBase;
    }

    public List<HeldItemsItem> getItemsList() {
        return itemsList;
    }

    public int getPeso() {
        return peso;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<AbilitiesItem> getHabilidades() {
        return habilidades;
    }

    public List<GameIndicesItem> getIndicesJuego() {
        return indicesJuego;
    }

    public Species getEspecie() {
        return especie;
    }

    public List<StatsItem> getStats() {
        return stats;
    }

    public List<MovesItem> getMovimientos() {
        return movimientos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public List<FormsItem> getFormas() {
        return formas;
    }

    public int getAltura() {
        return altura;
    }

    public int getOrden() {
        return orden;
    }

    @Override
    public String toString() {
        return
                "Pokemon{" +
                        "location_area_encounters = '" + zonasAparicion + '\'' +
                        ",types = '" + tipo + '\'' +
                        ",base_experience = '" + experienciaBase + '\'' +
                        ",held_items = '" + itemsList + '\'' +
                        ",weight = '" + peso + '\'' +
                        ",is_default = '" + isDefault + '\'' +
                        ",sprites = '" + sprites + '\'' +
                        ",abilities = '" + habilidades + '\'' +
                        ",game_indices = '" + indicesJuego + '\'' +
                        ",species = '" + especie + '\'' +
                        ",stats = '" + stats + '\'' +
                        ",moves = '" + movimientos + '\'' +
                        ",name = '" + nombre + '\'' +
                        ",id = '" + id + '\'' +
                        ",forms = '" + formas + '\'' +
                        ",height = '" + altura + '\'' +
                        ",order = '" + orden + '\'' +
                        "}";
    }
}