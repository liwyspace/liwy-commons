# LIWY-COMMONS

[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)]()
[![GitHub license](https://img.shields.io/github/license/liwyspace/liwy-commons.svg)](https://github.com/liwyspace/liwy-commons/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/issues)
[![GitHub stars](https://img.shields.io/github/stars/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/liwyspace/liwy-commons.svg?style=social)](https://github.com/liwyspace/liwy-commons/network)

* __作者：__ LIWY
* __QQ：__ 332301842
* __微信：__ liwy1024611
* __邮箱：__ liwy1024@163.com
* __github:__ [https://github.com/liwyspace/](https://github.com/liwyspace/)
* __gitee:__ [https://gitee.com/liwycode/](https://gitee.com/liwycode/)
* __team@osc:__ [https://team.oschina.net/liwy/](https://team.oschina.net/liwy/)
* __开源小屋官网：__ [www.oscafe.org](http://www.oscafe.org) / [www.oscafe.net](http://www.oscafe.net)
* __开源小屋公众号：__ oscafe_net

![开源小屋www.oscafe.org 公众平台二维码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/oscafe_qrcode.jpg)

## 1. 前言

liwy-commons 是作者在开发实践中总结归纳的一组常用的工具。在该工具包含了基础、加密解密、校验、JDBC、HTTP、动态代理、脚本、压缩、验证码等常用工具包。在基本模块中包含了常用的数组、Class、日期、数学、随机、反射、排序算法、计时器、字符串、系统参数等工具。

## 2. 模块

* `liwy-commons-lang` - 基础工具
* `liwy-commons-dbutils` - JDBC工具
* `liwy-commons-http` - Http客户端工具
* `liwy-commons-proxy` - 动态代理工具
* `liwy-commons-script` - 脚本工具
* `liwy-commons-compress` - 压缩工具
* `liwy-commons-captcha` - 验证码工具

### liwy-commons-lang

* `AntPathMatcher v1.0.1` - Ant风格Path匹配工具
* `BeanUtils v1.0.1` - Bean工具
* `ClassUtils v1.0.1` - Class工具
* `EnumUtils v1.0.1` - 枚举工具
* `ExceptionUtils v1.0.1` - 异常工具
* `PropertiesUtils v1.0.1` - Properties工具
* `RandomUtils v1.0.1` - 随机工具
* `ReflectUtils v1.0.1` - 反射工具
* `StringUtils v1.0.1` - 字符串工具
* `SystemUtils v1.0.1` - 系统参数工具
* algorithms 算法包
    * `SortUtils v1.0.1` - 排序算法
* codec 加密解密包
    * `CodecUtils v1.0.1` - 加密解密工具
* collection 集合包
    * `ArrayUtils` - 数组工具
    * `ListUtils` - List工具
    * `MapUtils` - Map工具
    * `QueueUtils` - Queue工具
    * `SetUtils` - Set工具
* concurrent 并发包
    * `ThreadUtils` - 多线程工具
* io IO包
    * `StreamUtils` - IO工具
    * `FileUtils` - 文件工具
    * `PathUtils` - 路径工具
    * `FileNameUtils` - 文件名工具
* json JSON包
    * `JsonUtils` - JSON工具
* math 数学包
    * `NumberUtils` - 数字工具
    * `MathUtils` - 数学工具
* time 时间包
    * `DateUtils` - 日期工具
    * `StopwatchUtils` - 秒表工具
* validator 验证包
    * `ValidateUtils v1.0.1` - 验证工具类

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

## 3. 开发
```shell
#手动安装ojdbc依赖
mvn install:install-file -Dfile=/opt/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar -DgeneratePom=true

mvn compile #编译项目的源代码
mvn test #使用合适的单元测试框架来测试已编译的源代码
mvn clean #清除先前构建的编译文件（target文件夹）。 
mvn package #把已编译的代码打包成可发布的格式，比如jar。
mvn install #把包安装在本地的repository中，可以被其他工程作为依赖来使用。 

mvn dependency:list #查看依赖列表
mvn dependency:tree #查看依赖树
mvn dependency:analyze #Used undclared dependencies：使用到的但没有显示声明的依赖；Unused dclared dependencies:未使用的但显示声明的依赖

mvn package -DskipTests  #跳过测试
mvn test -Dtest=StringUtilTest #指定运行测试用例，可使用 "*" 号和 "," 号
mvn cobertura:cobertura  #生成测试覆盖率报告
```

## 3. 安装

### Maven

在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependencies>
    <dependency>
        <groupId>com.liwy.commons</groupId>
        <artifactId>liwy-commons</artifactId>
        <version>0.0.1</version>
    </dependency>
    <dependency>
        <groupId>com.liwy.commons</groupId>
        <artifactId>commons-lang</artifactId>
        <version>0.0.1</version>
    </dependency>
    <dependency>
        <groupId>com.liwy.commons</groupId>
        <artifactId>commons-http</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

### Gradle

```
compile 'com.liwy.commons:commons-lang:0.0.1'
```

### 非Maven项目

可以从[http://search.maven.org/](http://search.maven.org/) 搜索`liwy-commons`找到项目，点击对应版本，下面是相应的Jar包，导入即可使用。

[liwy-commons](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.liwy.commons%22%20AND%20a%3A%22commons-lang%22)

点击链接后点选择对应版本，点击列表尾部的“Download”下载jar、API文档、源码

如果中央库访问不便，可以访问阿里云的资源：

[http://maven.aliyun.com/nexus/content/groups/public/com/xiaoleilu/hutool-all/](http://maven.aliyun.com/nexus/content/groups/public/com/xiaoleilu/hutool-all/)

## 4. 文档 

请移步: [http://liwy-commons.mydoc.io/](http://liwy-commons.mydoc.io/)

## 5. 参考
* commons-lang          [https://github.com/apache/commons-lang](https://github.com/apache/commons-lang)
* commons-io            [https://github.com/apache/commons-io](https://github.com/apache/commons-io)
* commons-beanutils     [https://github.com/apache/commons-beanutils](https://github.com/apache/commons-beanutils)
* commons-dbutils       [https://github.com/apache/commons-dbutils](https://github.com/apache/commons-dbutils)
* Commons-collections   [https://github.com/apache/commons-collections](https://github.com/apache/commons-collections)
* commons-codec         [https://github.com/apache/commons-codec](https://github.com/apache/commons-codec)
* commons-validator     [https://github.com/apache/commons-validator](https://github.com/apache/commons-validator)
* commons-proxy         [https://github.com/apache/commons-proxy](https://github.com/apache/commons-proxy)
* commons-compress      [https://github.com/apache/commons-compress](https://github.com/apache/commons-compress)
* commons-imaging       [https://github.com/apache/commons-imaging](https://github.com/apache/commons-imaging)
* Jodd                  [https://github.com/oblac/jodd](https://github.com/oblac/jodd)
* guava                 [https://github.com/google/guava](https://github.com/google/guava)
* jcommon               [https://github.com/facebook/jcommon](https://github.com/facebook/jcommon)
* TwitterCommons        [https://github.com/twitter/commons](https://github.com/twitter/commons)
* VJTools               [https://github.com/vipshop/vjtools](https://github.com/vipshop/vjtools)
* Hutool                [https://github.com/looly/hutool/](https://github.com/looly/hutool/)
* JCaptcha              [http://jcaptcha.sourceforge.net/](http://jcaptcha.sourceforge.net/)


## 6. 支持
![微信付款码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/weixin_fkcode.jpg)
![支付宝付款码](https://github.com/liwyspace/liwy-commons/raw/master/docs/resources/zhifubao_fkcode.jpg)


## 7. Changelog

### 1.0.1

_新特性：_

* 增加StrUtil.removeAll

_Bug修复：_

* 修正Http模块无法301和302重定向问题

### 1.0.0

_新特性：_

* 增加StrUtil.removeAll
* 增加RandomUtil.randomEleSet方法
* 增加 CollectionUtil.distinct方法
* 增加BASE32实现

_Bug修复：_

* 修正Http模块无法301和302重定向问题
* 修复SqlBuilder中Insert值为null时SQL语句错误问题
