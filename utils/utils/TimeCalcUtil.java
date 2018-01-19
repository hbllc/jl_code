package com.xgd.boss.core.utils;

import java.util.concurrent.TimeUnit;

/**
 * 计时工具 多线程不安全
 *
 * @author chengshaojin
 * @since 2017/12/22
 */
public class TimeCalcUtil {

    private long originalStart;

    private long previousTime;

    private boolean started = false;

    private boolean lapped = false;

    /**
     * 调用start方法开始计时
     *
     * @param autoLap 是否自动lap
     */
    public void start(boolean autoLap) {
        originalStart = System.currentTimeMillis();
        started = true;
        if (autoLap) {
            lap();
        }
    }

    /**
     * 调用lap方法lap
     */
    public void lap() {
        previousTime = System.currentTimeMillis();
        lapped = true;
    }

    /**
     * 计算计时器开始到现在经过的时间（秒）；自动lap
     *
     * @return 计时器开始到现在经过的时间
     */
    public long gapTime() {
        return gapTime(TimeUnit.SECONDS, true);
    }

    /**
     * 计算计时器开始到现在经过的时间（秒）
     * @param autoLap 是否自动lap
     * @return 计时器开始到现在经过的时间
     */
    public long gapTime(boolean autoLap) {
        return gapTime(TimeUnit.SECONDS, autoLap);
    }


    /**
     * 计算计时器开始到现在经过的时间
     *
     * @param timeUnit 返回时间单位 支持天, 时, 分, 秒, 毫秒
     * @param autoLap  是否自动lap
     * @return 计时器开始到现在经过的时间
     */
    public long gapTime(TimeUnit timeUnit, boolean autoLap) {
        if (!started) {
            throw new IllegalStateException("计时器尚未开始");
        }
        long gap = System.currentTimeMillis() - originalStart;
        if (autoLap) {
            lap();
        }
        return calcTime(timeUnit, gap);
    }

    /**
     * 计算计时器从上次lap到现在经过的时间（秒）；自动lap
     *
     * @return 计时器从上次lap到现在经过的时间
     */
    public long lapTime() {
        return lapTime(TimeUnit.SECONDS, true);
    }

    /**
     * 计算计时器从上次lap到现在经过的时间
     *
     * @param timeUnit 返回时间单位 支持天, 时, 分, 秒, 毫秒
     * @param autoLap  是否自动lap
     * @return 计时器从上次lap到现在经过的时间
     */
    public long lapTime(TimeUnit timeUnit, boolean autoLap) {
        if (!lapped) {
            throw new IllegalStateException("计时器尚未lap");
        }
        long gap = System.currentTimeMillis() - previousTime;
        if (autoLap) {
            lap();
        }
        return calcTime(timeUnit, gap);
    }

    private long calcTime(TimeUnit timeUnit, long gap) {
        switch (timeUnit) {
            case MILLISECONDS:
                return gap;
            case SECONDS:
                return gap / 1000;
            case MINUTES:
                return gap / 1000 / 60;
            case HOURS:
                return gap / 1000 / 60 / 60;
            case DAYS:
                return gap / 1000 / 60 / 60 / 24;
            default:
                throw new IllegalArgumentException("不支持的时间单位");
        }
    }

    public void reset() {
        originalStart = 0;
        previousTime = 0;
        started = false;
        lapped = false;
    }
}
