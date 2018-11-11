package cwh.order.producer.dao;

import cwh.order.producer.model.DAAN;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/11/11 0011.
 */
@Repository
public interface DAANDao {

    void insert(DAAN daan);
}
