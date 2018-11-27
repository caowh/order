package cwh.order.producer.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/23.
 */
@Data
public class OrderEvaluate {

    private long order_id;
    private String message;
    private int evaluate_type;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    private List<String> pictures;
    private List<FoodSale> foodSales;

}
