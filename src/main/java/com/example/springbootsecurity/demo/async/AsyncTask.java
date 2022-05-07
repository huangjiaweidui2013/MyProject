package com.example.springbootsecurity.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author localuser
 * create at 2022/4/26 16:10
 * @description 学习 jdk 自带异步任务
 */
@Slf4j
public class AsyncTask {
    public static void main(String[] args) throws Exception {
        test2();
    }

    /**
     * 我们使用默认的线程池进行异步调用时，如果异步任务是一个IO密集型，简单说处理时间占用长，
     * 将导致其他使用共享线程池的任务阻塞，造成系统性能下降甚至异常。甚至当一部分调用接口时，
     * 如果接口超时，那么也会阻塞与超时时长相同的时间；实际在计算密集的场景下使用是能提高性能的。
     *
     * @author localuser
     * @date 2022/4/26 16:28  * @return: void
     */
    public static void asyncTaskWithDefault() {
        // 创建10个任务，每个任务阻塞10秒
        for (int i = 0; i < 15; i++) {
            CompletableFuture.runAsync(
                    () -> {
                        try {
                            Thread.sleep(10000);
                            log.info("时间： {} : {}", new Date(), Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通常的线程池接口类ExecutorService，其中execute方法的返回值是void，即无法获取异步任务的执行状态，
     * 而3个重载的submit方法的返回值是Future，可以据此获取任务执行的状态和结果
     *
     * @author localuser
     * @date 2022/4/26 18:03  * @return: void
     */
    public static void test3() throws ExecutionException, InterruptedException {
        // 创建异步执行任务:
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Double> cf = executorService.submit(() -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成,如果已完成则直接返回结果。
        //子线程执行异常时其异常会被捕获，然后修改任务的状态为异常结束并唤醒等待的主线程，get方法判断任务状态发生变更，就终止等待了，并抛出异常。
        //如果执行任务异常，则get方法会把之前捕获的异常重新抛出。
        log.info("run result->" + cf.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    /*    supplyAsync / runAsync    */

    /**
     * supplyAsync表示创建带返回值的异步任务的，相当于ExecutorService submit(Callable<T> task) 方法，
     * runAsync表示创建无返回值的异步任务，相当于ExecutorService submit(Runnable task)方法，这两方法的效果跟submit是一样的
     *
     * @author localuser
     * @date 2022/4/26 18:05  * @return: void
     */
    public static void test2() throws Exception {
        // 创建异步执行任务，有返回值
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * supplyAsync表示创建带返回值的异步任务的，相当于ExecutorService submit(Callable<T> task) 方法，
     * runAsync表示创建无返回值的异步任务，相当于ExecutorService submit(Runnable task)方法，这两方法的效果跟submit是一样的
     *
     * @author localuser
     * @date 2022/4/26 18:05  * @return: void
     */
    @Test
    public void test4() throws Exception {
        // 创建异步执行任务，无返回值
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            }
        });
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * supplyAsync / runAsync 两方法各有一个重载版本，可以指定执行异步任务的Executor实现，如果不指定，默认使用ForkJoinPool.commonPool()，
     * 如果机器是单核的，则默认使用ThreadPerTaskExecutor，该类是一个内部类，每次执行execute都会创建一个新线程。
     *
     * @author localuser
     * @date 2022/4/26 19:20  * @return: void
     */
    @Test
    public void test22() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        }, pool);
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    @Test
    public void test42() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 创建异步执行任务:
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            }
        }, executorService);
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * 异步回调 thenApply / thenApplyAsync
     * thenApply 表示某个任务执行完成后执行的动作，即回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中
     *
     * @author localuser
     * @date 2022/4/26 19:24  * @return: void
     */
    @Test
    public void test5() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        //thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2 = cf.thenApply((result) -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return "test:" + result;
        });
        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread start cf2.get(),time->" + System.currentTimeMillis());
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    @Test
    public void test52() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        //thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2 = cf.thenApplyAsync((result) -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return "test:" + result;
        });
        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread start cf2.get(),time->" + System.currentTimeMillis());
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * thenAccept 同 thenApply ,接收上一个任务的返回值作为参数，但是无返回值；thenRun 的方法没有入参，也没有返回值
     *
     * @author localuser
     * @date 2022/4/26 19:42  * @return: void
     */
    @Test
    public void test6() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        CompletableFuture cf2 = cf.thenApply((result) -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return "test:" + result;
        }).thenAccept((result) -> { //接收上一个任务的执行结果作为入参，但是没有返回值
            log.info(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(result);
            log.info(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
        }).thenRun(() -> { //无入参，也没有返回值
            log.info(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info("thenRun do something");
            log.info(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });
        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("run result->" + cf.get());
        log.info("main thread start cf2.get(),time->" + System.currentTimeMillis());
        //cf2 等待最后一个thenRun执行完成
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * exceptionally方法指定某个任务执行异常时执行的回调方法，会将抛出异常作为参数传递到回调方法中，
     * 如果该任务正常执行则会exceptionally方法返回的CompletionStage的result就是该任务正常执行的结果。
     * 抛出异常后，只有cf2执行了，cf3没有执行。
     *
     * @author localuser
     * @date 2022/4/26 19:57  * @return: void
     */
    @Test
    public void testExceptionally() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        }, pool);
        //cf执行异常时，将抛出的异常作为入参传递给回调方法
        CompletableFuture<Double> cf2 = cf.exceptionally((param) -> {
            log.info(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info("error stack trace->");
            param.printStackTrace();
            log.info(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            return -1.1;
        });
        //cf正常执行时执行的逻辑，如果执行异常则不调用此逻辑
        CompletableFuture cf3 = cf.thenAccept((param) -> {
            log.info(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info("param->" + param);
            log.info(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
        });
        log.info("main thread start,time->" + System.currentTimeMillis());
        //等待子任务执行完成,此处无论是job2和job3都可以实现job2退出，主线程才退出，如果是cf，则主线程不会等待job2执行完成自动退出了
        //cf2.get时，没有异常，但是依然有返回值，就是cf的返回值
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * whenComplete 是当某个任务执行完成后执行的回调方法，会将执行结果或者执行期间抛出的异常传递给回调方法，
     * 如果是正常执行则异常为null。回调方法对应的CompletableFuture的result和该任务一致，如果该任务正常执行，
     * 则get方法返回执行结果，如果是执行异常，则get方法抛出异常。
     *
     * @author localuser
     * @date 2022/4/26 20:00  * @return: void
     */
    @Test
    public void test10() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        //cf执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为null
        CompletableFuture<Double> cf2 = cf.whenComplete((result, exception) -> {
            log.info(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (exception != null) {
                log.info("error stack trace->");
                exception.printStackTrace();
            } else {
                log.info("run succ,result->" + result);
            }
            log.info(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
        });
        //等待子任务执行完成
        log.info("main thread start wait,time->" + System.currentTimeMillis());
        //如果cf是正常执行的，cf2.get的结果就是cf执行的结果
        //如果cf是执行异常，则cf2.get会抛出异常
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * handle 跟 whenComplete 基本一致，区别在于handle的回调方法有返回值，
     * 且handle方法返回的CompletableFuture的result是回调方法的执行结果或者回调方法执行期间抛出的异常，
     * 与原始CompletableFuture的result无关了。
     *
     * @author localuser
     * @date 2022/4/26 20:07  * @return: void
     */
    @Test
    public void test11() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        //cf执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为null
        CompletableFuture<String> cf2 = cf.handle((a, exception) -> {
            log.info(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (exception != null) {
                log.info("error stack trace->");
                exception.printStackTrace();
            } else {
                log.info("run succ,result->" + a);
            }
            log.info(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
            if (exception != null) {
                return "run error";
            } else {
                return "run succ";
            }
        });
        //等待子任务执行完成
        log.info("main thread start wait,time->" + System.currentTimeMillis());
        //get的结果是cf2的返回值，跟cf没关系了
        log.info("run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * 组合处理
     * 1、thenCombine / thenAcceptBoth / runAfterBoth
     * 这三个方法都是将两个CompletableFuture组合起来，只有这两个都正常执行完了才会执行某个任务，
     * 区别在于，thenCombine会将两个任务的执行结果作为方法入参传递到指定方法中，且该方法有返回值；
     * thenAcceptBoth同样将两个任务的执行结果作为方法入参，但是无返回值；runAfterBoth没有入参，
     * 也没有返回值。注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     *
     * @author localuser
     * @date 2022/4/27 10:56  * @return: void
     */
    @Test
    public void test7() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        //cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf3,且有返回值
        CompletableFuture<Double> cf3 = cf.thenCombine(cf2, (a, b) -> {
            log.info(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            log.info("job3 param a->" + a + ",b->" + b);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return a + b;
        });

        //cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf3,无返回值
        CompletableFuture cf4 = cf.thenAcceptBoth(cf2, (a, b) -> {
            log.info(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            log.info("job4 param a->" + a + ",b->" + b);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });

        //cf4和cf3都执行完成后，执行cf5，无入参，无返回值
        CompletableFuture cf5 = cf4.runAfterBoth(cf3, () -> {
            log.info(Thread.currentThread() + " start job5,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            log.info("cf5 do something");
            log.info(Thread.currentThread() + " exit job5,time->" + System.currentTimeMillis());
        });

        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("cf run result->" + cf.get());
        log.info("main thread start cf5.get(),time->" + System.currentTimeMillis());
        log.info("cf3 run result->" + cf3.get());
        log.info("cf4 run result->" + cf4.get());
        log.info("cf5 run result->" + cf5.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * applyToEither / acceptEither / runAfterEither
     * 这三个方法都是将两个CompletableFuture组合起来，只要其中一个执行完了就会执行某个任务，
     * 其区别在于
     * applyToEither 会将已经执行完成的任务的执行结果作为方法入参，并有返回值；
     * acceptEither 同样将已经执行完成的任务的执行结果作为方法入参，但是没有返回值；
     * runAfterEither 没有方法入参，也没有返回值。注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     *
     * @author localuser
     * @date 2022/4/27 11:15
     * @return: void
     */
    @Test
    public void test8() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        //cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf3,且有返回值
        CompletableFuture<Double> cf3 = cf.applyToEither(cf2, (result) -> {
            log.info(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            log.info("job3 param result->" + result);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return result;
        });

        //cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf4,无返回值
        CompletableFuture cf4 = cf.acceptEither(cf2, (result) -> {
            log.info(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            log.info("job4 param result->" + result);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });

        //cf4和cf3都执行完成后，执行cf5，无入参，无返回值
        CompletableFuture cf5 = cf4.runAfterEither(cf3, () -> {
            log.info(Thread.currentThread() + " start job5,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            log.info("cf5 do something");
            log.info(Thread.currentThread() + " exit job5,time->" + System.currentTimeMillis());
        });

        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("cf run result->" + cf.get());
        log.info("main thread start cf5.get(),time->" + System.currentTimeMillis());
        log.info("applyToEither cf3 run result->" + cf3.get());
        log.info("acceptEither cf4 run result->" + cf4.get());
        log.info("cf5 run result->" + cf5.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * thenCompose 方法会在某个任务执行完成后，将该任务的执行结果作为方法入参然后执行指定的方法，
     * 该方法会返回一个新的CompletableFuture实例，如果该CompletableFuture实例的result不为null，
     * 则返回一个基于该result的新的CompletableFuture实例；如果该CompletableFuture实例为null，则，然后执行这个新任务。
     *
     * @author localuser
     * @date 2022/4/27 11:58  void
     * @Exception Exception
     */
    @Test
    public void test9() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<String> cf2 = cf.thenCompose((param) -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return CompletableFuture.supplyAsync(() -> {
                log.info(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                log.info(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
                return "job3 test";
            });
        });
        log.info("main thread start cf.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("cf run result->" + cf.get());
        log.info("main thread start cf2.get(),time->" + System.currentTimeMillis());
        log.info("cf2 run result->" + cf2.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }


    /**
     * allOf返回的CompletableFuture是多个任务都执行完成后才会执行，只有有一个任务执行异常，
     * 则返回的CompletableFuture执行get方法时会抛出异常，如果都是正常执行，则get返回null。
     *
     * @author localuser
     * @date 2022/4/27 13:42  void
     */
    @Test
    public void test12() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            log.info(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        CompletableFuture<Double> cf3 = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
            }
//            throw new RuntimeException("test");
            log.info(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return 2.2;
        });
        //allof 等待所有任务执行完成才执行cf4，如果有一个任务异常终止，则cf4.get时会抛出异常，都是正常执行，cf4.get返回null。
        //anyOf 是只有一个任务执行完成，无论是正常执行或者执行异常，都会执行cf4，cf4.get的结果就是已执行完成的任务的执行结果。
        CompletableFuture cf4 = CompletableFuture.allOf(cf, cf2, cf3).whenComplete((a, b) -> {
            if (b != null) {
                log.info("error stack trace->");
                b.printStackTrace();
            } else {
                log.info("run success,result->" + a);
            }
        });

        log.info("main thread start cf4.get(),time->" + System.currentTimeMillis());
        //等待子任务执行完成
        log.info("cf4 run result->" + cf4.get());
        log.info("main thread exit,time->" + System.currentTimeMillis());
    }

}
