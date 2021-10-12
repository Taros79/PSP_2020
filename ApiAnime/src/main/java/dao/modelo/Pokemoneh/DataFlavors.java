package dao.modelo.Pokemoneh;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class DataFlavors extends Berri {

    @SerializedName(value = "flavors")
    private List<Flavors> flavorsList;

}
