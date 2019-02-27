package com.atsonic.java8test.exer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestStreamApi {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99, Employee.Status.FREE),
            new Employee("李四", 58, 5555.55, Employee.Status.BUSY),
            new Employee("王五", 26, 3333.33, Employee.Status.VOCATION),
            new Employee("赵六", 36, 6666.66, Employee.Status.FREE),
            new Employee("田七", 12, 8888.88, Employee.Status.BUSY),
            new Employee("田七", 12, 4444.44, Employee.Status.FREE)
    );

    /**
     * 1、给定一个数字列表，如何返回一个由每个数的平方构成的列表？
     * 给定[1,2,3,4,5]，应该返回[1,4,9,16,25]
     */
    @Test
    public void test1() {
        Integer[] nums = new Integer[]{1,2,3,4,5};

        Arrays.stream(nums)
                .map(x -> x * x)
                .forEach(System.out::println);
    }

    /**
     * 2、怎样用 map和 reduce方法数一数流中有多少个Employee？
     */
    @Test
    public void test2() {
        Optional<Integer> count = employees.stream()
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(count.get());
    }

}
