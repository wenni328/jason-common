package com.jason.common.bean;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xieyong
 * @date: 2018/8/30 13:53
 * @Description: 只是支持相同字段互转，一般是用在rpc抓换
 */
public class BeanConverter {
    /**
     * 转换对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convertObj(Object source, Class<T> targetClass) {
        if(source==null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanCopier copier=BeanCopier.create(source.getClass(),targetClass,false);
            copier.copy(source,target,null);
            return target;
        } catch (InstantiationException| IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换ArrayList
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertArrayList(List sourceList, Class<T> targetClass) {
        ArrayList<T> targetList=new ArrayList<>();
        if(sourceList==null) {
            return targetList;
        }
        for(Object obj:sourceList) {
            targetList.add(convertObj(obj,targetClass));
        }
        return targetList;
    }
}
