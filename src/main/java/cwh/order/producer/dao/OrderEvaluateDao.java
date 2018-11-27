package cwh.order.producer.dao;

import cwh.order.producer.model.OrderEvaluate;
import org.springframework.stereotype.Repository;

/**
 * Created by 曹文豪 on 2018/11/23.
 */
@Repository
public interface OrderEvaluateDao {

    OrderEvaluate query(long id);
}
