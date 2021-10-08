
package dao.modelo;

import lombok.Data;

import java.util.List;

@Data

public class TeamsRequest {

    public Integer count;
    public Filters filters;
    public Competition competition;
    public Season season;
    public List<Team> teams = null;

}
