package cwh.order.producer.dao;

import cwh.order.producer.model.SellUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/11/4 0004.
 */
@Repository
public interface UserDao {

    void insert(SellUser sellUser);

    SellUser query(String openid);

    void updatePhone(SellUser sellUser);
}
