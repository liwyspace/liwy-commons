package com.liwy.commons.lang;

import java.util.concurrent.TimeUnit;

public class StopWatch {
	private static final long NANO_2_MILLIS = 1000000L;
	
	private enum State {
        UNSTARTED {
            @Override boolean isStarted() { return false; }
            @Override boolean isStopped() { return true;  }
            @Override boolean isSuspended() { return false; }
        },
        RUNNING {
            @Override boolean isStarted() { return true; }
            @Override boolean isStopped() { return false; }
            @Override boolean isSuspended() { return false; }
        },
        STOPPED {
            @Override boolean isStarted() { return false; }
            @Override boolean isStopped() { return true; }
            @Override boolean isSuspended() { return false; }
        },
        SUSPENDED {
            @Override boolean isStarted() { return true; }
            @Override boolean isStopped() { return false; }
            @Override  boolean isSuspended() { return true; }
        };
        abstract boolean isStarted();
        abstract boolean isStopped();
        abstract boolean isSuspended();
    }
	private enum SplitState {
        SPLIT,
        UNSPLIT
    }
	private State runningState = State.UNSTARTED;
    private SplitState splitState = SplitState.UNSPLIT;
    private long startTime;
    private long startTimeMillis;
    private long stopTime;
	
	public StopWatch() {
        super();
    }
	/**
	 * 提供一个方便的开始秒表。
	 * @return
	 */
	public static StopWatch createStarted() {
        StopWatch sw = new StopWatch();
        sw.start();
        return sw;
    }
	/**
	 * 开始秒表
	 */
	public void start() {
        if (this.runningState == State.STOPPED) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
        }
        if (this.runningState != State.UNSTARTED) {
            throw new IllegalStateException("Stopwatch already started. ");
        }
        this.startTime = System.nanoTime();
        this.startTimeMillis = System.currentTimeMillis();
        this.runningState = State.RUNNING;
    }
	/**
	 * 停止秒表
	 */
	public void stop() {
        if (this.runningState != State.RUNNING && this.runningState != State.SUSPENDED) {
            throw new IllegalStateException("Stopwatch is not running. ");
        }
        if (this.runningState == State.RUNNING) {
            this.stopTime = System.nanoTime();
        }
        this.runningState = State.STOPPED;
    }
	/**
	 * 重置秒表。如果需要停止。
	 */
	public void reset() {
        this.runningState = State.UNSTARTED;
        this.splitState = SplitState.UNSPLIT;
    }
	/**
	 * 分割时间。
	 */
	public void split() {
        if (this.runningState != State.RUNNING) {
            throw new IllegalStateException("Stopwatch is not running. ");
        }
        this.stopTime = System.nanoTime();
        this.splitState = SplitState.SPLIT;
    }
	/**
	 * 移除拆分。
	 */
	public void unsplit() {
        if (this.splitState != SplitState.SPLIT) {
            throw new IllegalStateException("Stopwatch has not been split. ");
        }
        this.splitState = SplitState.UNSPLIT;
    }
	/**
	 * 暂停秒表以便稍后恢复。
	 */
	public void suspend() {
        if (this.runningState != State.RUNNING) {
            throw new IllegalStateException("Stopwatch must be running to suspend. ");
        }
        this.stopTime = System.nanoTime();
        this.runningState = State.SUSPENDED;
    }
	/**
	 * 暂停后恢复秒表。
	 */
	public void resume() {
        if (this.runningState != State.SUSPENDED) {
            throw new IllegalStateException("Stopwatch must be suspended to resume. ");
        }
        this.startTime += System.nanoTime() - this.stopTime;
        this.runningState = State.RUNNING;
    }
	/**
	 * 在秒表上得到时间。
	 * @return
	 */
	public long getTime() {
        return getNanoTime() / NANO_2_MILLIS;
    }
	
	/**
	 * 对指定的时间单位秒表时间。
	 * @param timeUnit
	 * @return
	 */
	public long getTime(final TimeUnit timeUnit) {
        return timeUnit.convert(getNanoTime(), TimeUnit.NANOSECONDS);
    }
	
	/**
	 * 获取纳米级别的时间
	 * @return
	 */
	public long getNanoTime() {
        if (this.runningState == State.STOPPED || this.runningState == State.SUSPENDED) {
            return this.stopTime - this.startTime;
        } else if (this.runningState == State.UNSTARTED) {
            return 0;
        } else if (this.runningState == State.RUNNING) {
            return System.nanoTime() - this.startTime;
        }
        throw new RuntimeException("Illegal running state has occurred.");
    }
	
	/**
	 * 在秒表上得到拆分时间。
	 * @return
	 */
	public long getSplitTime() {
        return getSplitNanoTime() / NANO_2_MILLIS;
    }
	
	/**
	 * 在纳秒秒表时间分裂
	 * @return
	 */
	public long getSplitNanoTime() {
        if (this.splitState != SplitState.SPLIT) {
            throw new IllegalStateException("Stopwatch must be split to get the split time. ");
        }
        return this.stopTime - this.startTime;
    }
	
	/**
	 * 返回秒表开始的时间。
	 * @return
	 */
	public long getStartTime() {
        if (this.runningState == State.UNSTARTED) {
            throw new IllegalStateException("Stopwatch has not been started");
        }
        // System.nanoTime is for elapsed time
        return this.startTimeMillis;
    }
	
	/**
     * <p>
     * 该方法用于查找是否启动秒表。暂停秒表也开始观看
     * </p>
     *
     * @return boolean
     *             If the StopWatch is started.
     */
    public boolean isStarted() {
        return runningState.isStarted();
    }

    /**
     * <p>
     * 此方法用于查找是否暂停秒表。
     * </p>
     *
     * @return boolean
     *             If the StopWatch is suspended.
     * @since 3.2
     */
    public boolean isSuspended() {
        return runningState.isSuspended();
    }

    /**
     * <p>
     * 此方法用于查找秒表是否停止。秒表尚未启动，并明确停止秒表被认为是停止。
     * </p>
     *
     * @return boolean
     *             If the StopWatch is stopped.
     */
    public boolean isStopped() {
        return runningState.isStopped();
    }    
}
