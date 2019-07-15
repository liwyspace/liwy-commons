package com.liwy.commons.lang;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>Object工具的测试类</p>
 *
 * @author liwy
 */
public class ObjectUtilsTest {
    /**
     * 测试Object基本信息
     *
     * @throws IntrospectionException
     */
    @Test
    public void test() throws IntrospectionException {
        // 构建一个bean
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBirthday(new Date());
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        // 获取bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(testBean.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

        List<String> propertyNameList = Arrays.stream(descriptors).map(PropertyDescriptor::getName).collect(Collectors.toList());
        List<String> getNameList = Arrays.stream(descriptors).map(propertyDescriptor -> propertyDescriptor.getReadMethod().getName()).collect(Collectors.toList());
        List<String> setNameList = Arrays.stream(descriptors).map(propertyDescriptor -> {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            return writeMethod == null ? null : writeMethod.getName();
        }).collect(Collectors.toList());

        assertThat(propertyNameList, hasItems("age", "birthday", "class", "flag", "map", "name", "subBean", "sunNames"));
        assertThat(getNameList, hasItems("getAge", "getBirthday", "getClass", "getFlag", "getMap", "getName", "getSubBean", "getSunNames"));
        assertThat(setNameList, hasItems("setAge", "setBirthday", null, "setFlag", "setMap", "setName", "setSubBean", "setSunNames"));

        // 反射获取字段方法信息
        Field[] declaredFields = testBean.getClass().getDeclaredFields();
        Method[] declaredMethods = testBean.getClass().getDeclaredMethods();

        List<String> fieldNameList = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList());
        List<String> methodNameList = Arrays.stream(declaredMethods).map(Method::getName).collect(Collectors.toList());

