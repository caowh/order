package cwh.order.producer.util;

import lombok.Data;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/12.
 */
@Data
public class PageQuery {

    private long long_param;
    private String string_param;
    private String string_param1;
    private int int_param;
    private List<Long> longList;
    private int start;
    private int count;
}
