
package dao.modelo.Pokemoneh;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Item {

    @Expose
    private String name;
    @Expose
    private String url;
}
