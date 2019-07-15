package com.liwy.commons.lang.math;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/7 16:58 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class MathUtilsTest {

    /**
     * <b>描述：</b> 测试浮点类型数据是否相等<br/>
     * <b>作者：</b>wenyao02.li<br/>
     * <b>版本：</b>V1.0 <br/>
     *
     * @param
     * @return void
     */
    @Test
    public void testFloatEqual() {
        double dNumber = 3.146d;
        float myNumber = 3.146f;
        double d = myNumber;
        System.out.println(dNumber);
        System.out.println(myNumber);
        System.out.println(d);
        System.out.println(Double.toString(dNumber));
        System.out.println(Float.toString(myNumber));
        System.out.println(Double.toString(d));
        Assert.assertFalse(myNumber==dNumber);
    }

    @Test
    public void test() {
        System.out.println(0.05);
        System.out.println(0.01);
        System.out.println(1.0);
        System.out.println(0.42);
        System.out.println(4.015);
        System.out.println(123.3);
        System.out.println(Double.toString(0.05));
        System.out.println(Double.toString(0.01));
        System.out.println(Double.toString(1.0));
        System.out.println(Double.toString(0.42));
        System.out.println(Double.toString(4.015));
        System.out.println(Double.toString(123.3));
        double a = 0.05;
        double b = 0.01;
        double c = 1.0;
        double d = 0.42;
        double e = 4.015;
        double f = 123.3;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
        System.out.println(Double.toString(a));
        System.out.println(Double.toString(b));
        System.out.println(Double.toString(c));
        System.out.println(Double.toString(d));
        System.out.println(Double.toString(e));
        System.out.println(Double.toString(f));

        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        System.out.println(new BigDecimal(Double.toString(4.015)).multiply(new BigDecimal(Double.toString(100))));
    }

    @Test
    public void test1() {
        double d = 301353.05123456789123456789;
        System.out.println(d);
        System.out.println(Double.toString(d));

        BigDecimal decimal = new BigDecimal(d);
        System.out.println(decimal);//301353.0499999999883584678173065185546875
        System.out.println(decimal.setScale(1, RoundingMode.HALF_UP));//301353.0

        System.out.println(new BigDecimal(Double.toString(d)));
        System.out.println(new BigDecimal(new Double(d).toString()));
        System.out.println(new BigDecimal("301353.05"));
        System.out.println(new BigDecimal("301353.895898895455898954895989"));
    }

    @Test
    public void test2() {
        System.out.println(Float.floatToIntBits(0.1f));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(0.1f)));
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(0.1f)));
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(0.1d)));
    }
}
