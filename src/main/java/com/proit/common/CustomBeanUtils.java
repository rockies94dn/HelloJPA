package com.proit.common;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

public class CustomBeanUtils {
	public static BeanUtilsBean instance() {
		BeanUtilsBean bean = new BeanUtilsBean(new ConvertUtilsBean() {

			@Override
			public Object convert(String value, Class clazz) {
				if (clazz.isEnum()) {
					return Enum.valueOf(clazz, value);
				}
				return super.convert(value, clazz);
			}
		});
		return bean;
	}
}
