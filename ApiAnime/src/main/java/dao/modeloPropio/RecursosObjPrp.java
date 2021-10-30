package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecursosObjPrp {

    @SerializedName("results")
    private List<ObjetoPrp> objetos;

    public List<ObjetoPrp> getResults() {
        return objetos;
    }

}