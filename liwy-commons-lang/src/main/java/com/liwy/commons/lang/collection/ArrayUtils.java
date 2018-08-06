package com.liwy.commons.lang.collection;

import com.liwy.commons.lang.ClassUtils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>关于数组的工具类</p>
 *
 * <ul>
 *  <li><b>getLength</b>
 *      - 获取数组的长度</li>
 *  <li><b>toMap</b>
 *      - 将给定的数组转换为{@link Map}</li>
 *  <li><b>toArray</b>
 *      - 创建类型安全的通用数组</li>
 *  <li><b>clone</b>
 *      - 浅克隆数组返回一个类型化的结果和处理</li>
 *  <li><b>subarray</b>
 *      - 截取数组</li>
 *  <li><b>reverse</b>
 *      - 反向指定的数组</li>
 *  <li><b>swap</b>
 *      - 交换指定元素</li>
 *  <li><b>indexOf</b>
 *      - 获取数组元素所在位置</li>
 *  <li><b>contains</b>
 *      - 是否包含指定元素</li>
 *  <li><b>isEmpty</b>
 *      - 校验数组是否为空</li>
 *  <li><b>nullToEmpty</b>
 *      - 将null转换为空数组</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class ArrayUtils {
    /**
     * 空数组常量
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    /**
     * <p>获取数组的长度
     *
     * <p>如果参数为null则返回0
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array
     * @return int
     */
    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * <p>将给定的数组转换为{@link Map}。
     * 每个数组的元素必须是{@link Entry}或数组，
     * 包含至少两个要素，其中第一个要素是作为键,第二个要素作为值</p>
     *
     * <pre>
     * Map colorMap = ArrayUtils.toMap(new String[][] {
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}});
     * </pre>
     *
     * @param array
     * @return
     */
    public static Map<Object, Object> toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<Object, Object>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry<?, ?>) {
                final Entry<?,?> entry = (Entry<?,?>) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '"
                            + object
                            + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    /**
     * <p>创建类型安全的通用数组
     *
     * <p>java语言不允许数组是从泛型类型创建:
     *
     * <pre>
     * public static &lt;T&gt; T[] createAnArray(int size) {
     * 		return new T[size]; // compiler error here
     * }
     * public static &lt;T&gt; T[] createAnArray(int size) {
     * 		return (T[])new Object[size]; // ClassCastException at runtime
     * }
     * </pre>
     *
     * <p>因此，可以用这种方法创建新的泛型类型数组。
     * 例如，可以创建一个字符串数组：
     *
     * <pre>
     * String[] array = ArrayUtils.toArray("1", "2");
     * String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
     * </pre>
     *
     * <p>该方法通常用于场景中，调用方本身使用必须合并到数组中的泛型类型。
     *
     * <p>注意，此方法仅用于提供相同类型的参数，以便编译器可以推断数组本身的类型。虽然可以选择类型明确的例如
     * <code>Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))</code>,
     * 没有真正的优势与
     * <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.
     * 相比
     *
     * @param  <T>   the array's element type
     * @param  items  the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     */
    public static <T> T[] toArray(final T... items) {
        return items;
    }

    /**
     * <p>浅克隆数组返回一个类型化的结果和处理
     * {@code null}.
     *
     * <p>数组中的对象不被克隆，因此多维数组没有特殊处理。
     *
     * <p>返回 {@code null} ，如果输入的数组为 {@code null}
     *
     * @param <T> the component type of the array
     * @param array  the array to shallow clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>产生一个包含起始和结束索引之间元素的新数组</p>
     *
     * <p>开始索引是包容的，结束索引是不包含的。空数组输入产生空输出</p>
     *
     * <p>的子阵列的组件类型总是与输入的数组一样。因此，如果输入是类型{@code Date}的数组，则设想如下用法：
     *
     * <pre>
     * Date[] someDates = (Date[])ArrayUtils.subarray(allDates, 2, 5);
     * </pre>
     *
     * @param <T> 数组的组成类型
     * @param array  待处理的数组
     * @param startIndexInclusive
     * @param endIndexExclusive
     * @return 包含起始索引和结束索引之间的元素的新数组。
     */
    public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        final Class<?> type = array.getClass().getComponentType();
        if (newSize <= 0) {
            final T[] emptyArray = (T[]) Array.newInstance(type, 0);
            return emptyArray;
        }
        final T[] subarray = (T[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>反向指定的数组
     * <p>多维数组没有特殊处理
     * <p>此方法对{@code null}输入数组不起作用。
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final Object[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }
    /**
     * <p>
     * 在给定范围内颠倒给定数组的顺序。
     *
     * <p>
     * 此方法对{@code null}输入数组不起作用。
     *
     * @param array
     * @param startIndexInclusive
     * @param endIndexExclusive
     */
    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * 交换指定元素
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 2) -&gt; ["3", "2", "1"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 0) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 1, 0) -&gt; ["2", "1", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 5) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], -1, 1) -&gt; ["2", "1", "3"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final Object[] array, int offset1, int offset2) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        Object aux = array[offset1];
        array[offset1] = array[offset2];
        array[offset2] = aux;
    }

    /**
     * <p>获取指定对象在指定数组中的索引
     *
     * <p>该方法返回 {@code -1} for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the index of the object within the array,
     *  ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * <p>获取指定对象在指定数组该数组从指定索引开始中的索引
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index,
     *  ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * <p>检查对象是否在给定数组中
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    /**
     * 校验数组是否为空
     *
     * @param array
     * @return boolean
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final long[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final int[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final short[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final char[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final byte[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final double[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final float[] array) {
        return getLength(array) == 0;
    }
    public static boolean isEmpty(final boolean[] array) {
        return getLength(array) == 0;
    }

    /**
     * 将null转换为空数组
     *
     * @param array
     * @return java.lang.Object[]
     */
    public static <T> T[] nullToEmpty(final T[] array, final Class<T[]> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null");
        }

        if (array == null) {
            return type.cast(Array.newInstance(type.getComponentType(), 0));
        }
        return array;
    }
    public static Object[] nullToEmpty(final Object[] array) {
        if (isEmpty(array)) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }
    public static Class<?>[] nullToEmpty(final Class<?>[] array) {
        if (isEmpty(array)) {
            return EMPTY_CLASS_ARRAY;
        }
        return array;
    }
    public static String[] nullToEmpty(final String[] array) {
        if (isEmpty(array)) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }
    public static long[] nullToEmpty(final long[] array) {
        if (isEmpty(array)) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }
    public static int[] nullToEmpty(final int[] array) {
        if (isEmpty(array)) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }
    public static short[] nullToEmpty(final short[] array) {
        if (isEmpty(array)) {
            return EMPTY_SHORT_ARRAY;
        }
        return array;
    }
    public static char[] nullToEmpty(final char[] array) {
        if (isEmpty(array)) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }
    public static byte[] nullToEmpty(final byte[] array) {
        if (isEmpty(array)) {
            return EMPTY_BYTE_ARRAY;
        }
        return array;
    }
    public static double[] nullToEmpty(final double[] array) {
        if (isEmpty(array)) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }
    public static float[] nullToEmpty(final float[] array) {
        if (isEmpty(array)) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array;
    }
    public static boolean[] nullToEmpty(final boolean[] array) {
        if (isEmpty(array)) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }
    public static Long[] nullToEmpty(final Long[] array) {
        if (isEmpty(array)) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }
    public static Integer[] nullToEmpty(final Integer[] array) {
        if (isEmpty(array)) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }
    public static Short[] nullToEmpty(final Short[] array) {
        if (isEmpty(array)) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }
    public static Character[] nullToEmpty(final Character[] array) {
        if (isEmpty(array)) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }
    public static Byte[] nullToEmpty(final Byte[] array) {
        if (isEmpty(array)) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }
    public static Double[] nullToEmpty(final Double[] array) {
        if (isEmpty(array)) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }
    public static Float[] nullToEmpty(final Float[] array) {
        if (isEmpty(array)) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }
    public static Boolean[] nullToEmpty(final Boolean[] array) {
        if (isEmpty(array)) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>将包装类数组转换为基本数据类型数组
     *
     * @param array  a {@code Character} array, may be {@code null}
     * @return a {@code char} array, {@code null} if null array input
     */
    public static Object toPrimitive(final Object array) {
        if (array == null) {
            return null;
        }
        Class<?> ct = array.getClass().getComponentType();
        Class<?> pt = ClassUtils.wrapperToPrimitive(ct);
        if(Integer.TYPE.equals(pt)) {
            return toPrimitive((Integer[]) array);
        }
        if(Long.TYPE.equals(pt)) {
            return toPrimitive((Long[]) array);
        }
        if(Short.TYPE.equals(pt)) {
            return toPrimitive((Short[]) array);
        }
        if(Double.TYPE.equals(pt)) {
            return toPrimitive((Double[]) array);
        }
        if(Float.TYPE.equals(pt)) {
            return toPrimitive((Float[]) array);
        }
        return array;
    }
    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new char[0];
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }
    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new char[0];
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            final Character b = array[i];
            result[i] = (b == null ? valueForNull : b.charValue());
        }
        return result;
    }

    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new long[0];
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }
    public static long[] toPrimitive(final Long[] array, final long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new long[0];
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            final Long b = array[i];
            result[i] = (b == null ? valueForNull : b.longValue());
        }
        return result;
    }
    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new int[0];
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }
    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new int[0];
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            final Integer b = array[i];
            result[i] = (b == null ? valueForNull : b.intValue());
        }
        return result;
    }
    public static short[] toPrimitive(final Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new short[0];
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }
    public static short[] toPrimitive(final Short[] array, final short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new short[0];
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            final Short b = array[i];
            result[i] = (b == null ? valueForNull : b.shortValue());
        }
        return result;
    }
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new byte[0];
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }
    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new byte[0];
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            final Byte b = array[i];
            result[i] = (b == null ? valueForNull : b.byteValue());
        }
        return result;
    }
    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new double[0];
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }
    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new double[0];
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            final Double b = array[i];
            result[i] = (b == null ? valueForNull : b.doubleValue());
        }
        return result;
    }
    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new float[0];
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }
    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new float[0];
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            final Float b = array[i];
            result[i] = (b == null ? valueForNull : b.floatValue());
        }
        return result;
    }
    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new boolean[0];
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new boolean[0];
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            final Boolean b = array[i];
            result[i] = (b == null ? valueForNull : b.booleanValue());
        }
        return result;
    }

    /**
     * <p>将基本数据类型数组转换为包装类数组
     *
     * @param array
     * @return java.lang.Character[]
     */
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Character[0];
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Long[0];
        }
        final Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Integer[0];
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Short[0];
        }
        final Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Byte[0];
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Double[0];
        }
        final Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Float[0];
        }
        final Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Boolean[0];
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return result;
    }
    
	// add
    private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            final Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        final int length = Array.getLength(array);
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        final Object result = Array.newInstance(clss, length + 1);
        System.arraycopy(array, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array, index, result, index + 1, length - index);
        }
        return result;
    }
    
	// remove
    public static <T> T[] remove(final T[] array, final int index) {
        return (T[]) remove((Object) array, index);
    }
    private static Object remove(final Object array, final int index) {
        int length = 0;
        if(array==null) {
        	length=0;
        } else {
        	length = Array.getLength(array);
        }
        
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        final Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }
    
}
