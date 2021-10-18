package com.example.springbootsecurity.demo;

import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: Java8DateTime
 * @author: HuangLang
 * @description: java8 的新时间日期
 * @date: 2021-06-21 上午 11:51
 */
@Slf4j
public class Java8DateTime {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    public static void main(String[] args) {
//        java8DateTimeDemo();
//        convertDateTime();

//        LocalDateTime localDateTime = LocalDate.now().plusDays(2).atStartOfDay().minusSeconds(1);
//        System.out.println(localDateTime);
//        LocalDateTime localDateTime1 = LocalDate.now().plusDays(-2).atStartOfDay();
//        String format = fmt.format(localDateTime1);
//        System.out.println(format);

        getDayOfWeek();
    }

    public static void java8DateTimeDemo() {
        // 获取当前时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("localDate: {}, localTime: {}, localDateTime: {}", localDate, localTime, localDateTime);

        // 根据指定日期/时间创建对象
        LocalDate localDate1 = LocalDate.of(2020, 6, 23);
        LocalTime localTime1 = LocalTime.of(9, 15, 36);
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 10, 15, 4, 15, 20);
        log.info("localDate1: {}, localTime1: {}, localDateTime1: {}", localDate1, localTime1, localDateTime1);

        /*日期时间的加减
        对于LocalDate,只有精度大于或等于日的加减，如年、月、日；
        对于LocalTime,只有精度小于或等于时的加减，如时、分、秒、纳秒；
        对于LocalDateTime,则可以进行任意精度的时间相加减；*/
        //以下方法的参数都是long型，返回值都是LocalDateTime
        LocalDateTime plusYearsResult = localDateTime.plusYears(2L);
        LocalDateTime plusMonthsResult = localDateTime.plusMonths(3L);
        LocalDateTime plusDaysResult = localDateTime.plusDays(7L);
        LocalDateTime plusHoursResult = localDateTime.plusHours(2L);
        LocalDateTime plusMinutesResult = localDateTime.plusMinutes(10L);
        LocalDateTime plusSecondsResult = localDateTime.plusSeconds(10L);

        System.out.println("当前时间是 : " + localDateTime + "\n"
                + "当前时间加2年后为 : " + plusYearsResult + "\n"
                + "当前时间加3个月后为 : " + plusMonthsResult + "\n"
                + "当前时间加7日后为 : " + plusDaysResult + "\n"
                + "当前时间加2小时后为 : " + plusHoursResult + "\n"
                + "当前时间加10分钟后为 : " + plusMinutesResult + "\n"
                + "当前时间加10秒后为 : " + plusSecondsResult + "\n"
        );

        //也可以以另一种方式来相加减日期，即plus(long amountToAdd, TemporalUnit unit)
        //参数1 ： 相加的数量， 参数2 ： 相加的单位
        LocalDateTime nextMonth = localDateTime.plus(1, ChronoUnit.MONTHS);
        LocalDateTime nextYear = localDateTime.plus(1, ChronoUnit.YEARS);
        LocalDateTime nextWeek = localDateTime.plus(1, ChronoUnit.WEEKS);

        System.out.println("now : " + localDateTime + "\n"
                + "nextYear : " + nextYear + "\n"
                + "nextMonth : " + nextMonth + "\n"
                + "nextWeek :" + nextWeek + "\n"
        );

        //将年、月、日等修改为指定的值，并返回新的日期（时间）对象
        //当前时间基础上，指定本年当中的第几天，取值范围为1-365,366
        LocalDate withDayOfYearResult = localDate.withDayOfYear(200);
        //当前时间基础上，指定本月当中的第几天，取值范围为1-29,30,31
        LocalDate withDayOfMonthResult = localDate.withDayOfMonth(5);
        //当前时间基础上，直接指定年份
        LocalDate withYearResult = localDate.withYear(2017);
        //当前时间基础上，直接指定月份
        LocalDate withMonthResult = localDate.withMonth(5);
        System.out.println("当前时间是 : " + localDate + "\n"
                + "指定本年当中的第200天 : " + withDayOfYearResult + "\n"
                + "指定本月当中的第5天 : " + withDayOfMonthResult + "\n"
                + "直接指定年份为2017 : " + withYearResult + "\n"
                + "直接指定月份为5月 : " + withMonthResult + "\n"
        );

