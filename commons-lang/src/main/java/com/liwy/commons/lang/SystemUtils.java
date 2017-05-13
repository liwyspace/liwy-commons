package com.liwy.commons.lang;

/**
 * 系统参数工具类
 * @author liwy-psbc
 *
 */
public class SystemUtils {
	/**
	 * 操作系统的架构
	 */
	public static final String OS_ARCH = System.getProperty("os.arch");
	/**
	 * 操作系统的名称
	 */
	public static final String OS_NAME = System.getProperty("os.name");
	/**
	 * 操作系统的版本
	 */
	public static final String OS_VERSION = System.getProperty("os.version");
	
	/**
	 * 文件的编码格式
	 */
	public static final String FILE_ENCODING = System.getProperty("file.encoding");
	
	/**
	 * 文件分隔符
	 * 
	 * 在 UNIX 系统中是"/"
	 * 在WINDOWS 系统中是"\"
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	/**
	 * 路径分隔符
	 * 
	 * 在 UNIX 系统中是":"
	 * 在WINDOWS 系统中是";"
	 */
	public static final String PATH_SEPARATOR = System.getProperty("path.separator");
	
	/**
	 * 行分隔符
	 * 
	 * 在 UNIX 系统中是"/n"
	 * 在WINDOWS 系统中是"\n"
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * 当前用户所属国家
	 */
	public static final String USER_COUNTRY = System.getProperty("user.country");
	
	/**
	 * 当前用户的当前工作目录
	 */
	public static final String USER_DIR = System.getProperty("user.dir");
	
	/**
	 * 当前用户的主目录
	 */
	public static final String USER_HOME = System.getProperty("user.home");
	
	/**
	 * 当前用户的语言
	 */
	public static final String USER_LANGUAGE = System.getProperty("user.language");
	
	/**
	 * 当前用户的名称
	 */
	public static final String USER_NAME = System.getProperty("user.name");
	
	/**
	 * 当前用户的时区
	 */
	public static final String USER_TIMEZONE = System.getProperty("user.timezone");
	
	/**
	 * Java 虚拟机模式
	 * 
	 * mixed mode	混合模式
	 * sharing		解释模式
	 */
	public static final String JAVA_VM_INFO = System.getProperty("java.vm.info");
	
	/**
	 * Java 虚拟机实现名称
	 */
	public static final String JAVA_VM_NAME = System.getProperty("java.vm.name");
	/**
	 * Java 虚拟机实现供应商
	 */
	public static final String JAVA_VM_VENDOR = System.getProperty("java.vm.vendor");
	
	/**
	 * Java 虚拟机实现版本
	 */
	public static final String JAVA_VM_VERSION = System.getProperty("java.vm.version");
	
	/**
	 * Java 虚拟机规范名称
	 */
	public static final String JAVA_VM_SPECIFICATION_NAME = System.getProperty("java.vm.specification.name");
	
	/**
	 * Java 虚拟机规范供应商
	 */
	public static final String JAVA_VM_SPECIFICATION_VENDOR = System.getProperty("java.vm.specification.vendor");
	
	/**
	 * Java 虚拟机规范版本
	 */
	public static final String JAVA_VM_SPECIFICATION_VERSION = System.getProperty("java.vm.specification.version");
	
	/**
	 * Java 运行时环境规范名称
	 */
	public static final String JAVA_SPECIFICATION_NAME = System.getProperty("java.specification.name");
	
	/**
	 * Java 运行时环境规范供应商
	 */
	public static final String JAVA_SPECIFICATION_VENDOR = System.getProperty("java.specification.vendor");
	
	/**
	 * Java 运行时环境规范版本
	 */
	public static final String JAVA_SPECIFICATION_VERSION = System.getProperty("java.specification.version");
	
	/**
	 * Java 运行时名称
	 */
	public static final String JAVA_RUNTIME_NAME = System.getProperty("java.runtime.name");
	
	/**
	 * Java 运行时版本
	 */
	public static final String JAVA_RUNTIME_VERSION = System.getProperty("java.runtime.version");
	
	/**
	 * Java 供应商
	 */
	public static final String JAVA_VENDOR = System.getProperty("java.vendor");
	
	/**
	 * Java 供应商地址
	 */
	public static final String JAVA_VENDOR_URL = System.getProperty("java.vendor.url");
	
	/**
	 * Java 版本
	 */
	public static final String JAVA_VERSION = System.getProperty("java.version");
	
	/**
	 * Java 安装目录
	 */
	public static final String JAVA_HOME = System.getProperty("java.home");
	
	/**
	 * 要使用的 JIT 编译器的名称
	 */
	public static final String JAVA_COMPILER = System.getProperty("java.compiler");
	
	/**
	 * Java 类路径
	 */
	public static final String JAVA_CLASS_PATH = System.getProperty("java.class.path");
	
	/**
	 * Java 类格式版本号
	 */
	public static final String JAVA_CLASS_VERSION = System.getProperty("java.class.version");
	
	/**
	 * awt工具包
	 * 
	 * 在Windows XP 上是 {@code sun.awt.windows.WToolkit}
	 */
	public static final String AWT_TOOLKIT = System.getProperty("awt.toolkit");
	
	/**
	 * Java awt可用字体
	 */
	public static final String JAVA_AWT_FONTS = System.getProperty("java.awt.fonts");
	
	/**
	 * Java awt图形环境
	 */
	public static final String JAVA_AWT_GRAPHICSENV = System.getProperty("java.awt.graphicsenv");
	
	/**
	 * Java awt是否headless模式
	 */
	public static final String JAVA_AWT_HEADLESS = System.getProperty("java.awt.headless");
	
	/**
	 * Java awt打印机制
	 */
	public static final String JAVA_AWT_PRINTERJOB = System.getProperty("java.awt.printerjob");
	
	/**
	 * Java 库加载路径
	 */
	public static final String JAVA_LIBRARY_PATH = System.getProperty("java.library.path");
	
	/**
	 * 默认的临时文件路径
	 */
	public static final String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");
	
	/**
	 * 包升级替换地址
	 */
	public static final String JAVA_ENDORSED_DIRS = System.getProperty("java.endorsed.dirs");
	
	/**
	 * 可选包扩展地址
	 */
	public static final String JAVA_EXT_DIRS = System.getProperty("java.ext.dirs");
}
