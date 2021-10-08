
package dao.modelo;


import lombok.Data;

@Data
public class Team {

    public Integer id;
    public Area area;
    public String name;
    public String shortName;
    public String tla;
    public Object crestUrl;
    public String address;
    public String phone;
    public String website;
    public String email;
    public Integer founded;
    public String clubColors;
    public String venue;
    public String lastUpdated;

}
