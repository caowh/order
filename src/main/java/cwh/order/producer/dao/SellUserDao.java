package cwh.order.producer.dao;

import cwh.order.producer.model.SellUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/11/4 0004.
 */
@Repository
public interface SellUserDao {

    void insert(SellUser sellUser);

    String queryPhone(String openid);

    void updatePhone(SellUser sellUser);

    void updateStore(SellUser sellUser);

    int queryNameCountByRegion(SellUser sellUser);
}
