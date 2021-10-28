package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Normal {

    @SerializedName("use_after")
    private Object useAfter;

    @SerializedName("use_before")
    private List<UseBeforeItem> useBefore;

    public Object getUseAfter() {
        return useAfter;
    }

    public List<UseBeforeItem> getUseBefore() {
        return useBefore;
    }

    @Override
    public String toString() {
        return
                "Normal{" +
                        "use_after = '" + useAfter + '\'' +
                        ",use_before = '" + useBefore + '\'' +
                        "}";
    }
}