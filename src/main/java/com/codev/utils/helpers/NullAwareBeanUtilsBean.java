package com.codev.utils.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {
    public void copyProperties(
        Object dest,
        Object orig,
        String... ignoreProperties
    ) throws IllegalAccessException, InvocationTargetException {
        if (orig == null) {
            return;
        }

        PropertyUtilsBean propertyUtils = getPropertyUtils();
        
        try {
            for (String property : propertyUtils.describe(orig).keySet()) {
                try {
                    Object value = propertyUtils.getProperty(orig, property);
                    boolean hasToIgnore = Arrays.asList(ignoreProperties).contains(property);

                    if (value != null && !hasToIgnore) {
                        propertyUtils.setProperty(dest, property, value);
                    }
                } catch (NoSuchMethodException e) {
                    System.err.println("Method not found for the property: " + property);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Error while trying to access or invoke the property: " + property);
                    throw e;
                }
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}

