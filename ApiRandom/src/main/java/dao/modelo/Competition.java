package dao.modelo;

import lombok.Data;

@Data
public class Competition {
    private long id;
    private Area area;
    private String name;
    private String code;
    private Object emblemURL;
    private String plan;
    private CurrentSeason currentSeason;
    private long numberOfAvailableSeasons;
    private String lastUpdated;


}
