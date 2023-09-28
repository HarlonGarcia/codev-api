package com.codev.utils.helpers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    @Override
    public void copyProperties(Object dest, Object orig) {
        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();

        try {
            notNull.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }
    }
}

