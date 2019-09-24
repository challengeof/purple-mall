package net.caidingke.common.test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * @author bowen
 */
public class Test {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);


    public static void main(String[] args) {
        String[] array = new String[]{"1", "@", "2"};
        System.out.println(A.valueOf("B").a("b"));
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());

        Multimap<String, String> map = LinkedListMultimap.create();
        map.put("a", "a");
//        map.put("a", "b");
        map.put("b", "c");
        HashMultiset set = HashMultiset.create();
        set.add("a");
        set.add("a");
        System.out.println(set);
        for (Object o : set) {
            System.out.println("aa");
            System.out.println(o);
        }
        List<Order> list = new ArrayList<>();
        list.add(new Order(1L, "bowen"));
        list.add(new Order(2L, "b"));
        Multimap<Long, Order> multimap = Multimaps.index(list, Order::getId);
        Collection<Order> orders = multimap.get(1L);
        for (Order order : orders) {
            System.out.println(order.getName());
        }

        Map<Long, List<Order>> map1 = list.stream().collect(groupingBy(Order::getId));
        List<Order> list1 = map1.get(1L);
        for (Order order : list1) {
            System.out.println(order.getName());
        }

        Map<Long, Order> a = list.stream()
                .collect(toMap(Order::getId, java.util.function.Function.identity()));

        list.stream().filter(o -> o.getId() == 1).mapToLong(Order::getId).sum();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Order {

        private Long id;
        private String name;
    }


}
