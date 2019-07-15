package com.liwy.commons.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>常用异常工具类</p>
 *
 * <ul>
 * <li><b>getThrowableList</b>     - 获取异常链中的异常</li>
 * <li><b>getMessage</b>           - 获取异常的消息字符串</li>
 * <li><b>getRootCause</b>         - 获取根本原因异常</li>
 * <li><b>getRootCauseMessage</b>  - 获取根本原因异常的消息字符串</li>
 * <li><b>getStackTrace</b>        - 获取异常的堆栈追踪字符串</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class ExceptionUtils {
    /**
     * 获取异常链中的异常
     *
     * @param throwable
     * @return List<Throwable>
     */
    public static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<Throwable>();
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }

    /**
     * 获取异常的消息字符串
     *
     * @param th
     * @return java.lang.String
     */
    public static String getMessage(final Throwable th) {
        if (th == null) {
            return StringUtils.EMPTY;
        }
        final String clsName = ClassUtils.getShortClassName(th.getClass());
        final String msg = th.getMessage();
        return clsName + ": " + msg;
    }

    /**
     * 获取根本原因异常
     *
     * @param throwable
     * @return java.lang.Throwable
     */
    public static Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> list = getThrowableList(throwable);
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    /**
     * 获取根本原因异常的消息字符串
     *
     * @param th
     * @return java.lang.String
     */
    public static String getRootCauseMessage(final Throwable th) {
        Throwable root = getRootCause(th);
        root = root == null ? th : root;
        return getMessage(root);
    }

    /**
     * 获取异常的堆栈追踪字符串
     *
     * @param throwable
     * @return java.lang.String
     */
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
