package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecursosObj {

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private Object previous;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Objeto> objetos;

    public String getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }

    public List<Objeto> getResults() {
        return objetos;
    }

    @Override
    public String toString() {
        return
                "RecursosObj{" +
                        "next = '" + next + '\'' +
                        ",previous = '" + previous + '\'' +
                        ",count = '" + count + '\'' +
                        ",Objetos = '" + objetos + '\'' +
                        "}";
    }
}