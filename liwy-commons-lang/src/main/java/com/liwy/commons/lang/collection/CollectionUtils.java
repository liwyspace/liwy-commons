package com.liwy.commons.lang.collection;

import com.liwy.commons.lang.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liwy
 * @date 2019-07-04 14:20
 */
public class CollectionUtils {

    public static final List EMPTY_LIST = Collections.EMPTY_LIST;
    public static final Set EMPTY_SET = Collections.EMPTY_SET;
    public static final Map EMPTY_MAP = Collections.EMPTY_MAP;

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        if (null == ts) {
            return new HashSet<>();
        }
        int initialCapacity = Math.max((int) (ts.length / .75f) + 1, 16);
        HashSet<T> set = new HashSet<>(initialCapacity);
        for (T t : ts) {
            set.add(t);
        }
        return set;
    }

    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return (ArrayList<T>) Arrays.asList(values);
    }

    /**
     * 集合为空
     *
     * @param coll
     * @return
     */
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 集合非空
     *
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    /**
     * 根据集合返回一个元素计数的 {@link Map}<br>
     * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
     * 例如：[a,b,c,c,c] 得到：<br>
     * a: 1<br>
     * b: 1<br>
     * c: 3<br>
     *
     * @param <T> 集合元素类型
     * @param col {@link Iterator}，如果为null返回一个空的Map
     * @return {@link Map}
     */
    public static <T> Map<T, Integer> countMap(Collection<T> col) {
        final HashMap<T, Integer> countMap = new HashMap<>();
        if (null == col) {
            return countMap;
        }
        for (final T obj : col) {
            final Integer c = countMap.get(obj);
            if (c == null) {
                countMap.put(obj, Integer.valueOf(1));
            } else {
                countMap.put(obj, Integer.valueOf(c.intValue() + 1));
            }
        }
        return countMap;
    }

    /**
     * 两个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param <T>   集合元素类型
     * @param collA 集合A
     * @param collB 集合B
     * @return 并集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> union(Collection<T> collA, Collection<T> collB) {
        final ArrayList<T> list = new ArrayList<>();
        if (isEmpty(collA)) {
            list.addAll(collB);
        } else if (isEmpty(collB)) {
            list.addAll(collA);
        } else {
            final Map<T, Integer> mapA = countMap(collA);
            final Map<T, Integer> mapB = countMap(collB);
            final Set<T> collSet = new HashSet<>(collA);
            collSet.addAll(collB);
            for (T obj : collSet) {
                int m = Math.max(ObjectUtils.defaultIfNull(mapA.get(obj), 0), ObjectUtils.defaultIfNull(mapB.get(obj), 0));
                for (int i = 0; i < m; i++) {
                    list.add(obj);
                }
            }
        }
        return list;
    }

    /**
     * 两个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 交集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> intersection(Collection<T> coll1, Collection<T> coll2) {
        final ArrayList<T> list = new ArrayList<>();
        if (isNotEmpty(coll1) && isNotEmpty(coll2)) {
            final Map<T, Integer> map1 = countMap(coll1);
            final Map<T, Integer> map2 = countMap(coll2);
            final Set<T> elts = new HashSet<>(coll2);
            int m;
            for (T t : elts) {
                m = Math.min(ObjectUtils.defaultIfNull(map1.get(t), 0), ObjectUtils.defaultIfNull(map2.get(t), 0));
                for (int i = 0; i < m; i++) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 两个集合的差集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留两个集合中此元素个数差的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[c]，此结果中只保留了一个<br>
     * 任意一个集合为空，返回另一个集合<br>
     * 两个集合无交集则返回两个集合的组合
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 差集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> disjunction(Collection<T> coll1, Collection<T> coll2) {
        if (isEmpty(coll1)) {
            return coll2;
        }
        if (isEmpty(coll2)) {
            return coll1;
        }

        final ArrayList<T> result = new ArrayList<>();
        final Map<T, Integer> map1 = countMap(coll1);
        final Map<T, Integer> map2 = countMap(coll2);
        final Set<T> elts = new HashSet<>(coll2);
        elts.addAll(coll1);
        int m;
        for (T t : elts) {
            m = Math.abs(ObjectUtils.defaultIfNull(map1.get(t), 0) - ObjectUtils.defaultIfNull(map2.get(t), 0));
            for (int i = 0; i < m; i++) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 集合中是吧包含任意一值
     *
     * @param coll1
     * @param coll2
     * @param <T>
     * @return
     */
    public static <T> boolean containsAny(final Collection<?> coll1, final T... coll2) {
        if (coll1.size() < coll2.length) {
            for (final Object aColl1 : coll1) {
                if (ArrayUtils.contains(coll2, aColl1)) {
                    return true;
                }
            }
        } else {
            for (final Object aColl2 : coll2) {
                if (coll1.contains(aColl2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 集合1中是否包含集合2中所有的元素，既集合2是否为集合1的子集
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 集合1中是否包含集合2中所有的元素
     */
    public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
        if (coll2.isEmpty()) {
            return true;
        }
        final Iterator<?> it = coll1.iterator();
        final Set<Object> elementsAlreadySeen = new HashSet<>();
        for (final Object nextElement : coll2) {
            if (elementsAlreadySeen.contains(nextElement)) {
                continue;
            }

            boolean foundCurrentElement = false;
            while (it.hasNext()) {
                final Object p = it.next();
                elementsAlreadySeen.add(p);
                if (nextElement == null ? p == null : nextElement.equals(p)) {
                    foundCurrentElement = true;
                    break;
                }
            }

            if (!foundCurrentElement) {
                return false;
            }
        }
        return true;
    }

    /**
     * 加入全部
     *
     * @param <C>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterable   要加入的{@link Iterable}
     * @return 是否加入成功
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
        if (iterable instanceof Collection<?>) {
            return collection.addAll((Collection<? extends C>) iterable);
        }

        boolean changed = false;
        Iterator<? extends C> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            changed |= collection.add(iterator.next());
        }
        return changed;
    }

    public static <C> boolean addAll(final Collection<C> collection, final C... elements) {
        boolean changed = false;
        for (final C element : elements) {
            changed |= collection.add(element);
        }
        return changed;
    }

    /**
     * 删除集合
     *
     * @param collection
     * @param remove
     * @param <E>
     * @return
     */
    public static <E> List<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
        final List<E> list = new ArrayList<>();
        for (final E obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 去掉集合中的多个元素
     *
     * @param collection  集合
     * @param elesRemoved 被去掉的元素数组
     * @return 原集合
     * @since 4.1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> removeAll(Collection<T> collection, T... elesRemoved) {
        collection.removeAll(newHashSet(elesRemoved));
        return collection;
    }

    /**
     * List去重
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return {@link ArrayList}
     */
    public static <T> List<T> distinct(List<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(collection));
    }

    /**
     * List排序
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        list.sort(null);
    }

    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }

    /**
     * 获取列表中的最小值
     *
     * @param coll
     * @param <T>
     * @return
     */
    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) < 0) {
                candidate = next;
            }
        }
        return candidate;
    }

    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
        if (comp == null) {
            return (T) min((Collection) coll);
        }

        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (comp.compare(next, candidate) < 0) {
                candidate = next;
            }
        }
        return candidate;
    }

    /**
     * 获取列表中的最大值
     *
     * @param coll
     * @param <T>
     * @return
     */
    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) > 0) {
                candidate = next;
            }
        }
        return candidate;
    }

    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        if (comp == null) {
            return (T) max((Collection) coll);
        }

        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (comp.compare(next, candidate) > 0) {
                candidate = next;
            }
        }
        return candidate;
    }

    /**
     * 替换列表中的数据
     *
     * @param list
     * @param oldVal
     * @param newVal
     * @param <T>
     * @return
     */
    public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
        boolean result = false;
        int size = list.size();
        if (size < 11 || list instanceof RandomAccess) {
            if (oldVal == null) {
                for (int i = 0; i < size; i++) {
                    if (list.get(i) == null) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (oldVal.equals(list.get(i))) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            }
        } else {
            ListIterator<T> itr = list.listIterator();
            if (oldVal == null) {
                for (int i = 0; i < size; i++) {
                    if (itr.next() == null) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (oldVal.equals(itr.next())) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 集合分片
     *
     * @param list
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> partition(final List<T> list, final int size) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        return new Partition<>(list, size);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(final List<T> list, final int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(final int index) {
            final int listSize = size();
            if (index < 0) {
                throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            }
            if (index >= listSize) {
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " +
                        listSize);
            }
            final int start = index * size;
            final int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        @Override
        public int size() {
            return (int) Math.ceil((double) list.size() / (double) size);
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }

    /**
     * 分组，按照{@link Hash}接口定义的hash算法，集合中的元素放入hash值对应的子列表中
     *
     * @param <T>        元素类型
     * @param collection 被分组的集合
     * @param hash       Hash值算法，决定元素放在第几个分组的规则
     * @return 分组后的集合
     */
    public static <T> List<List<T>> group(Collection<T> collection, Hash<T> hash) {
        final List<List<T>> result = new ArrayList<>();
        if (isEmpty(collection)) {
            return result;
        }
        if (null == hash) {
            // 默认hash算法，按照元素的hashCode分组
            hash = new Hash<T>() {
                @Override
                public int hash(T t) {
                    return null == t ? 0 : t.hashCode();
                }
            };
        }

        int index;
        List<T> subList;
        for (T t : collection) {
            index = hash.hash(t);
            if (result.size() - 1 < index) {
                while (result.size() - 1 < index) {
                    result.add(null);
                }
                result.set(index, newArrayList(t));
            } else {
                subList = result.get(index);
                if (null == subList) {
                    result.set(index, newArrayList(t));
                } else {
                    subList.add(t);
                }
            }
        }
        return result;
    }

    /**
     * Hash计算接口
     *
     * @param <T> 被计算hash的对象类型
     * @author looly
     * @since 3.2.2
     */
    public static interface Hash<T> {
        /**
         * 计算Hash值
         *
         * @param t 对象
         * @return hash
         */
        int hash(T t);
    }
}
