package cwh.order.producer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/17 0017.
 */
@Data
public class FoodTable {

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String t_name;
    private String openid;

}
