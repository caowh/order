package cwh.order.producer.dao;

import cwh.order.producer.model.FoodTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/11/17 0017.
 */
@Repository
public interface FoodTableDao {

    List<FoodTable> queryAll(String openid);

    int updateName(FoodTable foodTable);

    int queryCount(String openid);

}
