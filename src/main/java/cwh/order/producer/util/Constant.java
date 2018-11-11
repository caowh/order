package cwh.order.producer.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
public class Constant {

    public static final int CODE_OK = 0;

    public static final int CODE_UNLOGIN = -1;

    public static final int CODE_ERROR = -2;

    public static final int NO_PHONE = -3;

    public static final int NO_APPROVAL = -4;

    public static final String FILEPATH = "/usr/share/nginx/html";

    public static final String APPID = "wxbdba0216200dee6a";

    public static final String APPSECRET = "67f20433a972c589ace16d6968eff071";

    public static final long TIMEOUT = 30;  //min

    public static final String separator = "%%";

    public static final String phoneKey = "phoneKey";

    public static final String ERROR = "网络异常，稍后重试";


    public static String getSafeParameter(HttpServletRequest request, String arg) {
        String param = request.getParameter(arg);
        return param == null ? "" : param.replaceAll("\"", "“").
                replaceAll("'", "‘").trim();
    }

}
