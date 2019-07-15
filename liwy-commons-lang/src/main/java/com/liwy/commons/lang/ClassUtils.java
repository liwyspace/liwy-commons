package com.liwy.commons.lang;

import java.util.*;
import java.util.Map.Entry;

/**
 * <p>常用Class工具类</p>
 *
 * <p>常用方法</p>
 * <ul>
 * <li><b>getShortClassName</b>             - 获取Class的简短名称，与getSimpleName在内部类上有些不同</li>
 * <li><b>getAllSuperclasses</b>            - 获取所有祖先类</li>
 * <li><b>getAllInterfaces</b>              - 获取所有祖先类的所有接口</li>
 * <li><b>convertClassNamesToClasses</b>    - 将ClassName转换为Class</li>
 * <li><b>convertClassesToClassNames</b>    - 将Class转换为ClassName</li>
 * <li><b>isPrimitiveOrWrapper</b>          - 是否是基本类型或包装类</li>
 * <li><b>isPrimitiveWrapper</b>            - 是否是基本类型的包装类</li>
 * <li><b>primitiveToWrapper</b>            - 基本类型转包装类</li>
 * <li><b>wrapperToPrimitive</b>            - 包装类转基本类型</li>
 * <li><b>isInnerClass</b>                  - 是否为内部类</li>
 * <li><b>getClass</b>                      - 通过ClassName加载类</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class ClassUtils {
    /**
     * 基本类型名与基本类型的映射
     */
    private static final Map<String, Class<?>> namePrimitiveMap = new HashMap<>();

    static {
        namePrimitiveMap.put("boolean", Boolean.TYPE);
        namePrimitiveMap.put("byte", Byte.TYPE);
        namePrimitiveMap.put("char", Character.TYPE);
        namePrimitiveMap.put("short", Short.TYPE);
        namePrimitiveMap.put("int", Integer.TYPE);
        namePrimitiveMap.put("long", Long.TYPE);
        namePrimitiveMap.put("double", Double.TYPE);
        namePrimitiveMap.put("float", Float.TYPE);
        namePrimitiveMap.put("void", Void.TYPE);
    }

    /**
     * 基本类型与包装类的映射
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }

    /**
     * 包装类与基本类型的映射
     */
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

    static {
        for (final Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
            final Class<?> primitiveClass = entry.getKey();
            final Class<?> wrapperClass = entry.getValue();
            if (!primitiveClass.equals(wrapperClass)) {
                wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
            }
        }
    }

    /**
     * 将基本类型名映射到数组类名中相应的缩写
     */
    private static final Map<String, String> abbreviationMap;
    /**
     * 将数组类名中的缩写映射到对应的基本类型名。
     */
    private static final Map<String, String> reverseAbbreviationMap;

    static {
        final Map<String, String> m = new HashMap<String, String>();
        m.put("int", "I");
        m.put("boolean", "Z");
        m.put("float", "F");
        m.put("long", "J");
        m.put("short", "S");
        m.put("byte", "B");
        m.put("double", "D");
        m.put("char", "C");
        final Map<String, String> r = new HashMap<String, String>();
        for (final Entry<String, String> e : m.entrySet()) {
            r.put(e.getValue(), e.getKey());
        }
        abbreviationMap = Collections.unmodifiableMap(m);
        reverseAbbreviationMap = Collections.unmodifiableMap(r);
    }

    /**
     * <p>获取Class的简单类名</p>
     *
     * @param cls 待操作Class
     * @return 没有包名的类名，或空字符串
     */
    public static String getShortClassName(final Class<?> cls) {
        if (cls == null) {
            return StringUtils.EMPTY;
        }
        return getShortClassName(cls.getName());
    }

    /**
     * <p>从字符串中获取类名减去包名称。</p>
     * <p>被传入的字符串假定是一个没有被校验的类名.</p>
     *
     * <p>注意，这种方法不同于 Class.getSimpleName() 这将返回 {@code "Map.Entry"}
     * 然而 {@code java.lang.Class} 将返回 {@code "Entry"}. </p>
     *
     * @param className 待操作ClassName
     * @return 没有包名的类名，或空字符串
     */
    public static String getShortClassName(String className) {
        if (StringUtils.isEmpty(className)) {
            return StringUtils.EMPTY;
        }

        final StringBuilder arrayPrefix = new StringBuilder();

        // Handle array encoding
        if (className.startsWith("[")) {
            while (className.charAt(0) == '[') {
                className = className.substring(1);
                arrayPrefix.append("[]");
            }
            // Strip Object type encoding
            if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
                className = className.substring(1, className.length() - 1);
            }

            if (reverseAbbreviationMap.containsKey(className)) {
                className = reverseAbbreviationMap.get(className);
            }
        }

        final int lastDotIdx = className.lastIndexOf('.');
        final int innerIdx = className.indexOf(
                '$', lastDotIdx == -1 ? 0 : lastDotIdx + 1);
        String out = className.substring(lastDotIdx + 1);
        if (innerIdx != -1) {
            out = out.replace('$', '.');
        }
        return out + arrayPrefix;
    }

    /**
     * <p>获取该class的上级class的 {@code List}.</p>
     *
     * @param cls 待操作的Class
     * @return 所有上级Class列表, 如果输入{@code null}则返回{@code null}
     */
    public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    /**
     * <p>获取该类和该类的父类实现的接口的集合</p>
     *
     * @param cls 待操作的类
     * @return 所有接口的集合, 如果输入{@code null}则返回{@code null}
     */
    public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }

        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<Class<?>>();
        getAllInterfaces(cls, interfacesFound);

        return new ArrayList<Class<?>>(interfacesFound);
    }

    /**
     * <p>获取该类实现的接口的集合</p>
     *
     * @param cls             待操作的类
     * @param interfacesFound 获取的该类的接口集
     */
    private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            final Class<?>[] interfaces = cls.getInterfaces();

            for (final Class<?> i : interfaces) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }

            cls = cls.getSuperclass();
        }
    }

    /**
     * <p>将className列表转换为Class列表</p>
     *
     * @param classNames 待转换的ClassName
     * @return 转换后的Class列表, 如果输入为null则返回null
     */
    public static List<Class<?>> convertClassNamesToClasses(final List<String> classNames) {
        if (classNames == null) {
            return null;
        }
        final List<Class<?>> classes = new ArrayList<Class<?>>(classNames.size());
        for (final String className : classNames) {
            try {
                classes.add(Class.forName(className));
            } catch (final Exception ex) {
                classes.add(null);
            }
        }
        return classes;
    }

    /**
     * <p>将{@code Class} 对象列表转换为ClassNames列表.</p>
     *
     * @param classes 待转换的class列表
     * @return 转换后的classname列表, 如果输入为null则返回null
     */
    public static List<String> convertClassesToClassNames(final List<Class<?>> classes) {
        if (classes == null) {
            return null;
        }
        final List<String> classNames = new ArrayList<String>(classes.size());
        for (final Class<?> cls : classes) {
            if (cls == null) {
                classNames.add(null);
            } else {
                classNames.add(cls.getName());
            }
        }
        return classNames;
    }

    /**
     * <p>判断是否为基本类型或包装类</p>
     *
     * @param type
     * @return 如果是基本类型或包装类则返回true
     */
    public static boolean isPrimitiveOrWrapper(final Class<?> type) {
        if (type == null) {
            return false;
        }
        return type.isPrimitive() || isPrimitiveWrapper(type);
    }

    /**
     * <p>判断是否为基本类型的包装类： ({@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
     * {@link Integer}, {@link Long}, {@link Double}, {@link Float}).</p>
     *
     * @param type
     * @return 如果是包装类返回true
     */
    public static boolean isPrimitiveWrapper(final Class<?> type) {
        return wrapperPrimitiveMap.containsKey(type);
    }

    /**
     * <p>基本类型转包装类</p>
     *
     * @param cls 待转换的基本类型
     * @return 包装类, 如果不是基本类型或是null则返回本身
     */
    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = primitiveWrapperMap.get(cls);
        }
        return convertedClass;
    }

    /**
     * <p>包装类型转基本类型</p>
     *
     * @param cls 待转换的类型，可能是 <b>null</b>
     * @return 转换后的类型, 如果不是包装类或是null则返回null
     */
    public static Class<?> wrapperToPrimitive(final Class<?> cls) {
        return wrapperPrimitiveMap.get(cls);
    }

    /**
     * <p>判断该类是否为内部类或匿名内部类</p>
     *
     * @param cls 待校验的Class
     * @return 如果是内部类或匿名内部类则返回true, 如果不是或者是{@code null}则返回false
     */
    public static boolean isInnerClass(final Class<?> cls) {
        return cls != null && cls.getEnclosingClass() != null;
    }

    /**
     * <p>通过className与classLoader加载类
     * 支持如下格式的className:
     * "{@code java.util.Map.Entry[]}", "{@code java.util.Map$Entry[]}",
     * "{@code [Ljava.util.Map.Entry;}", and "{@code [Ljava.util.Map$Entry;}".</p>
     *
     * @param classLoader 类加载器
     * @param className   类名
     * @param initialize  是否初始化
     * @return 加载后的类
     * @throws ClassNotFoundException 如果class没有被发现
     */
    public static Class<?> getClass(
            final ClassLoader classLoader, final String className, final boolean initialize) throws ClassNotFoundException {
        try {
            Class<?> clazz;
            if (namePrimitiveMap.containsKey(className)) {
                clazz = namePrimitiveMap.get(className);
            } else {
                clazz = Class.forName(toCanonicalName(className), initialize, classLoader);
            }
            return clazz;
        } catch (final ClassNotFoundException ex) {
            // 支持使用.表示内部类的className
            final int lastDotIndex = className.lastIndexOf('.');

            if (lastDotIndex != -1) {
                try {
                    return getClass(classLoader, className.substring(0, lastDotIndex) +
                                    '$' + className.substring(lastDotIndex + 1),
                            initialize);
                } catch (final ClassNotFoundException ex2) {
                    // 忽略异常
                }
            }

            throw ex;
        }
    }

    /**
     * <p>加载类并初始化</p>
     *
     * @param className 类名
     * @return 通过当前线程中的类加载器加载的类，并已初始化
     * @throws ClassNotFoundException 如果没有发现类
     */
    public static Class<?> getClass(final String className) throws ClassNotFoundException {
        final ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
        final ClassLoader loader = contextCL == null ? ClassUtils.class.getClassLoader() : contextCL;
        return getClass(loader, className, true);
    }

    /**
     * <p>将className转换为JLS风格的ClassName</p>
     *
     * <pre>
     *     java.lang.String[]   [Ljava.lang.String
     * </pre>
     *
     * @param className 待转换的className
     * @return 转换后的ClassName
     */
    private static String toCanonicalName(String className) {
        className = StringUtils.trim(className);
        if (className == null) {
            throw new NullPointerException("className must not be null.");
        } else if (className.endsWith("[]")) {
            final StringBuilder classNameBuffer = new StringBuilder();
            while (className.endsWith("[]")) {
                className = className.substring(0, className.length() - 2);
                classNameBuffer.append("[");
            }
            final String abbreviation = abbreviationMap.get(className);
            if (abbreviation != null) {
                classNameBuffer.append(abbreviation);
            } else {
                classNameBuffer.append("L").append(className).append(";");
            }
            className = classNameBuffer.toString();
        }
        return className;
    }
}
