package com.jason.common.result;

import java.util.HashMap;
import java.util.List;

/**
 * @author: xieyong
 * @date: 2018/8/30 13:42
 * @Description: layui
 */
public class Layui extends HashMap<String, Object> {

    public static Layui data(Integer count,List<?> data){
        Layui r = new Layui();
        r.put("code", 0);
        r.put("msg", "");
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}
