package cwh.order.producer.util;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 曹文豪 on 2018/10/31.
 */
public class UserHelperUtil {

    public static long getUserId(HttpServletRequest request) {
        return 10000;
//        return (long) WebUtils.getRequiredSessionAttribute(request, Constant.USER_SESSION_NAME);
    }
}
