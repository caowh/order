package cwh.order.producer.dao;

import cwh.order.producer.model.FoodSale;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/21.
 */
@Repository
public interface FoodSaleDao {

    List<FoodSale> queryByOrder(long id);

}
