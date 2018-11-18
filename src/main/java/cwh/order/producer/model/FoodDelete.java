package cwh.order.producer.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/17 0017.
 */
@Data
public class FoodDelete {

    private long id;
    private String f_name;
    private String description;
    private BigDecimal price;
    private String classify_name;
    private String picture_url;
    private String openid;
    private Date delete_time;

}
