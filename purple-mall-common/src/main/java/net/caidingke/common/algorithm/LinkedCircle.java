package net.caidingke.common.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author bowen
 */
public class LinkedCircle {



    @Getter
    @Setter
    private static class Node {

        private String data;

        private Node next;

        public Node(String data) {
            this.data = data;
        }
    }

    private static boolean isCircle(Node node) {

        HashSet<String> data = new HashSet<>();

        while (node.next != null) {
            if (!data.add(node.data)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private static boolean isCircleStep(Node node) {
        Node p1 = node;
        Node p2 = node;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (Objects.equals(p1.data, p2.data)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Node node = new Node("1");
        Node node1 = new Node("2");
        Node node2 = new Node("5");
        Node node3 = new Node("4");
        Node node4 = new Node("6");
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;

        System.out.println(isCircle(node));
        System.out.println(isCircleStep(node));
    }
}
