package cwh.order.producer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/7 0007.
 */
@Data
public class FoodClassify {

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String classify_name;
    private String openid;
    private int classify_sort;

    private int food_count;
}
