package dao.modelo;

import lombok.Data;

@Data
public class CurrentSeason {
    private long id;
    private String startDate;
    private String endDate;
    private Long currentMatchday;
    private Object winner;


}
