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

    void updateRegion(SellUser sellUser);

    void updateAddress(SellUser sellUser);

    void updateStoreName(SellUser sellUser);

    void updateDescription(SellUser sellUser);

    void updateHeadPictureUrl(SellUser sellUser);

    int queryNameCountByRegion(SellUser sellUser);

    String getRegion(String openid);

    String getAddress(String openid);

    String getHeadPictureUrl(String openid);

    String getStoreName(String openid);

    String getDescription(String openid);

    SellUser getStore(String openid);

    int getApproval(String openid);

}
