package info.jerrinot.chainedprimitives;

import java.util.ArrayList;
import java.util.List;

public final class IntSortedSet {
    private Node first;

    public int[] toArray() {
        List<Integer> tmpList = new ArrayList<>();
        Node current = first;
        while (current != null) {
            current.addToList(tmpList);
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

    public boolean contains(int value) {
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

}
