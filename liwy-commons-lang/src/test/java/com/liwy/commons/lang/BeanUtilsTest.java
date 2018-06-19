package com.liwy.commons.lang;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/19 10:47 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class BeanUtilsTest {
    @Test
    public void test() throws IntrospectionException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBersday(new Date());
        Map map = new HashMap();
        map.put("name","lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        BeanInfo beanInfo = Introspector.getBeanInfo(testBean.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            System.out.println(descriptor.getName());
            System.out.println(descriptor.getReadMethod());
            System.out.println(descriptor.getWriteMethod());
        }

        System.out.println("反射获取字段：");
        Field[] declaredFields = testBean.getClass().getDeclaredFields();
        for(Field field:declaredFields) {
            System.out.println(field.getName());
        }

        Method[] declaredMethods = testBean.getClass().getDeclaredMethods();
        for(Method method:declaredMethods) {
            System.out.println(method.getName());
        }
    }

    @Test
    public void testBeanToMap() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBersday(new Date());
        Map map = new HashMap();
        map.put("name","lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        Map<String, Object> beanMap = BeanUtils.beanToMap(testBean);
        System.out.println(beanMap);
    }

    @Test
    public void testMapToBean() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TestBean testBean = new TestBean();
        testBean.setName("liwy");
        testBean.setAge(22);
        testBean.setBersday(new Date());
        Map map = new HashMap();
        map.put("name","lny");
        testBean.setMap(map);
        SubBean subBean = new SubBean();
        subBean.setName("subName");
        testBean.setSubBean(subBean);

        Map<String, Object> beanMap = BeanUtils.beanToMap(testBean);
        System.out.println(beanMap);

        beanMap.put("name", "LIWY22");
        subBean.setAge(99);
        beanMap.put("subBean", subBean);
        beanMap.put("flag", true);
        beanMap.put("xxx", "xxx");
        BeanUtils.mapToBean(testBean, beanMap);
        System.out.println(testBean);
    }
}

class SupTestBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TestBean extends SupTestBean {
    private Date bersday;
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

    public Date getBersday() {
        return bersday;
    }

    public void setBersday(Date bersday) {
        this.bersday = bersday;
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
                ", bersday=" + bersday +
                ", age=" + age +
                ", map=" + map +
                ", subBean=" + subBean +
                ", sunNames=" + sunNames +
                ", flag=" + flag +
                '}';
    }
}

class SubBean {
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