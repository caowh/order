package cwh.order.producer.dao;

import cwh.order.producer.model.StorePicture;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/5.
 */
@Repository
public interface StorePictureDao {

    void insert(StorePicture storePicture);

    List<String> getStorePictureUrls(String openid);

    int getStorePictureCount(String openid);

    void delete(String pic_url);
}
