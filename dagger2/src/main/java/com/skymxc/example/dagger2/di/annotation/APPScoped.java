package com.skymxc.example.dagger2.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * APP全局单例
 * 此注解使用的 Component 要全局范围内唯一 ，不然无法实现全局单例
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface APPScoped {
}