        //获取日期的年月日周时分秒
        int dayOfYear = localDateTime.getDayOfYear();
        int dayOfMonth = localDateTime.getDayOfMonth();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println("今天是" + localDateTime + "\n"
                + "本年当中第" + dayOfYear + "天" + "\n"
                + "本月当中第" + dayOfMonth + "天" + "\n"
                + "本周中星期" + dayOfWeek.getValue() + "-即" + dayOfWeek + "\n");

        //获取当天时间的年月日时分秒
        int year = localDateTime.getYear();
        Month month = localDateTime.getMonth();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        System.out.println("今天是" + localDateTime + "\n"
                + "年 ： " + year + "\n"
                + "月 ： " + month.getValue() + "-即 " + month + "\n"
                + "日 ： " + day + "\n"
                + "时 ： " + hour + "\n"
                + "分 ： " + minute + "\n"
                + "秒 ： " + second + "\n"
        );

        //时间日期前后的比较与判断
        LocalDate localDate3 = LocalDate.of(2017, 8, 8);
        LocalDate localDate4 = LocalDate.of(2018, 8, 8);
        boolean date1IsBeforeDate2 = localDate3.isBefore(localDate4);
        System.out.println("date1IsBeforeDate2 : " + date1IsBeforeDate2);

        //判断是否为闰年
        boolean leapYear = localDate.isLeapYear();
        System.out.println("now : " + localDate + ", is leap year ? " + leapYear);

        //java8时钟 ： clock()
        //返回当前时间，根据系统时间和UTC
        Clock clock = Clock.systemUTC();
        // 运行结果： SystemClock[Z]
        System.out.println(clock);

        /**
         * 时间戳
         * 事实上Instant就是java8以前的Date，
         * 可以使用以下两个类中的方法在这两个类型之间进行转换，
         * 比如Date.from(Instant)就是用来把Instant转换成java.util.date的，
         * 而new Date().toInstant()就是将Date转换成Instant的
         */
        Instant instant = Instant.now();
        System.out.println(instant);
        Date date = Date.from(instant);
        Instant instant1 = date.toInstant();
        System.out.println(date);
        System.out.println(instant1);

        /**
         * 计算时间、日期间隔
         * Duration:用于计算两个“时间”间隔
         * Period:用于计算两个“日期”间隔
         */
        //计算两个日期的日期间隔-年月日
        LocalDate date1 = LocalDate.of(2018, 2, 13);
        LocalDate date2 = LocalDate.of(2017, 3, 12);
        //内部是用date2-date1，所以得到的结果是负数
        Period period = Period.between(date1, date2);
        System.out.println("相差年数 ： " + period.getYears());
        System.out.println("相差月数 ： " + period.getMonths());
        System.out.println("相差日数 ： " + period.getDays());
        //还可以这样获取相差的年月日
        System.out.println("-------------------------------");
        long years = period.get(ChronoUnit.YEARS);
        long months = period.get(ChronoUnit.MONTHS);
        long days = period.get(ChronoUnit.DAYS);
        System.out.println("相差的年月日分别为 ： " + years + "," + months + "," + days);
        //注意，当获取两个日期的间隔时，并不是单纯的年月日对应的数字相加减，而是会先算出具体差多少天，在折算成相差几年几月几日的

