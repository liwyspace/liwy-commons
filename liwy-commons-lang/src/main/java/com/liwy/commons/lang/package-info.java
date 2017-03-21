/*
 * 授权给Apache软件基金会（ASF）下的一个或多个贡献者许可协议。
 * 有关版权所有权的其他信息，请参见此文件分发的通知文件。
 * ASF的许可证文件你的Apache许可证下，版本2（“许可证”）。
 * 您可能不使用此文件，除非符合许可证。
 * 您可以获得许可证副本在
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 除非适用法律要求或书面同意，在许可证下分发的软件是按“按需”分配的，没有任何明示或暗示的保证或条件。
 * 见许可证的权限和限制下的特定语言的许可证。
 */
/**
 * <p>该模块主要提供了一些工作中常用的公共方法</p>
 * <p>lang需要JDK 1.5+<p>
 * 
 * 
 * <h3>字符串处理 - StringUtils</h3>
 * <p>主要的类为{@link com.liwy.commons.lang.StringUtils}</p>
 * 
 * <h3>随机工具 - RandomUtils</h3>
 * <p>主要的类为{@link com.liwy.commons.lang.RandomUtils} 主要用于生成随机的数字与字符串</p>
 * 
 * 
 * 
 * ===========================================================================
 * <p>提供高度可重复使用的静态的实用方法，主要关注增加价值到{@link java.lang}类。
 * 这些类中的大多数是不可变的，因此线程安全。
 * 然而{@link org.apache.commons.lang3.charset }是目前在所有情况下保证线程安全。</p>
 * 
 * <p>顶级包中包含了各种实用工具类，虽然有各种各样的子包包括{@link org.apache.commons.lang3.math}, 
 * {@link org.apache.commons.lang3.concurrent} and {@link org.apache.commons.lang3.builder}。
 * 使用工具类一般是简单的。
 * 它们等价于另一种语言的全局函数，即独立的、线程安全的、静态方法的集合。
 * 相比之下，子包可能包含已经实现的接口或可能从代码中继承了所有方法的类。
 * 然而，它们可能包含更多类似全局的函数。</p>
 * 
 * <p>lang 3.0 需要JDK 1.5 + 3.2，因为郎需要JDK 6 +；遗产释放需要JDK 1.2 + 2.6。
 * 在这两种情况下，你可以找到后来被我们所可能被删除或修改，在JDK中的下一个主要版本有利于保持功能。
 * 请注意，郎3使用了一个不同的包比它的前辈，允许它被用来在同一时间作为较早版本。</p>
 * 
 * <p>你会发现过时的方法当你漫步通过朗文件。这些被删除在下一个主要版本。</p>
 * 
 * <p>所有的公用事业类包含警告不要使用空的公共构造函数。
 * 这似乎是一件奇怪的事情，但它允许像速度之类的工具访问类，就像它是一个bean。
 * 换句话说，是的，我们知道私有构造函数，并选择不使用它们。</p>
 * 
 * 
 * <h3>字符串处理 - StringUtils, StringEscapeUtils, RandomStringUtils</h3>
 *
 * <p>郎有一系列的字符串工具。
 * 第一个是{@link org.apache.commons.lang3.stringutils}，许多功能的调整，大量变换、挤压和拥抱{@link java.lang.string java lang.strings }。
 * 除了stringutils，还有其他一系列字符串操作类；{@link org.apache.commons.lang3.randomstringutils }和{@link org.apache.commons.lang3.stringescapeutils stringescapeutils }。
 * randomstringutils不言而喻。
 * 它提供了生成文本片段的方法，如可能用于默认密码。
 * stringescapeutils包含方法逃脱，unescape java，JavaScript，HTML，XML和SQL。</p>
 * 
 * <p>这些都是开始使用理想的类，如果你想进入Lang.
 * stringutils'{@link org.apache.commons.lang3.StringUtils#capitalize(String)}, {@link org.apache.commons.lang3.StringUtils#substringBetween(String, String)}/{@link org.apache.commons.lang3.StringUtils#substringBefore(String, String) Before}/{@link org.apache.commons.lang3.StringUtils#substringAfter(String, String) After}, {@link org.apache.commons.lang3.StringUtils#split(String)} and {@link org.apache.commons.lang3.StringUtils#join(Object[])}开始有好的方法。
 * 如果你使用java.sql.statements很多，StringEscapeUtils.escapeSql可能会感兴趣的。
 * </p>
 *
 * <h3>字符处理 - CharSetUtils, CharSet, CharRange, CharUtils</h3>
 *
 * <p>In addition to dealing with Strings, it's also important to deal with chars and Characters.
 * {@link org.apache.commons.lang3.CharUtils} exists for this purpose, while {@link org.apache.commons.lang3.CharSetUtils} exists for set-manipulation of Strings.
 * Be careful, although CharSetUtils takes an argument of type String, it is only as a set of characters.
 * For example, <code>CharSetUtils.delete("testtest", "tr")</code> will remove all t's and all r's from the String, not just the String "tr". </p>
 *
 * <p>{@link org.apache.commons.lang3.CharRange} and {@link org.apache.commons.lang3.CharSet} are both used internally by CharSetUtils, and will probably rarely be used.</p>
 *
 * <h3>JVM 互动 - SystemUtils, CharEncoding</h3>
 *
 * <p>SystemUtils is a simple little class which makes it easy to find out information about which platform you are on.
 * For some, this is a necessary evil. It was never something I expected to use myself until I was trying to ensure that Commons Lang itself compiled under JDK 1.2.
 * Having pushed out a few JDK 1.3 bits that had slipped in (<code>Collections.EMPTY_MAP</code> is a classic offender), I then found that one of the Unit Tests was dying mysteriously under JDK 1.2, but ran fine under JDK 1.3.
 * There was no obvious solution and I needed to move onwards, so the simple solution was to wrap that particular test in a <code>if(SystemUtils.isJavaVersionAtLeast(1.3f)) {</code>, make a note and move on.</p>
 *
 * <p>The {@link org.apache.commons.lang3.CharEncoding} class is also used to interact with the Java environment and may be used to see which character encodings are supported in a particular environment. </p>
 *
 * <h3>序列化 - SerializationUtils, SerializationException</h3>
 *
 * <p>Serialization doesn't have to be that hard!
 * A simple util class can take away the pain, plus it provides a method to clone an object by unserializing and reserializing, an old Java trick.</p>
 *
 * <h3>各种方法 - ObjectUtils, ClassUtils, ArrayUtils, BooleanUtils</h3>
 *
 * <p>你会相信它, {@link org.apache.commons.lang3.ObjectUtils} 包含方便的对象功能, 主要是空安全实现的方法在 {@link java.lang.Object}.</p>
 *
 * <p>{@link org.apache.commons.lang3.ClassUtils} 在很大程度上是一组反射的辅助方法.
 * 特别要注意的是隐藏在ClassUtils的比较器，用于排序的类和包对象的名字；然而他们只是按字母顺序排序，不懂整理的习惯 <code>java</code> and <code>javax</code> first.</p>
 *
 * <p>下一步, {@link org.apache.commons.lang3.ArrayUtils}.
 * 这是一个大的，有许多方法和许多重载这些方法，所以它可能是值得深入研究这里。
 * 在开始之前，假设每一个方法都重载了所有的原语和对象。
 * 此外，短的“xxx”意味着一个通用的原始类型，但通常也包括对象。 </p>
 *
 * <ul>
 *  <li>ArrayUtils 为所有基本类型提供单例空数组. 这将在很大程度上是使用集合API的toArray方法，而且将使用的方法，想返回空数组错误。</li>
 *  <li><code>add(xxx[], xxx)</code> 将添加一个原始类型到数组中，调整数组的大小，正如你所期望的那样。对象也支持。 </li>
 *  <li><code>clone(xxx[])</code> 克隆原始数组或对象。 </li>
 *  <li><code>contains(xxx[], xxx)</code> searches for a primitive or Object in a primitive or Object array. </li>
 *  <li><code>getLength(Object)</code> returns the length of any array or an IllegalArgumentException if the parameter is not an array. <code>hashCode(Object)</code>, <code>equals(Object, Object)</code>, <code>toString(Object)</code> </li>
 *  <li><code>indexOf(xxx[], xxx)</code> and <code>indexOf(xxx[], xxx, int)</code> are copies of the classic String methods, but this time for primitive/Object arrays. In addition, a lastIndexOf set of methods exists. </li>
 *  <li><code>isEmpty(xxx[])</code> lets you know if an array is zero-sized or null. </li>
 *  <li><code>isSameLength(xxx[], xxx[])</code> returns true if the arrays are the same length. </li>
 *  <li>Along side the add methods, there are also remove methods of two types. The first type remove the value at an index, <code>remove(xxx[], int)</code>, while the second type remove the first value from the array, <code>remove(xxx[], xxx)</code>. </li>
 *  <li>Nearing the end now. The <code>reverse(xxx[])</code> method turns an array around. </li>
 *  <li>The <code>subarray(xxx[], int, int)</code> method splices an array out of a larger array. </li>
 *  <li>Primitive to primitive wrapper conversion is handled by the <code>toObject(xxx[])</code> and <code>toPrimitive(Xxx[])</code> methods. </li>
 * </ul>
 *
 * <p>最后, {@link org.apache.commons.lang3.ArrayUtils#toMap(Object[])} 值得特别注意。
 * 它不是一种重载的用于数组的方法，而是一种简单的从字面创建映射的方法。 </p>
 *
 * <h4>Using toMap</h4>
 * <pre>
 * <code>
 * Map colorMap = ArrayUtils.toMap(new String[][] {{
 *   {"RED", "#FF0000"},
 *   {"GREEN", "#00FF00"},
 *   {"BLUE", "#0000FF"}
 * });
 * </code>
 * </pre>
 *
 * <p>Our final util class is {@link org.apache.commons.lang3.BooleanUtils}.
 * 它包含各种布尔代理方法，可能最感兴趣的是 {@link org.apache.commons.lang3.BooleanUtils#toBoolean(String)} method which turns various positive/negative Strings into a Boolean object, and not just true/false as with Boolean.valueOf.</p>
 *
 * <h3>零碎 - BitField, Validate</h3>
 * <p>在结束我们的包，我们留下了一些类，不适合任何话题至今。 </p>
 * <p>The {@link org.apache.commons.lang3.BitField} class provides a wrapper class around the classic bitmask integer, whilst the {@link org.apache.commons.lang3.Validate} class may be used for assertions (remember, we support Java 1.2). </p>
 *
 * @since 1.0
 */
package com.liwy.commons.lang;
