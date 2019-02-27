package com.atsonic.java8test.exer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream 的三个操作步骤：
 * <p>
 * 1、创建 Stream
 * <p>
 * 2、中间操作
 * <p>
 * 3、终止操作（终端操作）
 */
public class TestStreamAPI2 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 58, 5555.55),
            new Employee("王五", 26, 3333.33),
            new Employee("赵六", 36, 6666.66),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 4444.44)
    );

    // 中间操作
    /**
     * 多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，
     * 否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”
     */

    /**
     * 筛选与切片
     * filter——接受 Lambda，从流中排除某些元素。
     * limit——截断流，使其元素不超过给定数量。
     * skip(n)——跳过元素，返回一个扔掉了前 n个元素的流。若流中元素不足 n个，则返回一个空流。与 limit(n)互补。
     * distinct——筛选，通过流所生成元素的 hashCode()和 equals()去除重复元素
     */

    @Test
    public void test1() {
        // 中间操作：不会执行任何操作
        Stream<Employee> stream = employees.stream()
                                            .filter((e) -> {
//                                                System.out.println("Stream API 的中间操作");
                                                return e.getAge() > 1;
                                            })
//                                            .limit(2)
//                                            .skip(3)
                .distinct() // 需要重写javaBean的equals和hashCode方法
                ;
        // 终止操作：一次性执行全部内容，即“惰性求值”
        // 只有执行到该语句，中间操作才会执行处理
        stream.forEach(System.out::println);
    }

    /**
     * 映射
     * map——接受 Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上
     * 并将其映射成一个新元素。
     * flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     */
    @Test
    public void test2() {

        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        list.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);

        System.out.println("------------------------");

        employees.stream()
                 .map(Employee::getName)
                 .forEach(System.out::println);

        System.out.println("------------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter); // map --> {{a,a,a},{b,b,b},{c,c,c}...}
        stream.forEach(sm -> sm.forEach(System.out::println));

        System.out.println("------------------------");

        Stream<Character> stream2 = list.stream()
                .flatMap(TestStreamAPI2::filterCharacter); // flatMap --> {a,a,a,b,b,b,c,c,c...}
        stream2.forEach(System.out::println);



    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for(Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     * sort()——自然排序(Comparable)
     * sort(Comparator com)——定制排序(Comparator)
     */
    @Test
    public void test3() {
        List<String> list = Arrays.asList("ccc", "bbb", "ddd", "aaa", "eee");

        list.stream()
            .sorted()
            .forEach(System.out::println);

        System.out.println("------------------------");

        // 先按age排序，age相同则按name排序
        employees.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge() == e2.getAge()) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return Integer.compare(e1.getAge(), e2.getAge());
                    }
                }).forEach(System.out::println);

    }


}
