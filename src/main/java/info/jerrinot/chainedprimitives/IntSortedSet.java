package info.jerrinot.chainedprimitives;

import java.util.ArrayList;
import java.util.List;

public final class IntSortedSet {
    private Node first;

    public int[] toArray() {
        List<Integer> tmpList = new ArrayList<>();
        Node current = first;
        while (current != null) {
            tmpList.add(current.value);
            current = current.next;
        }
        int[] arr = new int[tmpList.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = tmpList.get(i);
        }
        return arr;
    }

    public boolean remove(int value) {
        Node current = first;
        while (current != null) {
            if (current.value == value) {
                removeNode(current);
                return true;
            }
            if (current.value > value) {
                return false;
            }
            current = current.next;
        }
        return false;
    }

    private void removeNode(Node current) {
        if (current.prev == null) {
            //removing the first node -> shift everything to left

            first = current.next;
            if (first != null) {
                first.prev = null;
            }
        } else if (current.next == null) {
            //last node

            Node prev = current.prev;
            //prev is never null as otherwise the branch above would kick-in
            prev.next = null;
        } else {
            Node prev = current.prev;
            Node next = current.next;
            prev.next = next;
            next.prev = prev;
        }
    }

    public boolean contains(int value) {
        Node current = first;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            if (current.value > value) {
                return false;
            }
            current = current.next;
        }
        return false;
    }

    public void add(int value) {
        Node current = first;
        Node prev = null;
        for (;;) {
            if (current == null) {
                insertBetween(value, prev, null);
                return;
            }

            if (current.value == value) {
                //already exist
                return;
            }

            if (current.value > value) {
                insertBetween(value, prev, current);
                return;
            }

            prev = current;
            current = current.next;
        }
    }

    private void insertBetween(int value, Node prev, Node next) {
        if (prev == null) {
            //first node
            first = new Node(value, null, next);
        } else if (next == null) {
            //last node
            prev.next = new Node(value, prev, null);
        } else {
            Node newNode = new Node(value, prev, next);
            prev.next = newNode;
            next.prev = newNode;
        }
    }
}
