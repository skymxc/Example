package com.skymxc.example.dagger2.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * APP全局单例
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface APPScoped {
}
