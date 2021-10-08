
package dao.modelo;

import lombok.Data;

import java.util.List;


@Data
public class Season {

    public Integer id;
    public String startDate;
    public String endDate;
    public Object currentMatchday;
    public List<String> availableStages = null;

}
