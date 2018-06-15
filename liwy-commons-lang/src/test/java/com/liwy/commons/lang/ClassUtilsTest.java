package com.liwy.commons.lang;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/15 10:32 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class ClassUtilsTest {
    @Test
    public void testClass() {
        String[] strArr = new String[]{"1","2"};
        Long longNumber = 120L;
        System.out.println(this.getClass().getName());
        System.out.println(strArr.getClass().getName());
        System.out.println(Map.Entry.class.getName());
        System.out.println(this.getClass().getSimpleName());
        System.out.println(strArr.getClass().getSimpleName());
        System.out.println(Map.Entry.class.getSimpleName());
        System.out.println(this.getClass().getPackage());
        System.out.println(strArr.getClass().getPackage());
        System.out.println(Map.Entry.class.getPackage());
        System.out.println(this.getClass().getComponentType());
        System.out.println(strArr.getClass().getComponentType());

        System.out.println("========= SupperClass ======");
        System.out.println(this.getClass().getSuperclass().getName());
        System.out.println(ArrayList.class.getSuperclass().getName());
        System.out.println(ArrayList.class.getInterfaces());
        System.out.println(ClassUtils.getAllSuperclasses(ArrayList.class));
        System.out.println(ClassUtils.getAllSuperclasses(ArrayList.class));

        System.out.println("==================Apache commons-lang");
        System.out.println(ClassUtils.getName(this));
        System.out.println(ClassUtils.getName(strArr));
        System.out.println(ClassUtils.getName(longNumber));
        System.out.println(ClassUtils.getSimpleName(this));
        System.out.println(ClassUtils.getSimpleName(strArr));
        System.out.println(ClassUtils.getSimpleName(longNumber));
        System.out.println(ClassUtils.getSimpleName(Map.Entry.class));
        System.out.println(ClassUtils.getShortClassName(this.getClass()));
        System.out.println(ClassUtils.getShortClassName(strArr.getClass()));
        System.out.println(ClassUtils.getShortClassName(longNumber.getClass()));
        System.out.println(ClassUtils.getShortClassName(Map.Entry.class));
        System.out.println(ClassUtils.getPackageName(this.getClass()));
        System.out.println(ClassUtils.getPackageName(strArr.getClass()));
        System.out.println(ClassUtils.getPackageName(Map.Entry.class));

        System.out.println("========== 原始类型与包装类型 =============");
        System.out.println(Boolean.TYPE);
        System.out.println(Long.class.isPrimitive());
        System.out.println(long.class.isPrimitive());

        System.out.println(boolean.class.getName());
        System.out.println(Boolean.class.getName());
    }

    /**
     * <b>描述：</b> 该ShortClassName与SimpleName的区别主要是内部类<br/>
     * <b>作者：</b>wenyao02.li<br/>
     * <b>版本：</b>V1.0 <br/>
     *
     * @param
     * @return void
     */
    @Test
    public void testGetShortClassName() {
        System.out.println(Map.Entry.class.getSimpleName());
        System.out.println(com.liwy.commons.lang.ClassUtils.getShortClassName(Map.Entry.class));
    }
}
