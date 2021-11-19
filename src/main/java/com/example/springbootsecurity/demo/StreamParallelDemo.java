package com.example.springbootsecurity.demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: StreamParallelDemo
 * @author: HuangLang
 * @description: 学习jdk8的stream相关操作, 并行数据处理与性能
 * @date: 2021-11-17 下午 2:10
 */
public class StreamParallelDemo {
    public static void main(String[] args) {
        System.out.println(measureSumPerf(StreamParallelDemo::rangedSum, 10_000_000));
        System.out.println(measureSumPerf(StreamParallelDemo::parallelRangedSum, 10_000_000));

    }

    /**
     * 将顺序流转换为并行流.
     * 请注意，在现实中，对顺序流调用parallel方法并不意味着流本身有任何实际的变化。它在内部实际上就是设了一个boolean标志，
     * 表示你想让调用parallel之后进行的所有操作都并行执行。类似地，你只需要对并行流调用sequential方法就可以把它变成顺序流。
     * 最后一次parallel或sequential调用会影响整个流水线。
     * 并行流内部使用了默认的ForkJoinPool（java7引入的分支/合并框架），它默认的
     * 线程数量就是你的处理器数量，这个值是由 Runtime.getRuntime().availableProcessors()得到的。
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    //顺序流
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    //并行流
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    //流提供的peek方法在分析Stream流水线时，能将中间变量的值输出到日志中，是非常有用的工具。
    public void peekLog() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        numbers.stream()
                .peek(x -> System.out.println("from stream: " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit: " + x))
                .collect(Collectors.toList()).forEach(System.out::println);


    }
}
