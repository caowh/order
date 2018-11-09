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
    private int status;
    private long classify_id;
    private String picture_url;

    private int monthly_sale;
    private int praise_count;

}
