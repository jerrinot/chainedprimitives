package info.jerrinot.chainedprimitives;

import java.util.ArrayList;
import java.util.List;

public final class IntSortedSet {
    private Node first;
    private boolean hasMaxInt;

    //for debugging only
    void print() {
        Node current = first;
        while (current != null) {
            current.print();
            current = current.next;
        }
    }

    public int[] toArray() {
        List<Integer> tmpList = new ArrayList<>();
        Node current = first;
        while (current != null) {
            current.transferToList(tmpList);
            current = current.next;
        }
        if (hasMaxInt) {
            tmpList.add(Integer.MAX_VALUE);
        }
        int[] arr = new int[tmpList.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = tmpList.get(i);
        }
        return arr;
    }

    public boolean remove(int value) {
        if (value == Node.EMPTY_SLOT) {
            boolean oldValue = hasMaxInt;
            hasMaxInt = false;
            return oldValue;
        }

        Node current = first;
        if (current == null) {
            return false;
        }
        for (;;) {
            Node.RemoveResult res = current.remove(value);
            switch (res) {
                case TRY_NEXT:
                    current = current.next;
                    break;
                case REMOVED:
                    return true;
                case NODE_EMPTIED:
                    unlinkEmptyNode(current);
                    return true;
                case NOT_FOUND:
                    return false;
            }
        }
    }

    public boolean contains(int value) {
        if (value == Node.EMPTY_SLOT) {
            return hasMaxInt;
        }

        Node current = first;
        if (current == null) {
            return false;
        }
        for (;;) {
            Node.ContainsResult containsResult = current.contains(value);
            switch (containsResult) {
                case TRUE:
                    return true;
                case FALSE:
                    return false;
                case TRY_NEXT:
                    current = current.next;
            }
        }
    }

    public void add(int value) {
        if (value == Node.EMPTY_SLOT) {
            hasMaxInt = true;
            return;
        }

        Node current = first;
        if (current == null) {
            first = new Node(value, null, null);
            return;
        }

        for (;;) {
            Node.PushResult pushResult = current.tryPushValue(value);
            switch (pushResult) {
                case OK:
                    return;
                case FULL:
                    Node.split(current);
                    break;
                case NEXT_HAS_SMALLER:
                    current = current.next;
                    break;
                case ALREADY_EXIST:
                    return;
            }
        }
    }

    private void unlinkEmptyNode(Node node) {
        if (node.prev == null) {
            Node next = node.next;
            if (next != null) {
                next.prev = null;
            }
            first = next;
        } else if (node.next == null) {
            Node prev = node.prev;
            prev.next = null;
        } else {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
        }

    }

}
