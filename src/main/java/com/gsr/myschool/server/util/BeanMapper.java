package com.gsr.myschool.server.util;

import org.apache.commons.beanutils.BeanMap;

import java.util.Map;

public class BeanMapper {

    public static Map beanToMap(Object object) {
        BeanMap beanMap = new BeanMap(object);
        return beanMap;
    }
}
