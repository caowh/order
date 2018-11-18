package cwh.order.producer.dao;

import cwh.order.producer.model.FoodDelete;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/11/17 0017.
 */
@Repository
public interface FoodDeleteDao {

    void insert(FoodDelete foodDelete);
}
