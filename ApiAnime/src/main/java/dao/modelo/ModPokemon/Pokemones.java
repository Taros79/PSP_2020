package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;
import dao.modelo.Pokemoneh.ResultsItem;

import java.util.List;

public class Pokemones {

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private Object previous;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<ResultsItem> results;

    public String getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "Pokemones{" +
                        "next = '" + next + '\'' +
                        ",previous = '" + previous + '\'' +
                        ",count = '" + count + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}