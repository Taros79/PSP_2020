package dao.modelo;

import lombok.Data;

import java.util.List;

@Data
public class CompetitionsRequest {
    private long count;
    private Filters filters;
    private List<Competition> competitions;


}
