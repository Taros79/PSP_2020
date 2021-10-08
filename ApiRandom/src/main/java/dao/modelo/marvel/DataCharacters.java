package dao.modelo.marvel;

import com.google.gson.annotations.SerializedName;
import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString(callSuper = true)
public class DataCharacters extends Data{

    @SerializedName(value = "results")
    private List<Character> characters;
}
