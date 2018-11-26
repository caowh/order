package cwh.order.producer.dao;

import cwh.order.producer.model.FoodOrder;
import cwh.order.producer.util.PageQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/21.
 */
@Repository
public interface FoodOrderDao {


    List<FoodOrder> query(PageQuery pageQuery);

    FoodOrder queryDetail(FoodOrder foodOrder);

}
