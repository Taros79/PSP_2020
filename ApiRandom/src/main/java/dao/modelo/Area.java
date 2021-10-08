package dao.modelo;

import lombok.Data;

@Data
public class Area {
    private long id;
    private String name;
    private String countryCode;
    private String ensignURL;
    private Long parentAreaID;
    private String parentArea;


}
