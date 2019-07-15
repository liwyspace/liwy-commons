package com.liwy.commons.lang;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>枚举工具的测试类</p>
 *
 * @author liwy
 */
public class EnumUtilsTest {

    /**
     * 枚举的基本信息
     */
    @Test
    public void testEnum() {
        assertThat(Week.MONDAY.name(), is("MONDAY"));
        assertThat(Week.MONDAY.getValue(), is(1));
        assertThat(Week.MONDAY.getName(), is("星期一"));

        assertThat(Week.TUESDAY.name(), is("TUESDAY"));
        assertThat(Week.TUESDAY.getValue(), is(2));
        assertThat(Week.TUESDAY.getName(), is("星期二"));
    }

    /**
     * 获取枚举字符串值和枚举对象的Map对应
     */
    @Test
    public void testGetEnumMap() {
        Map<String, Week> map = EnumUtils.getEnumMap(Week.class);
        assertThat(map.get("MONDAY"), is(Week.MONDAY));
    }

    /**
     * 获取枚举值列表
     */
    @Test
    public void testGetEnumList() {
        List<Week> enumList = EnumUtils.getEnumList(Week.class);
        assertThat(enumList, hasItems(Week.MONDAY, Week.TUESDAY, Week.WEDNESDAY, Week.THURSDAY, Week.FRIDAY, Week.SATURDAY, Week.SUNDAY));
    }

    /**
     * 通过enumName获取枚举值
     */
    @Test
    public void testGetEnum() {
        assertThat(EnumUtils.getEnum(Week.class, "MONDAY"), is(Week.MONDAY));
        assertThat(EnumUtils.getEnum(Week.class, "TUESDAY"), is(Week.TUESDAY));
        assertThat(EnumUtils.getEnum(Week.class, "WEDNESDAY"), is(Week.WEDNESDAY));
    }

    /**
     * 通过enumName获取枚举值, 忽略大小写
     */
    @Test
    public void testGetEnumIgnoreCase() {
        assertThat(EnumUtils.getEnumIgnoreCase(Week.class, "MONDAY"), is(Week.MONDAY));
        assertThat(EnumUtils.getEnumIgnoreCase(Week.class, "tuesday"), is(Week.TUESDAY));
        assertThat(EnumUtils.getEnumIgnoreCase(Week.class, "wEdNeSdAy"), is(Week.WEDNESDAY));
    }

    /**
     * 校验enumName是否是枚举中的值
     */
    @Test
    public void testIsValidEnum() {
        assertThat(EnumUtils.isValidEnum(Week.class, "MONDAY"), is(true));
        assertThat(EnumUtils.isValidEnum(Week.class, "TUESDAY"), is(true));
        assertThat(EnumUtils.isValidEnum(Week.class, "WEDNESDAY"), is(true));

        assertThat(EnumUtils.isValidEnum(Week.class, "ERROR"), is(false));
    }

    /**
     * 校验enumName是否是枚举中的值, 忽略大小写
     */
    @Test
    public void testIsValidEnumIgnoreCase() {
        assertThat(EnumUtils.isValidEnumIgnoreCase(Week.class, "MONDAY"), is(true));
        assertThat(EnumUtils.isValidEnumIgnoreCase(Week.class, "tuesday"), is(true));
        assertThat(EnumUtils.isValidEnumIgnoreCase(Week.class, "wEdNeSdAy"), is(true));

        assertThat(EnumUtils.isValidEnumIgnoreCase(Week.class, "ERROR"), is(false));
    }

    /**
     * 通过code获取枚举
     */
    @Test
    public void testGetEnumByCode() {
        System.out.println(EnumUtils.getEnumByCode(Week.class, "A"));
    }

    /**
     * 通过value获取枚举
     */
    @Test
    public void testGetEnumByValue() {
        System.out.println(EnumUtils.getEnumByValue(Week.class, 2));
    }

    /**
     * 通过name获取枚举
     */
    @Test
    public void testGetEnumByName() {
        System.out.println(EnumUtils.getEnumByName(Week.class, "星期一"));
    }

    private enum Week implements EnumUtils.ValueEnum<Integer>, EnumUtils.NameEnum<String>, EnumUtils.CodeEnum<String> {
        MONDAY(1, "星期一", "A"),
        TUESDAY(2, "星期二", "B"),
        WEDNESDAY(3, "星期三", "C"),
        THURSDAY(4, "星期四", "D"),
        FRIDAY(5, "星期五", "E"),
        SATURDAY(6, "星期六", "F"),
        SUNDAY(7, "星期日", "G");

        private Integer value;
        private String name;
        private String code;

        private Week(Integer value, String name, String code) {
            this.value = value;
            this.name = name;
            this.code = code;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getCode() {
            return code;
        }
    }
}

