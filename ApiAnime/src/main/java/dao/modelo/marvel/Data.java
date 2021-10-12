
package dao.modelo.marvel;

import com.google.gson.annotations.Expose;


@lombok.Data
public abstract class Data {

    @Expose
    private String count;
    @Expose
    private String limit;
    @Expose
    private String offset;

    @Expose
    private String total;


}
