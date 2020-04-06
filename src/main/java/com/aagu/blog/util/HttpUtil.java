package com.aagu.blog.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static Object createResponse(Integer code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        if (message != null) {
            response.put("message", message);
        }
        if (data instanceof List) {
            Map<String, Object> subMap = new HashMap<>();
            subMap.put("total", ((List) data).size());
            subMap.put("items", data);
            response.put("data", subMap);
        } else {
            response.put("data", data);
        }
        return response;
    }
}
