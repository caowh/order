package cwh.order.producer.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Data
public class Food {

    private long id;
    private String f_name;
    private String description;
    private BigDecimal price;
    private int surplusCount;
    private String openid;

}
