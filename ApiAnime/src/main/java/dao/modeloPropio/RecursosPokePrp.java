package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecursosPokePrp {

    @SerializedName("results")
    private List<PokemonPrp> results;

    public List<PokemonPrp> getResults() {
        return results;
    }

}