package com.azhen.java.util.concurrent;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {
    private static final Logger LOG = Logger.getLogger(ThreadPoolExecutorTest.class);
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager var1 = System.getSecurityManager();
            this.group = var1 != null?var1.getThreadGroup():Thread.currentThread().getThreadGroup();
            this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable var1) {
            Thread thread = new Thread(this.group, var1, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(e);
                }
            });
            return thread;
        }
    }

    public static void main(String[] args) {
        // threadExceptionHandler();
        // threadPoolExceptionHandler();
        customHandler();
    }

    public static void customHandler() {
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                 System.out.println("准备执行任务: " + r.toString());
             }
            @Override
             protected void afterExecute(Runnable r, Throwable t) {
                 System.out.println("结束任务: " + r.toString());
            }

            @Override
             protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        service.submit(() -> {
            System.out.println("hello");
        });
    }

    public static void threadPoolExceptionHandler() {
        ThreadFactory threadFactory = new DefaultThreadFactory();
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1,
                        100,
                        1, TimeUnit.MINUTES,
                        new LinkedBlockingQueue<>(),
                        threadFactory,
                        new ThreadPoolExecutor.CallerRunsPolicy());


        Future future = threadPoolExecutor.submit(() -> {
            throw new RuntimeException();
        });
        try {
            future.get();
        } catch (Exception e) {
            // 能捕获异常
            e.printStackTrace();
        }

        try {
            threadPoolExecutor.execute(() -> {
                throw new RuntimeException();
            });
        } catch (Exception e) {
            // 不能捕获异常
            e.printStackTrace();
        }

        try {
            threadPoolExecutor.submit(() -> {
                throw new RuntimeException();
            });
        } catch (Exception e) {
            // 不能捕获异常
            e.printStackTrace();
        }
    }

    public static void threadExceptionHandler() {
        new Thread(() -> {
            try {
                throw new RuntimeException();
            }catch (Exception e) {
                // 可以捕获异常
                e.printStackTrace();
            }
        }).start();

        try {
            new Thread(() -> {
                throw new RuntimeException();
            }).start();
        } catch (Exception e) {
            // 不能捕获异常
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            throw new RuntimeException();
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // 可以捕获异常
                LOG.error(e);
            }
        });
        thread.start();
    }
}
