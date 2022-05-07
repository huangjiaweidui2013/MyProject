package com.example.springbootsecurity.demo.guava;

import com.google.common.base.Optional;
import com.google.common.base.*;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.base.CharMatcher;

import java.util.*;

/**
 * @author localuser
 * create at 2022/4/24 10:47
 * @description 学习 guava 开源工具的一些用法
 */
@Slf4j
public class GuavaLearn {
    public static void main(String[] args) {
        testBiMap();
        testImmutableCollection2();
    }

    /**
     * Immutable特点
     * 1.在多线程操作下，是线程安全的。
     * <p>
     * 2.所有不可变集合会比可变集合更有效的利用资源。
     * <p>
     * 3.中途不可改变
     * 输出：
     * iList: [a, b, c]
     * iSet: [e1, e2]
     * iMap: {k1=v1, k2=v2}
     */
    public static void testImmutableCollection() {
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");
        log.info("iList: {}", iList);
        log.info("iSet: {}", iSet);
        log.info("iMap: {}", iMap);
    }

    /**
     * guava提供了很多Immutable集合，比如ImmutableList/ImmutableSet/ImmutableSortedSet/ImmutableMap/……
     * 注意 Immutable vs unmodifiable 的区别
     * 输出：
     * 源list的总数：3, 不可变iList的总数：2
     *
     * @author localuser
     * @date 2022/4/25 17:37  * @return: void
     */
    public static void testImmutableCollection2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> ofList = Collections.unmodifiableList(list);
        ImmutableList<String> copyOfList = ImmutableList.copyOf(list);
        list.add("c");
        log.info("源 list 的总数：{}, 不可变 ofList 的总数：{}, 不可变 copyOfList 的总数：{}",
                list.size(), ofList.size(), copyOfList.size());

    }

    /**
     * Multiset 是无序可以重复的集合，它就是游离在List/Set之间
     *
     * @author localuser
     * @date 2022/4/25 17:07  * @return: void
     */
    public static void testMultiSet() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("moon1");
        multiset.add("moon1");
        multiset.add("sun2");
        multiset.add("sun2");
        multiset.add("sun2");
        multiset.add("Mars");
        multiset.add("Mars");
        log.info("总数：{}, 其中一个元素数量：{}", multiset.size(), multiset.count("sun2"));
    }

    /**
     * JDK提供给我们的Map是一个键，一个值，一对一的，那么在实际开发中，显然存在一个KEY多个VALUE的情况。
     * 友情提示下，guava所有的集合都有create方法，这样的好处在于简单，而且我们不必在重复泛型信息了。
     * get()/keys()/keySet()/values()/entries()/asMap()都是非常有用的返回view collection的方法。
     * Multimap的实现类有：ArrayListMultimap/HashMultimap/LinkedHashMultimap/TreeMultimap/ImmutableMultimap/……
     *
     * @author localuser
     * @date 2022/4/25 18:28 * @param null:  * @return: null
     */
    public static void testMultiMap() {
        Multimap<String, Integer> integerMultimap = ArrayListMultimap.create();
        integerMultimap.put("key1", 1);
        integerMultimap.put("key1", 2);
        integerMultimap.put("key1", 3);
        integerMultimap.put("key2", 4);
        integerMultimap.put("key2", 4);
        integerMultimap.put("key2", 4);
        Collection<Integer> collection1 = integerMultimap.get("key1");
        collection1.forEach(i -> log.info("collection1 : " + i));
        Collection<Integer> collection2 = integerMultimap.get("key2");
        collection2.forEach(i -> log.info("collection2 : " + i));
    }

    /**
     * 双向Map，key 和 value 都是唯一的：BiMap
     * biMap.inverse() != biMap ；biMap.inverse().inverse() == biMap
     *
     * @author localuser
     * @date 2022/4/26 14:20 * @param null:  * @return: null
     */
    public static void testBiMap() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("li", "China");
        biMap.put("katty", "America");
        biMap.put("Tom", "Canada");
        log.info("正向，根据key查找value：{}; 反向，根据value查找key：{}", biMap.get("li"), biMap.inverse().get("China"));
    }


    /**
     * Table涉及到3个概念：rowKey,columnKey,value，并提供了多种视图以及操作方法让你更加轻松的处理多个KEY的场景。
     *
     * @author localuser
     * @date 2022/4/26 14:38 * @param null:  * @return: null
     */
    public static void testTable() {
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("li", "China", 90);
        table.put("katty", "America", 80);
        table.put("Tom", "Canada", 85);
        Set<Table.Cell<String, String, Integer>> cellSet = table.cellSet();
        for (Table.Cell<String, String, Integer> cell : cellSet) {
            log.info("row key: {}, column key: {}, cell value: {}",
                    cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
        Set<String> rowKeySet = table.rowKeySet();
        Set<String> columnKeySet = table.columnKeySet();
        Collection<Integer> values = table.values();
        log.info("rowKeySet: {}, columnKeySet: {}, values: {}", rowKeySet, columnKeySet, values);
    }

    /**
     * Guava 字符串连接器Joiner
     * 输出：嗨，jim|jack|kevin
     */
    public static void testJoiner() {
        StringBuilder stringBuilder = new StringBuilder("Hi, ");
        // 字符串连接器，以|为分隔符，同时去掉null元素
        Joiner joiner = Joiner.on("|").skipNulls();
        // 构成一个字符串jim|jack|kevin并添加到stringBuilder
        stringBuilder = joiner.appendTo(stringBuilder, "jim", null, "jack", null, "kevin");
        log.info("stringBuilder: {}", stringBuilder);
    }

    /**
     * Guava MapToString
     * 输出：Cookies:12332#Content-Length:30000#Date:2018.07.04#Mime:text/html
     */
    public static void testMapToString() {
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Cookies", "12332");
        testMap.put("Content-Length", "30000");
        testMap.put("Date", "2018.07.04");
        testMap.put("Mime", "text/html");
        // 用:分割键值对，并用#分割每个元素，返回字符串
        String join = Joiner.on("#").withKeyValueSeparator(":").join(testMap);
        log.info("testMapToString -> {}", join);
    }

    /**
     * Guava StringToMap
     * 输出：
     * Cookies -> 12332
     * Content-Length -> 30000
     * Date -> 2018.07.04
     * Mime -> text/html
     */
    public void testStringToMap() {
        // 接上一个，内部类的引用，得到分割器，将字符串解析为map
        Splitter.MapSplitter mapSplitter = Splitter.on("#").withKeyValueSeparator(":");
        Map<String, String> map = mapSplitter.split("Cookies:12332#Content-Length:30000#Date:2018.07.04#Mime:text/html");
        map.forEach((k, v) -> log.info("map -> ({}, {})", k, v));
    }

    /**
     * Guava 字符串工具类Strings
     * 输出：
     * true
     * true
     * false
     * helloTTT
     */
    public void testStrings() {
        log.info(String.valueOf(Strings.isNullOrEmpty(""))); // true
        log.info(String.valueOf(Strings.isNullOrEmpty(null))); // true
        log.info(String.valueOf(Strings.isNullOrEmpty("hello"))); // false
        // 将null转化为""
        log.info(Strings.nullToEmpty(null)); // ""

        // 从尾部不断补充T只到总共8个字符，如果源字符串已经达到或操作，则原样返回。类似的有padStart
        log.info(Strings.padEnd("hello", 8, 'T')); // helloTTT

    }

    /**
     * collapseFrom 匹配到的字符坍塌替换
     * trimAndCollapseFrom 修剪两边空格，再做坍塌替换
     * trimFrom 去空格
     * trimLeadingFrom 左边去空格
     * trimTrailingFrom右边去空格
     *
     * @date 2022/4/24 13:56
     * <p>
     * * @return: void
     */
    public void collapseFrom() {
        String input = "    ting feng    ";
        String result1 = CharMatcher.breakingWhitespace().collapseFrom(input, '*');
        log.info(result1);    //*ting*feng*

        String result2 = CharMatcher.breakingWhitespace().trimAndCollapseFrom(input, '*');
        log.info(result2);    //ting*feng

        String result3 = CharMatcher.is(' ').trimAndCollapseFrom(input, '*');
        log.info(result3);    //同上 ting*feng

        String result4 = CharMatcher.breakingWhitespace().trimFrom(input);
        log.info(result4);    //ting feng

        String result5 = CharMatcher.breakingWhitespace().trimLeadingFrom(input);
        log.info(result5);    //ting feng

        String result6 = CharMatcher.breakingWhitespace().trimTrailingFrom(input);
        log.info(result6);    //    ting feng
    }

    // is 匹配参数之内的字符
    // isNot 匹配参数之外的字符
    // javaIsoControl 匹配java转义字符
    // ascii 匹配 Ascii
    // negate 返回与当前CharMatcher相反的CharMatcher
    public void is_isNot_negate_ascii() {
        String input = "a, c, z, 1, 2";
        log.info(CharMatcher.is(',').retainFrom(input));      //,,,,
        log.info(CharMatcher.isNot(',').retainFrom(input));   //a c z 1 2

        log.info(CharMatcher.javaIsoControl().removeFrom("ab\tcd\nef\bg"));    //abcdefg
        log.info(CharMatcher.ascii().retainFrom("あH*el.lo,}12"));     //H*el.lo,}12
        log.info(CharMatcher.ascii().negate().retainFrom("あH*el.lo,}12"));    //あ
    }

    // and 两个 Matcher 同时匹配
    // any anyOf 匹配所有/其中任意字符
    // none noneOf 不匹配所有/其中任何字符,与any()相反
    // 判断  * matchesAllOf 判断sequence所有字符是否都被charMatcher匹配
    //      * matchesAnyOf 判断sequence中是否存在字符被charMatcher匹配
    //      * matchesNoneOf 判断sequence所有字符是否都没被charMatcher匹配
    public void and_() {
        String input = "H*el.lo,}12";
        CharMatcher charMatcher1 = CharMatcher.forPredicate(Character::isLetterOrDigit);
        CharMatcher charMatcher2 = CharMatcher.forPredicate(Character::isLowerCase);
        String result = charMatcher1.and(charMatcher2).retainFrom(input);
        log.info(result);     //ello

        log.info(CharMatcher.any().retainFrom(input));            //H*el.lo,}12
        log.info(CharMatcher.anyOf("Hel").retainFrom(input));     //Hell

        log.info(CharMatcher.none().retainFrom(input));           //
        log.info(CharMatcher.noneOf("Hel").retainFrom(input));    //*.o,}12

        log.info(String.valueOf(CharMatcher.is(',').matchesAllOf(input)));        //false
        log.info(String.valueOf(CharMatcher.is(',').matchesAnyOf(input)));        //true
        log.info(String.valueOf(CharMatcher.is(',').matchesNoneOf(input)));       //false
    }

    public static void testPrecondition(String name, int age, Map<String, String> extInfo) {
        Preconditions.checkNotNull(name, "name must be not null!");
        Preconditions.checkArgument(age >= 18, "your age is under 18!");
        Map<String, String> defaultExtInfo = Maps.newHashMap();
        defaultExtInfo.put("sex", "man");
        extInfo = Optional.fromNullable(extInfo).or(defaultExtInfo);
//        extInfo = Optional.ofNullable(extInfo).orElse(defaultExtInfo);
        for (Map.Entry<String, String> entry : extInfo.entrySet()) {
            log.info(entry.getKey() + " : " + entry.getValue());
        }
    }


}
