package com.example.springbootsecurity.demo;

import com.example.springbootsecurity.demo.entity.Dish;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: StreamDemo
 * @author: HuangLang
 * @description: 学习jdk8的stream相关操作
 * @date: 2021-11-16 上午 10:38
 */
public class StreamDemo {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
//        pythagoreanTriples();
//        createStream();

        groupByStream();

//        streamReduce();
    }

    /**
     * 创建流
     */
    static void createStream() {
        //1.由值创建流
        Stream<String> stream1 = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream1.map(String::toUpperCase).forEach(System.out::println);
        //使用empty得到一个空流
        Stream<String> emptyStream = Stream.empty();

        //2.由数组创建流
        int[] numbers = {2, 4, 6, 8, 10};
        System.out.println(Arrays.stream(numbers).sum());

        //3.由文件生成流
        /*Java中用于处理文件等I/O操作的NIO API（非阻塞 I/O）已更新，以便利用Stream API。
        java.nio.file.Files中的很多静态方法都会返回一个流。例如，一个很有用的方法是
        Files.lines，它会返回一个由指定文件中的各行构成的字符串流。使用你迄今所学的内容，
        你可以用这个方法看看一个文件中有多少各不相同的词：*/
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4.由函数生成流：创建无限流
        /*Stream API提供了两个静态方法来从函数生成流：Stream.iterate和Stream.generate。
        这两个操作可以创建所谓的无限流：不像从固定集合创建的流那样有固定大小的流。由iterate
        和generate产生的流会用给定的函数按需创建值，因此可以无穷无尽地计算下去！一般来说，
        应该使用limit(n)来对这种流加以限制，以避免打印无穷多个值。*/
        //4.1 迭代 iterate
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
        //斐波纳契元组序列
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20).map(t -> t[0]).forEach(System.out::println);

        //4.1 生成 generate
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

    }

    /**
     * reduce 归约操作（将流归约成一个值）
     */
    static void streamReduce() {
        List<Integer> numbers = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Optional<Integer> reduce1 = numbers.stream().reduce(Integer::sum);
        System.out.println(reduce1.orElse(0));

        Integer reduce2 = numbers.stream().reduce(100, Integer::sum);
        System.out.println(reduce2);

        Optional<Integer> reduce3 = numbers.stream().reduce((a, b) -> a * b);
        System.out.println(reduce3.orElse(0));

        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        System.out.println(max.orElse(0));

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        System.out.println(min.orElse(0));

        long count = numbers.stream().count();
        System.out.println(count);

        /**
         * Java 8引入了三个原始类型特化流接口来解决这个问题：IntStream、DoubleStream和
         * LongStream，分别将流中的元素特化为int、long和double，从而避免了暗含的装箱成本。每
         * 个接口都带来了进行常用数值归约的新方法，比如对数值流求和的sum，找到最大元素的max。
         * 此外还有在必要时再把它们转换回对象流的方法。要记住的是，这些特化的原因并不在于流的复杂性，
         * 而是装箱造成的复杂性——即类似int和Integer之间的效率差异。
         */
        List<CollectorsGroupingBy.Apple> apples = CollectorsGroupingBy.appleList();
        //1. 映射到数值流
        int sum = apples.stream().mapToInt(CollectorsGroupingBy.Apple::getNum).sum();
        System.out.println(sum);

        IntStream intStream = apples.stream().mapToInt(CollectorsGroupingBy.Apple::getNum);
        //2. 转换回对象流
        Stream<Integer> boxed = intStream.boxed();
        Integer boxSum = boxed.reduce(0, Integer::sum);
        System.out.println(boxSum);

        //3. 默认值OptionalInt    Optional原始类型特化版本：OptionalInt、OptionalDouble和OptionalLong
        OptionalInt optionalInt = apples.stream().mapToInt(CollectorsGroupingBy.Apple::getNum).max();
        int orElseMax = optionalInt.orElse(0);
        System.out.println(orElseMax);

        //数值范围
        IntStream intStream1 = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        System.out.println(intStream1.count());
    }

    /**
     * 计算勾股数 3,4,5
     */
    static void pythagoreanTriples() {
        Stream<int[]> pythagoreanTriples1 = IntStream.rangeClosed(1, 100).boxed().flatMap(a ->
                IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
        );
        pythagoreanTriples1.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        //上述解决办法并不是最优的，因为你要求两次平方根。让代码更为紧凑的一种可能的方法是，
        // 先生成所有的三元数(a*a, b*b, a*a+b*b)，然后再筛选符合条件的.
        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed().flatMap(a -> IntStream.rangeClosed(a, 100).mapToObj(
                        b -> new double[]{a, b, Math.sqrt(a * a + b * b)}
                ).filter(t -> t[2] % 1 == 0));
        pythagoreanTriples2.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    /**
     * 流收集
     */
    static void collectStream() {
        List<Dish> menu = getDishList();

        //1.归约
        long howManyDishes = menu.stream().count();
        System.out.println(howManyDishes);

//        Optional<Dish> collect = menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        Optional<Dish> collect = menu.stream().max(Comparator.comparing(Dish::getCalories));
        System.out.println(collect.orElse(null));

        //2.汇总
        Integer collect1 = menu.stream().collect(summingInt(Dish::getCalories));
//        Integer collect1 = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(collect1);
        //平均数
        Double collect2 = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(collect2);
        //想要得到两个或更多这样的结果，而且希望只需一次操作就可以完成。在这种情况下，可以使用summarizingInt工厂
        //方法返回的收集器
        IntSummaryStatistics collect3 = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(collect3.getMax() + ", " + collect3.getMin() + ", " + collect3.getAverage() + ","
                + collect3.getCount() + ", " + collect3.getSum());

        //3.连接字符串
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(shortMenu);

    }

    /**
     * 分组
     */
    static void groupByStream() throws JsonProcessingException {
        List<Dish> menu = getDishList();
        Map<Dish.Type, List<Dish>> collect1 = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(mapper.writeValueAsString(collect1));
        //注意: 从源码可以得知,普通的单参数 groupingBy(f)（其中f是分类函数）实际上是 groupingBy(f, toList()) 的简便写法
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect2 = menu.stream().collect(groupingBy(Dish::getType,
                groupingBy(d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                })
        ));
        System.out.println(mapper.writeValueAsString(collect2));
        System.out.println(collect2);

        Map<Dish.Type, Map<CaloricLevel, Long>> collect3 = menu.stream().collect(groupingBy(Dish::getType,
                groupingBy(d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, counting())
        ));
        System.out.println(mapper.writeValueAsString(collect3));

        Map<Dish.Type, Optional<Dish>> collect4 = menu.stream().collect(groupingBy(Dish::getType,
                maxBy(Comparator.comparingInt(Dish::getCalories)))
        );
        System.out.println(mapper.writeValueAsString(collect4));

        //1. 把收集器的结果转换为另一种类型
        //这个Optional::get操作放在这里是安全的，因为reducing收集器永远都不会返回Optional.empty()。
        Map<Dish.Type, Dish> collect5 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(
                maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get))
        );
        System.out.println(mapper.writeValueAsString(collect5));

        //2. 与groupingBy联合使用的其他收集器的例子
        //一般来说，通过groupingBy工厂方法的第二个参数传递的收集器将会对分到同一组中的所有流元素执行进一步归约操作.
        Map<Dish.Type, Integer> collect6 = menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));
        System.out.println(mapper.writeValueAsString(collect6));

        //常常和groupingBy联合使用的另一个收集器是mapping方法生成的。这个方法接受两个参数：一个函数对流中的元素做变换，
        // 另一个则将变换的结果对象收集起来。其目的是在累加之前对每个输入元素应用一个映射函数，这样就可以让接受特定类型元素
        // 的收集器适应不同类型的对象。
        Map<Dish.Type, Set<CaloricLevel>> collect7 = menu.stream().collect(groupingBy(Dish::getType, mapping(
                d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toSet())));
        System.out.println(mapper.writeValueAsString(collect7));

        //在上一个示例中，对于返回的Set是什么类型并没有任何保证。但通过使用toCollection，你就可
        //以有更多的控制。例如，你可以给它传递一个构造函数引用来要求HashSet：
        Map<Dish.Type, Set<CaloricLevel>> collect8 = menu.stream().collect(groupingBy(Dish::getType, mapping(
                d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                    else
                        return CaloricLevel.FAT;
                }, toCollection(HashSet::new))));
        System.out.println(mapper.writeValueAsString(collect8));

        /***************************************** 分区 **************************************/
        //分区是分组的特殊情况：由一个谓词（返回一个布尔值的函数）作为分类函数，它称分区函数。分区函数返回一个布尔值，这意味着得到的
        //分组Map的键类型是Boolean，于是它最多可以分为两组: true是一组，false是一组。
        Map<Boolean, List<Dish>> collect9 = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(mapper.writeValueAsString(collect9));

        Map<Boolean, Map<Dish.Type, List<Dish>>> collect10 = menu.stream().collect(partitioningBy(Dish::isVegetarian,
                groupingBy(Dish::getType)));
        System.out.println(mapper.writeValueAsString(collect10));

        Map<Boolean, Dish> collect11 = menu.stream().collect(partitioningBy(Dish::isVegetarian,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mapper.writeValueAsString(collect11));
    }

    /**
     * 判断数字是否是质数 :true 质数, false 非质数
     *
     * @param candidate
     * @return true 质数, false 非质数
     */
    static boolean isPrime(int candidate) {
        //一个简单的优化是仅测试小于等于待测数平方根的因子
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    /**
     * 前n个数字分为质数和非质数
     *
     * @param n
     * @return
     */
    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    static List<Dish> getDishList() {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        return menu;
    }

    public enum CaloricLevel {DIET, NORMAL, FAT}
}

/*
                                            表5-1 中间操作和终端操作
        操 作             类 型                 返回类型        使用的类型/函数式接口             函数描述符
        filter          中间                      Stream<T>       Predicate<T>            T -> boolean
        distinct        中间(有状态-无界)          Stream<T>
        skip            中间(有状态-有界)          Stream<T>       long
        limit           中间(有状态-有界)          Stream<T>       long
        map             中间                      Stream<R>       Function<T, R> T -> R
        flatMap         中间                      Stream<R>       Function<T, Stream<R>>  T -> Stream<R>
        sorted          中间(有状态-无界)          Stream<T>       Comparator<T>           (T, T) -> int
        anyMatch        终端                      boolean         Predicate<T>            T -> boolean
        noneMatch       终端                      boolean         Predicate<T>            T -> boolean
        allMatch        终端                      boolean         Predicate<T>            T -> boolean
        findAny         终端                      Optional<T>
        findFirst       终端                      Optional<T>
        forEach         终端                      void            Consumer<T>             T -> void
        collect         终端                      R               Collector<T, A, R>
        reduce          终端 (有状态-有界)         Optional<T>      BinaryOperator<T>      (T, T) -> T
        count           终端                      long
        */
