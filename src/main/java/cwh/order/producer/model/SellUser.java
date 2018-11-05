package cwh.order.producer.model;

import lombok.Data;

/**
 * Created by Administrator on 2018/11/4 0004.
 */
@Data
public class SellUser {

    private String openid;
    private String phone;
    private String store_name;
    private String region;
    private String address;
    private String description;
    private String headPicture_url;
    private int business;
    private int approval;
}
