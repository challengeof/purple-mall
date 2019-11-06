package net.caidingke.chenxi.algorithm;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author bowen
 */
public class Test {

    private static int fic(int n) {
        if (n <= 2) {
            return 1;
        }
        return fic(n - 2) + fic(n - 1);
    }

    public static void main(String[] args) {
//        for (int i = 1; i <= 10; i++) {
//            System.out.print(fic(i) + " ");
//        }
//        ficb(100);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add("1");
        list.add("2");
        list1.add("1");
        list1.add("3");

        Multiset<String> set = HashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("b");
        set.add("b");
        set.add("c");
        System.out.println(set.count("a"));
        for (String s : set) {
            System.out.println(s);
        }
        list.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s + "a";
            }
        });
//        list1.retainAll(list);
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println(list1.indexOf("3"));
    }

    private static void ficb(int i) {

        int a = 0;
        int b = 1;
        int m;
        do {
            m = a + b;
            a = b;
            b = m;
            System.out.print(a + " ");
        } while (m <= i);
    }

}
