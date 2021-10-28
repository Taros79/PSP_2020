package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;
import dao.modelo.ModPokemon.Items;

import java.util.List;

public class Recursos {

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private Object previous;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Items> results;

    public String getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }

    public List<Items> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "RecursosObj{" +
                        "next = '" + next + '\'' +
                        ",previous = '" + previous + '\'' +
                        ",count = '" + count + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}