        //计算两个时间的间隔
        System.out.println("-------------------------------");
        LocalDateTime date3 = LocalDateTime.now();
        LocalDateTime date4 = LocalDateTime.of(2018, 1, 13, 22, 30, 10);
        Duration duration = Duration.between(date3, date4);
        System.out.println(date3 + " 与 " + date4 + " 间隔  " + "\n"
                + " 天 :" + duration.toDays() + "\n"
                + " 时 :" + duration.toHours() + "\n"
                + " 分 :" + duration.toMinutes() + "\n"
                + " 毫秒 :" + duration.toMillis() + "\n"
                + " 纳秒 :" + duration.toNanos() + "\n"
        );
        //注意，并没有获得秒差的，但既然可以获得毫秒，秒就可以自行获取了

        /**
         * 当计算程序的运行时间时，应当使用时间戳Instant
         */
        Instant ins1 = Instant.now();
        for (int i = 0; i < 10000000; i++) {
            //循环一百万次
        }
        Instant ins2 = Instant.now();
        Duration duration2 = Duration.between(ins1, ins2);
        System.out.println("程序运行耗时为 ： " + duration2.toMillis() + "毫秒");
    }


    public void dateTimeFormatter() {
        //1. 使用jdk自身配置好的日期格式
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE_TIME;
        //2. 使用自定义格式
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.now();
        //反过来调用也可以 : date1.format(formatter1);
        String date1Str = formatter1.format(date1);
        String date1Str2 = formatter1.format(date1);
        System.out.println(date1Str);
        System.out.println(date1Str2);

        /**
         * 将时间字符串形式转化为日期对象
         */
        String datetime = "2018-01-13 21:27:30";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(datetime, dtf);
        System.out.println(ldt);

        /**
         * 将时间日期对象转为格式化后的时间日期对象
         */
        LocalDateTime ldt1 = LocalDateTime.now();
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String temp = dtf1.format(ldt1);
        LocalDateTime formatterDateTime = LocalDateTime.parse(temp, dtf1);
        System.out.println(formatterDateTime);

        /**
         *long毫秒值转换为日期
         */
        System.out.println("---------long毫秒值转换为日期---------");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneId.of("Asia/Shanghai")));
        System.out.println(format);

    }

    public static void convertDateTime() {
        // Date 转 LocalDate
        Date date = new Date();
        //ofInstant 方法从1.9才有
        LocalDate localDate1 = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate localDate2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Date 转 ZonedDateTime
        ZonedDateTime zonedDateTime1 = date.toInstant().atZone(ZoneId.systemDefault());
        System.out.println("localDate1: " + localDate1 + " ,localDate2: " + localDate2);
        System.out.println("zonedDateTime1: " + zonedDateTime1);

        //
        LocalDateTime localDateTime = zonedDateTime1.toLocalDateTime();


        // LocalDate 转 Date
        LocalDate localDate3 = LocalDate.now();
        ZonedDateTime zonedDateTime = localDate3.atStartOfDay(ZoneId.systemDefault());
        Date date1 = Date.from(zonedDateTime.toInstant());
        System.out.println(date1);

        Instant instant = date.toInstant();

    }

    public static void getDayOfWeek() {
        Date date = new Date();
        LocalDate localDate1 = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate localDate2 = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()).plus(5, ChronoUnit.DAYS);
        DayOfWeek dayOfWeek1 = localDate1.getDayOfWeek();
        DayOfWeek dayOfWeek2 = localDate2.getDayOfWeek();
        System.out.println("1今天星期几: " + dayOfWeek1.getValue());
        System.out.println("2今天星期几: " + dayOfWeek2.getValue());

    }

}
/**
 * LocalDateTime,Date:
 * <p>
 * 没有(无法)包含时区(本地时间,隐式的指默认时区ZoneId.systemDefault)信息,其实就是本地时间,即没有包含时区信息,就像我们平时互相说的时间日期一样,
 * 意味着如果需要转换为其他有时区的时间或者转换为其他时区的时候需要传入时区,这里的传入的时区一般就是指ZoneId.systemDefault()系统默认时区,所以很多函数转换时不传,会去取默认时区(ZoneId
 * .systemDefault())了
 * ————————————————
 * 版权声明：本文为CSDN博主「hank009」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/huang007guo/article/details/104019643
 */
