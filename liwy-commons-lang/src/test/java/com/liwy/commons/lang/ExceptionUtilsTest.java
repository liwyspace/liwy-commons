package com.liwy.commons.lang;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>异常工具的测试类</p>
 *
 * @author liwy
 */
public class ExceptionUtilsTest {

    /**
     * 获取异常链中的异常
     */
    @Test
    public void testGetThrowableList() {
        IOException ioException = new IOException();
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        assertThat(ExceptionUtils.getThrowableList(argumentException), hasItems(argumentException, ioException));
    }

    /**
     * 获取异常的消息字符串
     */
    @Test
    public void testGetMessage() {
        IOException ioException = new IOException();
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        assertThat(ExceptionUtils.getMessage(argumentException).toString(), is("IllegalArgumentException: java.io.IOException"));
    }

    /**
     * 获取根本原因异常
     */
    @Test
    public void testGetRootCause() {
        IOException ioException = new IOException();
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        assertThat(ExceptionUtils.getRootCause(argumentException).toString(), is("java.io.IOException"));
    }

    /**
     * 获取根本原因异常的消息字符串
     */
    @Test
    public void testGetRootCauseMessage() {
        IOException ioException = new IOException("IO异常");
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        assertThat(ExceptionUtils.getRootCauseMessage(argumentException), is("IOException: IO异常"));
    }

    /**
     * 获取异常的堆栈追踪字符串
     */
    @Test
    public void testGetStackTrace() {
        IOException ioException = new IOException();
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        assertThat(ExceptionUtils.getStackTrace(argumentException),
                allOf(startsWith("java.lang.IllegalArgumentException: java.io.IOException"), endsWith(" more\n")));
    }

}

