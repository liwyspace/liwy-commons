package com.liwy.commons.lang;

import org.junit.Assert;
import org.junit.Test;

import java.beans.Introspector;
import java.util.Map;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/21 14:05 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class EnumUtilsTest {
    @Test
    public void testEnum() {
        System.out.println(Week.MONDAY);
        System.out.println(Week.MONDAY.name());
        System.out.println(Week.MONDAY.getValue());
        System.out.println(Week.MONDAY.getName());
        System.out.println(Week.TUESDAY);
        System.out.println(Week.TUESDAY.name());
        System.out.println(Week.TUESDAY.getValue());
        System.out.println(Week.TUESDAY.getName());
    }

    @Test
    public void testGetEnumMap() {
        Map<String, Week> map = EnumUtils.getEnumMap(Week.class);
        Assert.assertTrue(Week.MONDAY.equals(map.get("MONDAY")));
    }
}

enum Week {
    MONDAY(1, "星期一"),
    TUESDAY(2, "星期二"),
    WEDNESDAY(3, "星期三"),
    THURSDAY(4, "星期四"),
    FRIDAY(5, "星期五"),
    SATURDAY(6, "星期六"),
    SUNDAY(7, "星期日");

    private Integer value;
    private String name;

    private Week(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}