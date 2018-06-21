package com.liwy.commons.lang;

import com.liwy.commons.lang.collection.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>常用反射工具类</p>
 *
 * <ul>
 *  <li><b>getDeclaredField</b>
 *      - 得到当前类的一个可访问的Field</li>
 *  <li><b>getField</b>
 *      - 得到当前类及父类的一个可访问的Field</li>
 *  <li><b>getAllFields</b>
 *      - 得到当前类及父类的所有可访问的Field</li>
 *  <li><b>getFieldsWithAnnotation</b>
 *      - 得到当前类及父类的所有带指定注解的Field</li>
 *  <li><b>getFieldValue</b>
 *      - 获取指定字段的值</li>
 *  <li><b>setFieldValue</b>
 *      - 设置指定字段的值</li>
 *  <li><b>removeFinalModifier</b>
 *      - 删除字段的Final修饰符</li>
 *  <li><b>getAccessibleConstructor</b>
 *      - 获取一个可访问的构造器</li>
 *  <li><b>invokeConstructor</b>
 *      - 执行class的构造器</li>
 *  <li><b>getAccessibleMethod</b>
 *      - 获取可访问的方法</li>
 *  <li><b>getMethodsWithAnnotation</b>
 *      - 获取带有指定注解的方法</li>
 *  <li><b>invokeMethod</b>
 *      - 执行指定注解</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class ReflectUtils {
	private static final int ACCESS_TEST = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE;
    /**
     * 判断Member是否可访问，即是否存在public修饰符
     *
     * @param m
     * @return boolean
     */
    private static boolean isAccessible(final Member m) {
        return m != null && Modifier.isPublic(m.getModifiers()) && !m.isSynthetic();
    }
    
    /**
     * 判断Class是否可访问的
     *
     * @param type
     * @return boolean
     */
    private static boolean isAccessible(final Class<?> type) {
        Class<?> cls = type;
        while (cls != null) {
            if (!Modifier.isPublic(cls.getModifiers())) {
                return false;
            }
            // 获取它的外部类
            cls = cls.getEnclosingClass();
        }
        return true;
    }
    
    /**
     * 判断modifiers是否为包访问范围
     *
     * @param modifiers
     * @return boolean
     */
    private static boolean isPackageAccess(final int modifiers) {
        return (modifiers & ACCESS_TEST) == 0;
    }

    /**
     * 将成员是public范围、成员所在类是默认范围的成员设置为反射可访问
     *
     * @param o
     * @return boolean
     */
    private static boolean setAccessibleWorkaround(final AccessibleObject o) {
        if (o == null || o.isAccessible()) {
            return false;
        }
        final Member m = (Member) o;
        // 将反射不可访问的、成员是public范围、成员所在类是默认范围 设置为反射可访问
        if (!o.isAccessible() && Modifier.isPublic(m.getModifiers()) && isPackageAccess(m.getDeclaringClass().getModifiers())) {
            try {
                o.setAccessible(true);
                return true;
            } catch (final SecurityException e) {
            }
        }
        return false;
    }

	/**
	 * 通过名字得到一个可访问的 {@link Field}, 根据需要可以突破访问范围. 只考虑这个指定的类
	 *
	 * @param cls
	 * @param fieldName
	 * @param forceAccess
	 * @return java.lang.reflect.Field
	 */
    public static Field getDeclaredField(final Class<?> cls, final String fieldName, final boolean forceAccess) {
        if(cls==null) {
        	throw new IllegalArgumentException(String.format("The class must not be null"));
        }
        if(StringUtils.isBlank(fieldName)) {
        	throw new IllegalArgumentException(String.format("The field name must not be blank/empty"));
        }
        
        try {
            final Field field = cls.getDeclaredField(fieldName);
            if (!isAccessible(field)) {
                if (forceAccess) {
                    field.setAccessible(true);
                } else {
                    return null;
                }
            }
            return field;
        } catch (final NoSuchFieldException e) {
        }
        return null;
    }
    
    /**
     * 通过名字获取一个可访问 {@link Field} , 根据请求是否突破访问范围. 包括Superclasses和interfaces
     *
     * @param cls
     * @param fieldName
     * @param forceAccess
     * @return java.lang.reflect.Field
     */
    public static Field getField(final Class<?> cls, final String fieldName, final boolean forceAccess) {
        if(cls==null) {
        	throw new IllegalArgumentException(String.format("The class must not be null"));
        }
        if(StringUtils.isBlank(fieldName)) {
        	throw new IllegalArgumentException(String.format("The field name must not be blank/empty"));
        }
        for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
            try {
                final Field field = acls.getDeclaredField(fieldName);
                if (!Modifier.isPublic(field.getModifiers())) {
                    if (forceAccess) {
                        field.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                return field;
            } catch (final NoSuchFieldException ex) {
            }
        }

        Field match = null;
        for (final Class<?> class1 : ClassUtils.getAllInterfaces(cls)) {
            try {
                final Field test = class1.getField(fieldName);
                if (match != null) {
                    throw new IllegalArgumentException(String.format("Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", fieldName, cls));
                }
                match = test;
            } catch (final NoSuchFieldException ex) {
            }
        }
        return match;
    }
    
    /**
     * 获取当前类及其父类的所有的 fields
     *
     * @param cls
     * @return java.util.List<java.lang.reflect.Field>
     */
    public static List<Field> getAllFields(final Class<?> cls) {
        if(cls==null) {
        	throw new IllegalArgumentException(String.format("The class must not be null"));
        }
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }
    
    /**
     * 获取当前类及其父类的所有的带有特定注解的 fields
     *
     * @param cls
     * @param annotationCls
     * @return java.util.List<java.lang.reflect.Field>
     */
    public static List<Field> getFieldsWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
        if(annotationCls==null) {
        	throw new IllegalArgumentException(String.format("The annotation class must not be null"));
        }
        final List<Field> allFields = getAllFields(cls);
        final List<Field> annotatedFields = new ArrayList<Field>();
        for (final Field field : allFields) {
            if (field.getAnnotation(annotationCls) != null) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }
    
    
    /**
     * 获取指定对象的指定字段的值，可突破访问范围，指定对象为null时查询的是静态字段
     *
     * @param field
     * @param target
     * @param forceAccess
     * @return java.lang.Object
     */
    public static Object getFieldValue(final Field field, final Object target, final boolean forceAccess) throws IllegalAccessException {
        if(field==null) {
        	throw new IllegalArgumentException(String.format("The field must not be null"));
        }

        if(target == null && !Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException(String.format("The field '%s' is not static",field.getName()));
        }

        if (forceAccess && !field.isAccessible()) {
            field.setAccessible(true);
        } else {
            setAccessibleWorkaround(field);
        }
        return field.get(target);
    }
    
    /**
     * 设置指定对象的指定字段的值，可突破访问范围，指定对象为null时查询的是静态字段
     *
     * @param field
     * @param target
     * @param value
     * @param forceAccess
     * @return void
     */
    public static void setFieldValue(final Field field, final Object target, final Object value, final boolean forceAccess)
            throws IllegalAccessException {
        if(field==null) {
        	throw new IllegalArgumentException(String.format("The field must not be null"));
        }

        if(target == null && !Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException(String.format("The field %s.%s is not static",field.getDeclaringClass().getName(),
                    field.getName()));
        }

        if (forceAccess && !field.isAccessible()) {
            field.setAccessible(true);
        } else {
            setAccessibleWorkaround(field);
        }
        field.set(target, value);
    }
    
    /**
     * 删除字段的Final修饰符
     *
     * @param field
     * @param forceAccess
     * @return void
     */
    public static void removeFinalModifier(final Field field, final boolean forceAccess) {
        if(field==null) {
        	throw new IllegalArgumentException(String.format("The field must not be null"));
        }

        try {
            if (Modifier.isFinal(field.getModifiers())) {
                // Do all JREs implement Field with a private ivar called "modifiers"?
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                final boolean doForceAccess = forceAccess && !modifiersField.isAccessible();
                if (doForceAccess) {
                    modifiersField.setAccessible(true);
                }
                try {
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                } finally {
                    if (doForceAccess) {
                        modifiersField.setAccessible(false);
                    }
                }
            }
        } catch (final NoSuchFieldException ignored) {
        } catch (final IllegalAccessException ignored) {
        }
    }

    /**
     * 获取一个可访问的构造器
     *
     * @param cls
     * @param parameterTypes
     * @return java.lang.reflect.Constructor<T>
     */
    public static <T> Constructor<T> getAccessibleConstructor(final Class<T> cls,
            final Class<?>... parameterTypes) {
        if(cls==null) {
        	throw new IllegalArgumentException(String.format("class cannot be null"));
        }

        Constructor ctor = null;
        try {
            ctor = cls.getConstructor(parameterTypes);
            return ctor!=null && isAccessible(ctor)
                    && isAccessible(ctor.getDeclaringClass()) ? ctor : null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 执行class的构造器，根据参数匹配
     *
     * @param cls
     * @param args
     * @return T
     */
    public static <T> T invokeConstructor(final Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        args = ArrayUtils.nullToEmpty(args);
        Class<?> parameterTypes[] = new Class[0];
        if (args.length != 0) {
            final Class<?>[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i] == null ? null : args[i].getClass();
            }
        }
        final Constructor<T> ctor = getAccessibleConstructor(cls, parameterTypes);
        if (ctor == null) {
            throw new NoSuchMethodException(
                "No such accessible constructor on object: "+ cls.getName());
        }
        return ctor.newInstance(args);
    }

    /**
     * 获取可访问的方法
     *
     * @param cls
     * @param methodName
     * @param parameterTypes
     * @return java.lang.reflect.Method
     */
    public static Method getAccessibleMethod(final Class<?> cls, final String methodName,
            final Class<?>... parameterTypes) {
        try {
            Method method = cls.getMethod(methodName,
                    parameterTypes);
            if (!isAccessible(method)) {
                return null;
            }
            if (Modifier.isPublic(cls.getModifiers())) {
                return method;
            }
            // Check the implemented interfaces and subinterfaces
            method = getAccessibleMethodFromInterfaceNest(cls, methodName,
                    parameterTypes);

            // Check the superclass chain
            if (method == null) {
                method = getAccessibleMethodFromSuperclass(cls, methodName,
                        parameterTypes);
            }
            return method;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * <p>Returns an accessible method (that is, one that can be invoked via
     * reflection) by scanning through the superclasses. If no such method
     * can be found, return {@code null}.</p>
     *
     * @param cls Class to be checked
     * @param methodName Method name of the method we wish to call
     * @param parameterTypes The parameter type signatures
     * @return the accessible method or {@code null} if not found
     */
    private static Method getAccessibleMethodFromSuperclass(final Class<?> cls,
            final String methodName, final Class<?>... parameterTypes) {
        Class<?> parentClass = cls.getSuperclass();
        while (parentClass != null) {
            if (Modifier.isPublic(parentClass.getModifiers())) {
                try {
                    return parentClass.getMethod(methodName, parameterTypes);
                } catch (final NoSuchMethodException e) {
                    return null;
                }
            }
            parentClass = parentClass.getSuperclass();
        }
        return null;
    }

    /**
     * <p>Returns an accessible method (that is, one that can be invoked via
     * reflection) that implements the specified method, by scanning through
     * all implemented interfaces and subinterfaces. If no such method
     * can be found, return {@code null}.</p>
     *
     * <p>There isn't any good reason why this method must be {@code private}.
     * It is because there doesn't seem any reason why other classes should
     * call this rather than the higher level methods.</p>
     *
     * @param cls Parent class for the interfaces to be checked
     * @param methodName Method name of the method we wish to call
     * @param parameterTypes The parameter type signatures
     * @return the accessible method or {@code null} if not found
     */
    private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls,
            final String methodName, final Class<?>... parameterTypes) {
        // Search up the superclass chain
        for (; cls != null; cls = cls.getSuperclass()) {

            // Check the implemented interfaces of the parent class
            final Class<?>[] interfaces = cls.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                // Is this interface public?
                if (!Modifier.isPublic(interfaces[i].getModifiers())) {
                    continue;
                }
                // Does the method exist on this interface?
                try {
                    return interfaces[i].getDeclaredMethod(methodName,
                            parameterTypes);
                } catch (final NoSuchMethodException e) { // NOPMD
                    /*
                     * Swallow, if no method is found after the loop then this
                     * method returns null.
                     */
                }
                // Recursively check our parent interfaces
                final Method method = getAccessibleMethodFromInterfaceNest(interfaces[i],
                        methodName, parameterTypes);
                if (method != null) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 获取带有指定注解的方法
     *
     * @param cls
     * @param annotationCls
     * @return java.util.List<java.lang.reflect.Method>
     */
    public static List<Method> getMethodsWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
        if(cls==null) {
        	throw new IllegalArgumentException(String.format("The class must not be null"));
        }
        if(annotationCls==null) {
        	throw new IllegalArgumentException(String.format("The annotation class must not be null"));
        }
        final Method[] allMethods = cls.getMethods();
        final List<Method> annotatedMethods = new ArrayList<Method>();
        for (final Method method : allMethods) {
            if (method.getAnnotation(annotationCls) != null) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    /**
     * 执行指定方法
     *
     * @param object
     * @param methodName
     * @param args
     * @return java.lang.Object
     */
    public static Object invokeMethod(final Object object, final String methodName, Object... args)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        args = ArrayUtils.nullToEmpty(args);
        Class<?> parameterTypes[] = new Class[0];
        if (args.length != 0) {
            final Class<?>[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i] == null ? null : args[i].getClass();
            }
        }
        final Method method = getAccessibleMethod(object.getClass(), methodName,
                parameterTypes);
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: "
                    + methodName + "() on object: "
                    + object.getClass().getName());
        }
        return method.invoke(object, args);
    }
}
