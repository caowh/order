package cwh.order.producer.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/27.
 */
@Repository
public interface EvaluatePictureDao {

    List<String> query(long id);

}
