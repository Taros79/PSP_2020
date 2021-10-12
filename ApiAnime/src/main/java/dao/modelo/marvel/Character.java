
package dao.modelo.marvel;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.List;

@Data
public class Character {

    @Expose
    private Comics comics;
    @Expose
    private String description;
    @Expose
    private Events events;
    @Expose
    private String id;
    @Expose
    private String modified;
    @Expose
    private String name;
    @Expose
    private String resourceURI;
    @Expose
    private Series series;
    @Expose
    private Stories stories;
    @Expose
    private Thumbnail thumbnail;
    @Expose
    private List<Url> urls;



}
