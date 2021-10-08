package dao.modelo;

import lombok.Data;

import java.util.List;

@Data
public class AreasRequest {
    private long count;
    private Filters filters;
    private List<Area> areas;


}
