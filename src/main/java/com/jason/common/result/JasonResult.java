package com.jason.common.result;

import java.util.HashMap;

/**
 * @author: xieyong
 * @date: 2018/8/29 15:54
 * @Description:
 */
public class JasonResult extends HashMap<String, Object> {

    public JasonResult() {
        put("code", 200);
        put("msg", "操作成功");
    }

    public static JasonResult error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static JasonResult error(String msg) {
        return error(500, msg);
    }

    public static JasonResult error(int code, String msg) {
        JasonResult r = new JasonResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static JasonResult ok(String msg) {
        JasonResult r = new JasonResult();
        r.put("msg", msg);
        return r;
    }

    public static JasonResult ok(String msg, Object o) {
        JasonResult r = new JasonResult();
        r.put("msg", msg);
        r.put("data", o);
        return r;
    }

    public static JasonResult ok(Object o) {
        JasonResult r = new JasonResult();
        r.put("msg", "操作成功");
        r.put("data", o);
        return r;
    }

    public static JasonResult ok() {
        return new JasonResult();
    }

    @Override
    public JasonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
