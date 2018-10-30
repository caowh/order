package cwh.order.producer.service;

import cwh.order.producer.dao.FoodDao;
import cwh.order.producer.dao.FoodPictureDao;
import cwh.order.producer.model.Food;
import cwh.order.producer.model.FoodPicture;
import cwh.order.producer.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Service
public class FoodServiceImpl implements FoodService {

    @Resource
    private FoodDao foodDao;
    @Resource
    private FoodPictureDao foodPictureDao;

    public void add(HttpServletRequest request) {
        /*
          1、添加菜，初始数量为0
          2、存储图片，生成访问url，如果上传失败，允许菜添加成功，图片可以后续上传变更
          3、url生成成功后，根据food_id，建立菜和图片的关联关系；每上传成功一张图，建立一次关联关系，捕获异常互相不影响
          */
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Food food = new Food();
        food.setF_name(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setSurplusCount(0);
        foodDao.insert(food);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("foodFiles");
        long food_id = food.getId();
        for (MultipartFile file : files) {
            String url = FileUtil.save(file);
            FoodPicture foodPicture = new FoodPicture();
            foodPicture.setFood_id(food_id);
            foodPicture.setPic_url(url);
            foodPictureDao.insert(foodPicture);
        }
    }

}
