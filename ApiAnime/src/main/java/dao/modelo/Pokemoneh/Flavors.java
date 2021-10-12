
package dao.modelo.Pokemoneh;

import com.google.gson.annotations.Expose;
import dao.modelo.marvel.*;
import lombok.Data;

import java.util.List;

@Data
public class Flavors {

    @Expose
    private int potency;
    @Expose
    private Flavor flavor;
}