        assertThat(fieldNameList, allOf(hasItems("age", "birthday", "flag", "map", "subBean", "sunNames", "this$0"), not(hasItems("class", "name"))));
        assertThat(methodNameList,
                allOf(hasItems("toString", "getAge", "getBirthday", "getFlag", "getMap", "getSubBean", "getSunNames", "setAge", "setBirthday", "setFlag", "setMap", "setSubBean", "setSunNames"),
                        not(hasItems("getClass", "getName", "setName"))));
    }

    /**
     * 如果为null，返回默认值
     */
    @Test
    public void testDefaultIfNull() {
        assertThat(ObjectUtils.defaultIfNull(null, null), nullValue());
        assertThat(ObjectUtils.defaultIfNull(null, ""), is(""));
        assertThat(ObjectUtils.defaultIfNull(null, "zz"), is("zz"));
        assertThat(ObjectUtils.defaultIfNull("abc", "liwy"), is("abc"));
        assertThat(ObjectUtils.defaultIfNull(Boolean.TRUE, Boolean.FALSE), is(Boolean.TRUE));
    }

    /**
     * 比较对象大小
     */
    @Test
    public void testCompare() {
        Integer a = new Integer(5);
        Integer b = new Integer(8);
        Integer c = new Integer(8);
        assertThat(ObjectUtils.compare(a, b), is(-1));
        assertThat(ObjectUtils.compare(b, a), is(1));
        assertThat(ObjectUtils.compare(b, c), is(0));
        assertThat(ObjectUtils.compare(null, b), is(-1));
        assertThat(ObjectUtils.compare(b, null), is(1));
    }

    /**
     * 比较对象大小,null值为最大
     */
    @Test
    public void testCompare_nullGreater() {
        Integer a = new Integer(5);
        Integer b = new Integer(8);
        Integer c = new Integer(8);
        assertThat(ObjectUtils.compare(a, b, false), is(-1));
        assertThat(ObjectUtils.compare(b, a, false), is(1));
        assertThat(ObjectUtils.compare(b, c, false), is(0));
        assertThat(ObjectUtils.compare(null, b, false), is(-1));
        assertThat(ObjectUtils.compare(b, null, false), is(1));

        assertThat(ObjectUtils.compare(a, b, true), is(-1));
        assertThat(ObjectUtils.compare(b, a, true), is(1));
        assertThat(ObjectUtils.compare(b, c, true), is(0));
        assertThat(ObjectUtils.compare(null, b, true), is(1));
        assertThat(ObjectUtils.compare(b, null, true), is(-1));
    }

    /**
     * 获取最小的对象
     */
    @Test
    public void testMin() {
        Integer a = new Integer(1);
        Integer b = new Integer(2);
        Integer c = new Integer(5);
        assertThat(ObjectUtils.min(a, b, c), is(a));
    }

    /**
     * 获取最大的对象
     */
    @Test
    public void testMax() {
        Integer a = new Integer(1);
        Integer b = new Integer(2);
        Integer c = new Integer(5);
        assertThat(ObjectUtils.max(a, b, c), is(c));
    }

    /**
     * Map转Bean类型
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    @Test
    public void testMapToBean() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, Object> beanMap = new HashMap<>();
        beanMap.put("name", "liwy");
        beanMap.put("age", 22);
        Date birthday = new Date();
        beanMap.put("birthday", birthday);
        Map map = new HashMap();
        map.put("name", "lny");
        beanMap.put("map", map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        beanMap.put("subBean", subBean);
        beanMap.put("flag", true);
        beanMap.put("xxx", "xxx");

        TestBean testBean = new TestBean();
        testBean.setName("LIWY2");
        ObjectUtils.mapToBean(testBean, beanMap);

        assertThat(testBean.getName(), is("liwy"));
        assertThat(testBean.getAge(), is(22));
        assertThat(testBean.getBirthday(), is(birthday));
        assertThat(testBean.getMap(), is(map));
        assertThat(testBean.getSubBean(), is(subBean));
        assertThat(testBean.getFlag(), is(true));
    }

    /**
     * 对象转Map
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    @Test
    public void testBeanToMap() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        Date birthday = new Date();
        testBean.setBirthday(birthday);
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        Map<String, Object> beanMap = ObjectUtils.beanToMap(testBean);
        assertThat(beanMap.get("birthday"), is(birthday));
        assertThat(beanMap.get("sunNames"), nullValue());
        assertThat(beanMap.get("flg"), nullValue());
        assertThat(beanMap.get("name"), is("liwy"));
        assertThat(beanMap.get("age"), is(22));
        assertThat(beanMap.get("subBean"), is(subBean));
        assertThat(beanMap.get("class"), is((Object) testBean.getClass()));
        assertThat(beanMap.get("map"), is(map));
    }

    /**
     * 通过Get方法获取属性的值
     */
    @Test
    public void testGetSimpleProperty() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        Date birthday = new Date();
        testBean.setBirthday(birthday);
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        assertThat(ObjectUtils.getSimpleProperty(testBean, "name"), is("liwy"));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "age"), is(22));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "map"), is(map));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "subBean"), is(subBean));
    }

    /**
     * 通过Set方法设置属性的值
     */
    @Test
    public void testSetSimpleProperty() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        Date birthday = new Date();
        testBean.setBirthday(birthday);
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        ObjectUtils.setSimpleProperty(testBean, "name", "LIWY", true);
        ObjectUtils.setSimpleProperty(testBean, "age", 32, true);
        Map map1 = new HashMap();
        map.put("first", 1);
        ObjectUtils.setSimpleProperty(testBean, "map", map1, true);
        SubBean subBean1 = new SubBean();
        subBean1.setName("subName1");
        ObjectUtils.setSimpleProperty(testBean, "subBean", subBean1, true);

        assertThat(ObjectUtils.getSimpleProperty(testBean, "name"), is("LIWY"));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "age"), is(32));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "map"), is(map1));
        assertThat(ObjectUtils.getSimpleProperty(testBean, "subBean"), is(subBean1));
    }

    /**
     * 获取bean的所有有效属性描述
     */
    @Test
    public void testGetPropertyDescriptors() {
        // 构建一个bean
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBirthday(new Date());
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        // 获取bean信息
        PropertyDescriptor[] descriptors = ObjectUtils.getPropertyDescriptors(testBean);

        List<String> propertyNameList = Arrays.stream(descriptors).map(PropertyDescriptor::getName).collect(Collectors.toList());
        List<String> getNameList = Arrays.stream(descriptors).map(propertyDescriptor -> propertyDescriptor.getReadMethod().getName()).collect(Collectors.toList());
        List<String> setNameList = Arrays.stream(descriptors).map(propertyDescriptor -> {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            return writeMethod == null ? null : writeMethod.getName();
        }).collect(Collectors.toList());

        assertThat(propertyNameList, hasItems("age", "birthday", "class", "flag", "map", "name", "subBean", "sunNames"));
        assertThat(getNameList, hasItems("getAge", "getBirthday", "getClass", "getFlag", "getMap", "getName", "getSubBean", "getSunNames"));
        assertThat(setNameList, hasItems("setAge", "setBirthday", null, "setFlag", "setMap", "setName", "setSubBean", "setSunNames"));
    }

    /**
     * 获取bean的指定有效属性描述
     */
    @Test
    public void testGetPropertyDescriptor() {
        // 构建一个bean
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBirthday(new Date());
        Map map = new HashMap();
        map.put("name", "lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        // 获取bean信息
        PropertyDescriptor descriptor = ObjectUtils.getPropertyDescriptor(testBean, "name");

        assertThat(descriptor.getName(), is("name"));
        assertThat(descriptor.getReadMethod().getName(), is("getName"));
        assertThat(descriptor.getWriteMethod().getName(), is("setName"));
    }

    /**
     * 测试使用父类
     */
    private class SupTestBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 测试使用类
     */
    private class TestBean extends SupTestBean {
        private Date birthday;
        private Integer age;
        private Map map;
        private SubBean subBean;

        private List sunNames;
        private Boolean flag;

        public List getSunNames() {
            return sunNames;
        }

        public Object getSunNames(int index) {
            return sunNames.get(index);
        }

        public void setSunNames(List sunNames) {
            this.sunNames = sunNames;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public SubBean getSubBean() {
            return subBean;
        }

        public void setSubBean(SubBean subBean) {
            this.subBean = subBean;
        }

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(Boolean flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "name=" + this.getName() +
                    ", birthday=" + birthday +
                    ", age=" + age +
                    ", map=" + map +
                    ", subBean=" + subBean +
                    ", sunNames=" + sunNames +
                    ", flag=" + flag +
                    '}';
        }
    }

    /**
     * 测试使用子类
     */
    private class SubBean {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "SubBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}