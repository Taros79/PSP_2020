package dao.modelo.Pokemoneh;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Berri {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int growth_time;
    @Expose
    private int max_harvest;
    @Expose
    private int natural_gift_power;
    @Expose
    private int size;
    @Expose
    private int smoothness;
    @Expose
    private int soil_dryness;
    @Expose
    private Item item;
    @Expose
    private Firmness firmness;

    @SerializedName(value = "natural_gift_type")
    private NaturalGiftType naturalGiftType;
}
