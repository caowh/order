package cwh.order.producer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created by 曹文豪 on 2018/11/5.
 */
@Data
public class StorePicture {

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String openid;
    private String pic_url;
}
