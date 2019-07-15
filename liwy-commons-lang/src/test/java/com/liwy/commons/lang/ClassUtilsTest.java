package com.liwy.commons.lang;

import com.liwy.commons.lang.collection.ArrayUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>Class工具的测试类</p>
 *
 * @author liwy
 */
public class ClassUtilsTest {

    /**
     * Class基本操作
     */
    @Test
    public void testClass() {
        Class<String> strClass = String.class;
        Class<String[]> arrClass = String[].class;
        Class<Map.Entry> entryClass = Map.Entry.class;

        // 获取类名
        assertThat(strClass.getName(), is("java.lang.String"));
        assertThat(arrClass.getName(), is("[Ljava.lang.String;"));
        assertThat(entryClass.getName(), is("java.util.Map$Entry"));

        // 获取简单类名
        assertThat(strClass.getSimpleName(), is("String"));
        assertThat(arrClass.getSimpleName(), is("String[]"));
        assertThat(entryClass.getSimpleName(), is("Entry"));

        // 获取包名
        assertThat(strClass.getPackage().getName(), is("java.lang"));
        assertThat(arrClass.getPackage(), nullValue());
        assertThat(entryClass.getPackage().getName(), is("java.util"));

        // 获取数组中元素的类型
        assertThat(strClass.getComponentType(), nullValue());
        assertThat(arrClass.getComponentType().getName(), is("java.lang.String"));
        assertThat(entryClass.getComponentType(), nullValue());

        // 获取所有的父类
        assertThat(strClass.getSuperclass().getName(), is("java.lang.Object"));
        assertThat(arrClass.getSuperclass().getName(), is("java.lang.Object"));
        assertThat(entryClass.getSuperclass(), nullValue());
        assertThat(ArrayList.class.getSuperclass().getName(), is("java.util.AbstractList"));

        // 获取所有的接口
        assertThat(Arrays.stream(strClass.getInterfaces()).map(cls -> cls.getName()).collect(Collectors.toList()),
                hasItems("java.io.Serializable", "java.lang.Comparable", "java.lang.CharSequence"));
        assertThat(Arrays.stream(arrClass.getInterfaces()).map(cls -> cls.getName()).collect(Collectors.toList()),
                hasItems("java.lang.Cloneable", "java.io.Serializable"));
        assertThat(entryClass.getInterfaces().length, is(0));

        // 原始类型与包装类型
        assertThat(Boolean.TYPE.getName(), is("boolean"));
        assertThat(Long.class.isPrimitive(), is(false));
        assertThat(long.class.isPrimitive(), is(true));
        assertThat(boolean.class.getName(), is("boolean"));
        assertThat(Boolean.class.getName(), is("java.lang.Boolean"));
    }

    /**
     * 通过Class获取简短类名
     */
    @Test
    public void testGetShortClassName_class() {
        assertThat(Map.Entry.class.getSimpleName(), is("Entry"));
        assertThat(ClassUtils.getShortClassName(Map.Entry.class), is("Map.Entry"));
    }

    /**
     * 通过Class获取简短类名
     */
    @Test
    public void testGetShortClassName_className() {
        assertThat(ClassUtils.getShortClassName("com.liwy.oscafe.Map.Entry"), is("Entry"));
    }

    /**
     * 获取所有的父类
     */
    @Test
    public void testGetAllSuperclasses() {
        assertThat(ClassUtils.getAllSuperclasses(ArrayList.class).stream().map(Class::getName).collect(Collectors.toList())
                , hasItems("java.util.AbstractList", "java.util.AbstractCollection", "java.lang.Object"));
    }

    /**
     * 获取所有的接口
     */
    @Test
    public void testGetAllInterfaces() {
        assertThat(ClassUtils.getAllInterfaces(ArrayList.class).stream().map(Class::getName).collect(Collectors.toList())
                , hasItems("java.util.List", "java.util.Collection", "java.lang.Iterable",
                        "java.util.RandomAccess", "java.lang.Cloneable", "java.io.Serializable"));
    }

    /**
     * 将className列表转换为Class列表
     */
    @Test
    public void testConvertClassNamesToClasses() {
        List<Class<?>> classes = ClassUtils.convertClassNamesToClasses(ArrayUtils.toList("java.lang.String", "java.lang.Boolean"));
        assertThat(classes.stream().map(Class::getSimpleName).collect(Collectors.toList()), hasItems("String", "Boolean"));
    }

    /**
     * 将{@code Class} 对象列表转换为ClassNames列表
     */
    @Test
    public void testConvertClassesToClassNames() {
        List<String> strings = ClassUtils.convertClassesToClassNames(ArrayUtils.toList(String.class, Boolean.class));
        assertThat(strings, hasItems("java.lang.String", "java.lang.Boolean"));
    }

    /**
     * 判断是否为基本类型或包装类
     */
    @Test
    public void testIsPrimitiveOrWrapper() {
        assertThat(ClassUtils.isPrimitiveOrWrapper(Boolean.class), is(true));
        assertThat(ClassUtils.isPrimitiveOrWrapper(boolean.class), is(true));
        assertThat(ClassUtils.isPrimitiveOrWrapper(String.class), is(false));
    }

    /**
     * 判断是否为基本类型的包装类
     */
    @Test
    public void testIsPrimitiveWrapper() {
        assertThat(ClassUtils.isPrimitiveWrapper(Boolean.class), is(true));
        assertThat(ClassUtils.isPrimitiveWrapper(boolean.class), is(false));
        assertThat(ClassUtils.isPrimitiveWrapper(String.class), is(false));
    }

    /**
     * 基本类型转包装类型
     */
    @Test
    public void testPrimitiveToWrapper() {
        assertThat(ClassUtils.primitiveToWrapper(boolean.class).getName(), is("java.lang.Boolean"));
        assertThat(ClassUtils.primitiveToWrapper(Boolean.class).getName(), is("java.lang.Boolean"));
        assertThat(ClassUtils.primitiveToWrapper(null), nullValue());
    }

    /**
     * 包装类型转基本类型
     */
    @Test
    public void testWrapperToPrimitive() {
        assertThat(ClassUtils.wrapperToPrimitive(boolean.class), nullValue());
        assertThat(ClassUtils.wrapperToPrimitive(Boolean.class).getName(), is("boolean"));
        assertThat(ClassUtils.wrapperToPrimitive(null), nullValue());
    }

    /**
     * 判断该类是否为内部类或匿名内部类
     */
    @Test
    public void testIsInnerClass() {
        assertThat(ClassUtils.isInnerClass(Map.class), is(false));
        assertThat(ClassUtils.isInnerClass(Map.Entry.class), is(true));
    }

    /**
     * 加载类并初始化
     */
    @Test
    public void testGetClass() throws ClassNotFoundException {
        Class<?> strClass = ClassUtils.getClass("java.lang.String");
        assertThat(strClass.getSimpleName(), is("String"));

        Class<?> aClass = ClassUtils.getClass(this.getClass().getClassLoader(), "java.lang.String", true);
        assertThat(aClass.getSimpleName(), is("String"));
    }

}
