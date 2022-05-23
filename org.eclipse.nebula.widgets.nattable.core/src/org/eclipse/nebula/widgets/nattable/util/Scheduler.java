/*******************************************************************************
 * Copyright (c) 2012, 2020 Original authors and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.util;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler implements ThreadFactory {

    private final String threadNamePrefix;
    private final AtomicInteger counter = new AtomicInteger();
    private int scheduledTasks;
    private ScheduledExecutorService threadPool;

    public Scheduler(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public synchronized ScheduledFuture<?> schedule(Runnable runnable, long initialDelayMillis) {
        return getThreadPool().schedule(runnable, initialDelayMillis, TimeUnit.MILLISECONDS);
    }

    public synchronized ScheduledFuture<?> scheduleAtFixedRate(
            Runnable runnable, long initialDelayMillis, long refreshIntervalMillis) {
        this.scheduledTasks++;
        return getThreadPool().scheduleAtFixedRate(
                runnable,
                initialDelayMillis,
                refreshIntervalMillis,
                TimeUnit.MILLISECONDS);
    }

    public synchronized ScheduledFuture<?> scheduleWithFixedDelay(
            Runnable runnable, long initialDelayMillis, long refreshIntervalMillis) {
        this.scheduledTasks++;
        return getThreadPool().scheduleWithFixedDelay(
                runnable,
                initialDelayMillis,
                refreshIntervalMillis,
                TimeUnit.MILLISECONDS);
    }

    private synchronized ScheduledExecutorService getThreadPool() {
        if (this.threadPool == null) {
            this.threadPool = Executors.newScheduledThreadPool(1, this);
        }
        return this.threadPool;
    }

    public synchronized void unschedule(ScheduledFuture<?> future) {
        future.cancel(false);
        if (this.threadPool != null && --this.scheduledTasks <= 0) {
            this.threadPool.shutdownNow();
            this.threadPool = null;
        }
    }

    /**
     * Terminate all actively executing tasks and shutdown the scheduler.
     *
     * @since 1.5
     */
    public synchronized void shutdownNow() {
        if (this.threadPool != null) {
            this.threadPool.shutdownNow();
            this.threadPool = null;
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, this.threadNamePrefix + "-" + this.counter.incrementAndGet()); //$NON-NLS-1$
    }

    public synchronized Future<?> submit(Runnable runnable) {
        return getThreadPool().submit(runnable);
    }
}
