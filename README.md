# LIWY-COMMONS
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/liwyspace/liwy-commons)
[![GitHub issues](https://img.shields.io/github/issues/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/issues)
[![GitHub stars](https://img.shields.io/github/stars/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/network)

> 官网: [开源小屋 www.oscafe.net](http://www.oscafe.net)
>
> 邮箱: oscafe_net@163.com
>
> QQ: 332301842
>
> 扫描关注微信公众号：开源小屋
>
> ![开源小屋www.oscafe.net 公众平台二维码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/oscafe_qrcode.jpg)
## 前言
liwy-commons 是作者在开发实践中总结归纳的一组常用的工具类。
## 模块
* `liwy-commons-lang` - 基本工具类，包括String、Array、Math、Date等
* `liwy-commons-dbutils` - 对JDBC常用操作的封装
* ...
### liwy-commons-lang
* `ArrayUtils` - 数组工具
* `ClassUtils` - Class工具
* `CodecUtils` - 加密解密工具
* `DateUtils` - 日期工具
* `MathUtils` - 数学工具
* `RandomUtils` - 随机工具
* `ReflectUtils` - 反射工具
* `SortUtils` - 排序算法
* `StopWatch` - 计时器工具
* `StringUtils` - 字符串工具
* `SystemUtils` - 系统参数工具
* `ValidateUtils` - 数据验证工具
### liwy-commons-dbutils
* handlers
  * `ResultSetHandler` - 结果集处理器接口
  * `AbstractListHandler` - 返回List型的结果集的抽象处理器
  * `AbstractKeyedHandler` - 返回Map型的结果集的抽象处理器
  * `ArrayHandler` - 返回一条Array型记录的处理器
  * `ArrayListHandler` - 返回一组Array型记录并保存在List中的处理器
  * `BeanHandler` - 返回一条Bean型记录的处理器
  * `BeanListHandler` - 返回一组Bean型记录并保存在List中的处理器
  * `BeanKeyedHandler` - 返回一组Bean型记录并保存在Map中的处理器
  * `MapHandler` - 返回一条Map型记录的处理器
  * `MapListHandler` - 返回一组Map型记录并保存在List中的处理器
  * `MapKeyedHandler` - 返回一组Map型记录并保存在Map中的处理器
  * `ColumnHandler` - 返回一条记录的某一列的处理器
  * `ColumnListHandler` - 返回一组记录的某一列的处理器
* wrappers
  * `ProxyFactory` - 代理工厂
  * `StringTrimmedResultSet` - 去除结果两侧空白的代理器
* `DbUtils` - 数据库连接与结果集工具
* `QueryLoader` - Sql文件加载器
* `QueryRunner` - CRUD相关工具
* `SqlCmdUtils` - 模拟SQL客户端

## 安装

### Maven
在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>net.oscafe</groupId>
    <artifactId>liwy-commons</artifactId>
    <version>${liwy-commons.version}</version>
</dependency>
```

### Gradle
```
compile 'com.xiaoleilu:hutool-all:${hutool.version}'
```

### 非Maven项目
可以从[http://search.maven.org/](http://search.maven.org/) 搜索`liwy-commons`找到项目，点击对应版本，下面是相应的Jar包，导入即可使用。

[http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.xiaoleilu%22%20AND%20a%3A%22hutool-all%22](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.xiaoleilu%22%20AND%20a%3A%22hutool-all%22)

点击链接后点选择对应版本，点击列表尾部的“Download”下载jar、API文档、源码

如果中央库访问不便，可以访问阿里云的资源：

[http://maven.aliyun.com/nexus/content/groups/public/com/xiaoleilu/hutool-all/](http://maven.aliyun.com/nexus/content/groups/public/com/xiaoleilu/hutool-all/)

## 文档 

请移步: [http://liwy-commons.mydoc.io/](http://liwy-commons.mydoc.io/)

## 支持
![微信付款码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/weixin_fkcode.jpg)
![支付宝付款码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/zhifubao_fkcode.jpg)


## Changelog

### 1.0.1
#### 新特性
* 增加StrUtil.removeAll
* 增加RandomUtil.randomEleSet方法
* 增加 CollectionUtil.distinct方法
* 增加BASE32实现

#### Bug修复
* 修正Http模块无法301和302重定向问题
* 修复SqlBuilder中Insert值为null时SQL语句错误问题

---------------------------------------------------------------------------

### 1.0.0
#### 新特性
* 增加StrUtil.removeAll
* 增加RandomUtil.randomEleSet方法
* 增加 CollectionUtil.distinct方法
* 增加BASE32实现

#### Bug修复
* 修正Http模块无法301和302重定向问题
* 修复SqlBuilder中Insert值为null时SQL语句错误问题
