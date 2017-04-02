# LIWY-COMMONS
> 官网: [开源小屋 www.oscafe.net](http://www.oscafe.net)
>
> 邮箱: oscafe_net@163.com
>
> QQ: 332301842
>
> 扫描关注微信公众号：开源小屋
>
> ![开源小屋www.oscafe.net 公众平台二维码](./oscafe_qrcode.jpg)
## 前言
liwy-commons 是作者在开发实践中总结归纳的一组常用的工具类。
## 模块
* `liwy-commons-lang` - 基本工具类，包括String、Array、Math、Date等
* `liwy-commons-dbutils` - 对JDBC常用操作的封装
* ...
### liwy-commons-lang
> `ArrayUtils` - 数组工具
>
> `ClassUtils` - Class工具
>
> `CodecUtils` - 加密解密工具
>
> `DateUtils` - 日期工具
>
> `MathUtils` - 数学工具
>
> `RandomUtils` - 随机工具
>
> `ReflectUtils` - 反射工具
>
> `SortUtils` - 排序算法
>
> `StopWatch` - 计时器工具
>
> `StringUtils` - 字符串工具
>
> `SystemUtils` - 系统参数工具
>
> `ValidateUtils` - 数据验证工具
### liwy-commons-dbutils
> handlers
>> `ResultSetHandler` - 结果集处理器接口
>>
>> `AbstractListHandler` - 返回List型的结果集的抽象处理器
>>
>> `AbstractKeyedHandler` - 返回Map型的结果集的抽象处理器
>>
>> `ArrayHandler` - 返回一条Array型记录的处理器
>>
>> `ArrayListHandler` - 返回一组Array型记录并保存在List中的处理器
>>
>> `BeanHandler` - 返回一条Bean型记录的处理器
>>
>> `BeanListHandler` - 返回一组Bean型记录并保存在List中的处理器
>>
>> `BeanKeyedHandler` - 返回一组Bean型记录并保存在Map中的处理器
>>
>> `MapHandler` - 返回一条Map型记录的处理器
>>
>> `MapListHandler` - 返回一组Map型记录并保存在List中的处理器
>>
>> `MapKeyedHandler` - 返回一组Map型记录并保存在Map中的处理器
>>
>> `ColumnHandler` - 返回一条记录的某一列的处理器
>>
>> `ColumnListHandler` - 返回一组记录的某一列的处理器

>
> wrappers
>> `ProxyFactory` - 代理工厂
>>
>> `StringTrimmedResultSet` - 去除结果两侧空白的代理器
>
> `DbUtils` - 数据库连接与结果集工具
>
> `QueryLoader` - Sql文件加载器
>
> `QueryRunner` - CRUD相关工具
>
> `SqlCmdUtils` - 模拟SQL客户端

## 支持
![微信付款码](./weixin_fkcode.jpg)
![支付宝付款码](./zhifubao_fkcode.jpg)