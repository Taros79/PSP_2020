package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;
import dao.modelo.ModMovimientos.FlavorTextEntriesItem;
import dao.modelo.ModMovimientos.Type;

import java.util.List;

public class MovimientoPrp {

    @SerializedName("pp")
    private int pp;

    @SerializedName("accuracy")
    private int accuracy;

    @SerializedName("type")
    private Type type;

    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntriesItem> flavorTextEntries;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("power")
    private int power;

    public int getPp() {
        return pp;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Type getType() {
        return type;
    }

    public List<FlavorTextEntriesItem> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "PP --> " + pp +
                "\nPrecision --> " + accuracy +
                "\nPotencia --> " + power +
                "\nTipo --> " + type.getName();
    }
}