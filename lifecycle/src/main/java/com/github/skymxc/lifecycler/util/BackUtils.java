package com.github.skymxc.lifecycler.util;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2019/12/2  9:40
 */
public class BackUtils {

    public void back() {
        int age = 12;
        String str = "";
        if (age > 60) {
            str = "老年人";
        } else if (age > 18) {
            str = "成年人";
        } else {
            str = "未成年人";
        }
    }


}
