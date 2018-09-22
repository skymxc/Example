package com.skymxc.example.dagger2.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * activity 内单例
 * 使用 此注解的Component 生命周期要跟随 Activity 的生命周期。
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
