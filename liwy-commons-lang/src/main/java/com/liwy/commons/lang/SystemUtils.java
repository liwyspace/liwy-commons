package com.liwy.commons.lang;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>常用系统参数工具类</p>
 *
 * <ul>
 *  <li>标准系统信息</li>
 *  <li>判断是否指定Java版本</li>
 *  <li>判断是否指定系统版本</li>
 *  <li><b>getHostName</b>
 *      - 获取HostName</li>
 *  <li><b>getInetAddress</b>
 *      - 获取系统地址信息</li>
 *  <li><b>getProperty</b>
 *      - 查看指定系统变量（-D）</li>
 *  <li><b>getEnv</b>
 *      - 查看指定环境变量</li>
 *  <li><b>getPropertyFromAll</b>
 *      - 从系统变量与环境变量中查找，系统变量优先</li>
 *  <li><b>registerSysPropertiesListener</b>
 *      - 注册系统变量监听</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
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

	//-----------------------> JDK版本

	public static final boolean IS_JAVA_1_1 = isMatchJavaVersion("1.1");
	public static final boolean IS_JAVA_1_2 = isMatchJavaVersion("1.2");
	public static final boolean IS_JAVA_1_3 = isMatchJavaVersion("1.3");
	public static final boolean IS_JAVA_1_4 = isMatchJavaVersion("1.4");
	public static final boolean IS_JAVA_1_5 = isMatchJavaVersion("1.5");
	public static final boolean IS_JAVA_1_6 = isMatchJavaVersion("1.6");
	public static final boolean IS_JAVA_1_7 = isMatchJavaVersion("1.7");
	public static final boolean IS_JAVA_1_8 = isMatchJavaVersion("1.8");
	public static final boolean IS_JAVA_9 = isMatchJavaVersion("9");
	public static final boolean IS_JAVA_10 = isMatchJavaVersion("10");
	public static final boolean IS_JAVA_11 = isMatchJavaVersion("11");

	//-----------------------> 操作系统版本

	public static final boolean IS_OS_AIX = isMatchOs("AIX");
	public static final boolean IS_OS_HP_UX = isMatchOs("HP-UX");
	// IBM OS/400.
	public static final boolean IS_OS_400 = isMatchOs("OS/400");
	public static final boolean IS_OS_IRIX = isMatchOs("Irix");
	public static final boolean IS_OS_LINUX = isMatchOs("Linux") || isMatchOs("LINUX");

	public static final boolean IS_OS_MAC = isMatchOs("Mac");
	public static final boolean IS_OS_MAC_OSX = isMatchOs("Mac OS X");
	// Mac OS X Cheetah.
	public static final boolean IS_OS_MAC_OSX_CHEETAH = isMatchOsVersion("Mac OS X", "10.0");
	// Mac OS X Puma.
	public static final boolean IS_OS_MAC_OSX_PUMA = isMatchOsVersion("Mac OS X", "10.1");
	// Mac OS X Jaguar.
	public static final boolean IS_OS_MAC_OSX_JAGUAR = isMatchOsVersion("Mac OS X", "10.2");
	// Mac OS X Panther.
	public static final boolean IS_OS_MAC_OSX_PANTHER = isMatchOsVersion("Mac OS X", "10.3");
	// Mac OS X Tiger.
	public static final boolean IS_OS_MAC_OSX_TIGER = isMatchOsVersion("Mac OS X", "10.4");
	// Mac OS X Leopard.
	public static final boolean IS_OS_MAC_OSX_LEOPARD = isMatchOsVersion("Mac OS X", "10.5");
	// Mac OS X Snow Leopard.
	public static final boolean IS_OS_MAC_OSX_SNOW_LEOPARD = isMatchOsVersion("Mac OS X", "10.6");
	// Mac OS X Lion.
	public static final boolean IS_OS_MAC_OSX_LION = isMatchOsVersion("Mac OS X", "10.7");
	// Mac OS X Mountain Lion.
	public static final boolean IS_OS_MAC_OSX_MOUNTAIN_LION = isMatchOsVersion("Mac OS X", "10.8");
	// Mac OS X Mavericks.
	public static final boolean IS_OS_MAC_OSX_MAVERICKS = isMatchOsVersion("Mac OS X", "10.9");
	// Mac OS X Yosemite.
	public static final boolean IS_OS_MAC_OSX_YOSEMITE = isMatchOsVersion("Mac OS X", "10.10");
	// Mac OS X El Capitan.
	public static final boolean IS_OS_MAC_OSX_EL_CAPITAN = isMatchOsVersion("Mac OS X", "10.11");

	public static final boolean IS_OS_FREE_BSD = isMatchOs("FreeBSD");
	public static final boolean IS_OS_OPEN_BSD = isMatchOs("OpenBSD");
	public static final boolean IS_OS_NET_BSD = isMatchOs("NetBSD");
	public static final boolean IS_OS_OS2 = isMatchOs("OS/2");
	public static final boolean IS_OS_SOLARIS = isMatchOs("Solaris");
	public static final boolean IS_OS_SUN_OS = isMatchOs("SunOS");

	public static final boolean IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX
			|| IS_OS_SOLARIS || IS_OS_SUN_OS || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD;

	/**
	 * 判断是否为Windows系统
	 */
	private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
	public static final boolean IS_OS_WINDOWS = isMatchOs(OS_NAME_WINDOWS_PREFIX);
	public static final boolean IS_OS_WINDOWS_2000 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 2000");
	public static final boolean IS_OS_WINDOWS_2003 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 2003");
	public static final boolean IS_OS_WINDOWS_2008 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " Server 2008");
	public static final boolean IS_OS_WINDOWS_2012 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " Server 2012");
	public static final boolean IS_OS_WINDOWS_95 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 95");
	public static final boolean IS_OS_WINDOWS_98 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 98");
	public static final boolean IS_OS_WINDOWS_ME = isMatchOs(OS_NAME_WINDOWS_PREFIX + " Me");
	public static final boolean IS_OS_WINDOWS_NT = isMatchOs(OS_NAME_WINDOWS_PREFIX + " NT");
	public static final boolean IS_OS_WINDOWS_XP = isMatchOs(OS_NAME_WINDOWS_PREFIX + " XP");
	public static final boolean IS_OS_WINDOWS_VISTA = isMatchOs(OS_NAME_WINDOWS_PREFIX + " Vista");
	public static final boolean IS_OS_WINDOWS_7 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 7");
	public static final boolean IS_OS_WINDOWS_8 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 8");
	public static final boolean IS_OS_WINDOWS_10 = isMatchOs(OS_NAME_WINDOWS_PREFIX + " 10");

	/**
	 * 通过JDK版本前缀匹配
	 *
	 * @param versionPrefix
	 * @return boolean
	 */
	private static boolean isMatchJavaVersion(final String versionPrefix) {
		if (JAVA_SPECIFICATION_VERSION == null) {
			return false;
		}
		return JAVA_SPECIFICATION_VERSION.startsWith(versionPrefix);
	}

	/**
	 * 通过系统名称前缀匹配系统
	 *
	 * @param osNamePrefix
	 * @return boolean
	 */
	private static boolean isMatchOs(final String osNamePrefix) {
		if (StringUtils.isEmpty(OS_NAME)) {
			return false;
		}
		return OS_NAME.startsWith(osNamePrefix);
	}

	/**
	 * 通过系统名称前缀与版本匹配
	 *
	 * @param osNamePrefix
	 * @param osVersionPrefix
	 * @return boolean
	 */
	private static boolean isMatchOsVersion(final String osNamePrefix, final String osVersionPrefix) {
		if(!isMatchOs(osNamePrefix)) {
			return false;
		}
		if (StringUtils.isEmpty(OS_VERSION)) {
			return false;
		}
		final String[] versionPrefixParts = osVersionPrefix.split("\\.");
		final String[] versionParts = OS_VERSION.split("\\.");
		for (int i = 0; i < Math.min(versionPrefixParts.length, versionParts.length); i++) {
			if (!versionPrefixParts[i].equals(versionParts[i])) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 获取HostName
	 *
	 * @param
	 * @return java.lang.String
	 */
	public static String getHostName() {
		return IS_OS_WINDOWS ? System.getenv("COMPUTERNAME") : System.getenv("HOSTNAME");
	}

	/**
	 * 获取系统地址信息
	 * getHostName
	 * getHostAddress
	 *
	 * @param
	 * @return java.net.InetAddress
	 */
	public static InetAddress getInetAddress() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddress;
	}

	/**
	 * 查看指定系统变量（-D）
	 *
	 * @param name
	 * @return java.lang.String
	 */
	public static String getProperty(String name) {
		return System.getProperty(name);
	}

	/**
	 * 查看指定环境变量
	 *
	 * @param name
	 * @return java.lang.String
	 */
	public static String getEnv(String name) {
		return System.getenv(name);
	}

	/**
	 * 从系统变量与环境变量中查找，系统变量优先
	 *
	 * @param
	 * @return java.lang.String
	 */
	public static String getPropertyFromAll(String name) {
		String value = getProperty(name);
		if(value==null) {
			value = getEnv(name);
		}
		return value;
	}

	/**
	 * 注册系统变量监听
	 * Properties 本质上是一个HashTable，每次读写都会加锁，所以不支持频繁的System.getProperty(name)来检查系统内容变化
	 * 因此扩展了Properties子类,在其所关心的属性变化时进行通知.
	 *
	 * @see ListenableProperties
	 */
	public static synchronized void registerSysPropertiesListener(SysPropertiesListener listener) {
		Properties currentProperties = System.getProperties();

		// 将System的properties实现替换为ListenableProperties
		if (!(currentProperties instanceof ListenableProperties)) {
			ListenableProperties newProperties = new ListenableProperties(currentProperties);
			System.setProperties(newProperties);
			currentProperties = newProperties;
		}

		((ListenableProperties) currentProperties).register(listener);
	}

	/**
	 * 获取所关心的Property变更的Listener基类.
	 */
	public abstract static class SysPropertiesListener {
		// 关心的Property
		protected String propertyName;
		public SysPropertiesListener(String propertyName) {
			this.propertyName = propertyName;
		}
		public abstract void onChange(String propertyName, String value);
	}

	/**
	 * Properties扩展类类,在其所关心的属性变化时进行通知.
	 *
	 * @see SysPropertiesListener
	 */
	private static class ListenableProperties extends Properties {

		private static final long serialVersionUID = -8282465702074684324L;

		protected transient List<SysPropertiesListener> listeners = new CopyOnWriteArrayList<SysPropertiesListener>();

		public ListenableProperties(Properties properties) {
			super(properties);
		}

		public void register(SysPropertiesListener listener) {
			listeners.add(listener);
		}

		@Override
		public synchronized Object setProperty(String key, String value) {
			Object result = put(key, value);
			for (SysPropertiesListener listener : listeners) {
				if (listener.propertyName.equals(key)) {
					listener.onChange(key, value);
				}
			}
			return result;
		}
	}

}
