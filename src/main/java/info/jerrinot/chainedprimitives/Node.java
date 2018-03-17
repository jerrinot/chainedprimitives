package info.jerrinot.chainedprimitives;

import java.util.List;

final class Node {
    //todo: introduce a bitmap for empty slots
    private static final int EMPTY_SLOT = Integer.MAX_VALUE;

    private int value0;
    private int value1;
    private int value2;
    private int value3;

    Node next;
    Node prev;

    Node(int value, Node prev, Node next) {
        this.value0 = value;
        this.value1 = EMPTY_SLOT;
        this.value2 = EMPTY_SLOT;
        this.value3 = EMPTY_SLOT;
        this.prev = prev;
        this.next = next;
    }

    enum RemoveResult {
        TRY_NEXT,
        REMOVED,
        NODE_EMPTIED,
        NOT_FOUND
    }

    void addToList(List<Integer> list) {
        // value0 is never empty as we do not keep completely empty nodes
        // so no need to check for emptiness
        list.add(value0);

        if (!isEmpty(value1)) {
            list.add(value1);
        } else {
            return;
        }

        if (!isEmpty(value2)) {
            list.add(value2);
        } else {
            return;
        }

        if (!isEmpty(value3)) {
            list.add(value3);
        }
    }

    RemoveResult remove(int value) {
        if (next != null && next.value0 <= value) {
            return RemoveResult.TRY_NEXT;
        }
        if (value0 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value0 == value) {
            return removeFrom0();
        } else if (value1 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value1 == value) {
            return removeFrom1();
        } else if (value2 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value2 == value) {
            return removeFrom2();
        } else if (value3 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value3 == value) {
            return removeFrom3();
        }
        return RemoveResult.NOT_FOUND;
    }

    RemoveResult removeFrom3() {
        value3 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    RemoveResult removeFrom2() {
        value2 = value3;
        value3 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    RemoveResult removeFrom1() {
        value1 = value2;
        value2 = value3;
        value3 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    RemoveResult removeFrom0() {
        value0 = value1;
        value1 = value2;
        value2 = value3;
        value3 = EMPTY_SLOT;
        return isEmpty(value0) ? RemoveResult.NODE_EMPTIED : RemoveResult.REMOVED;
    }

    ContainsResult contains(int value) {
        if (next != null && next.value0 <= value) {
            return ContainsResult.TRY_NEXT;
        }
        if (value0 == value) {
            return ContainsResult.TRUE;
        } else if (value0 > value || value0 == EMPTY_SLOT) {
            return ContainsResult.FALSE;
        } else if (value1 == value) {
            return ContainsResult.TRUE;
        } else if (value1 > value  || value1 == EMPTY_SLOT) {
            return ContainsResult.FALSE;
        } else if (value2 == value) {
            return ContainsResult.TRUE;
        } else if (value2 > value  || value2 == EMPTY_SLOT) {
            return ContainsResult.FALSE;
        } else if (value3 == value) {
            return ContainsResult.TRUE;
        } else if (value3 > value  || value3 == EMPTY_SLOT) {
            return ContainsResult.FALSE;
        }
        return ContainsResult.FALSE;
    }

    PushResult tryPushValue(int value) {
        if (next != null && next.value0 <= value) {
            return PushResult.NEXT_HAS_SMALLER;
        }
        if (value < value0) {
            return pushInto0(value);
        } else if (value == value0) {
            return PushResult.ALREADY_EXIST;
        } else if (value < value1) {
            return pushInto1(value);
        } else if (value == value1) {
            return PushResult.ALREADY_EXIST;
        } else if (value < value2) {
            return pushInto2(value);
        } else if (value == value2) {
            return PushResult.ALREADY_EXIST;
        } else {
            return pushInto3(value);
        }
    }

    private PushResult pushInto3(int value) {
        if (!isEmpty(value3)) {
            return PushResult.FULL;
        }
        value3 = value;
        return PushResult.OK;
    }

    private PushResult pushInto2(int value) {
        if (!isEmpty(value3)) {
            return PushResult.FULL;
        }
        value3 = value2;
        value2 = value;
        return PushResult.OK;
    }

    private PushResult pushInto1(int value) {
        if (!isEmpty(value3)) {
            return PushResult.FULL;
        }
        value3 = value2;
        value2 = value1;
        value1 = value;
        return PushResult.OK;
    }

    private PushResult pushInto0(int value) {
        if (!isEmpty(value3)) {
            return PushResult.FULL;
        }
        value3 = value2;
        value2 = value1;
        value1 = value0;
        value0 = value;
        return PushResult.OK;
    }

    private boolean isEmpty(int value) {
        return value == EMPTY_SLOT;
    }

    public static void split(Node nodeToSplit) {
        Node newNode = new Node(nodeToSplit.value2, nodeToSplit, nodeToSplit.next);
        Node.PushResult pushResult = newNode.tryPushValue(nodeToSplit.value3);
        assert pushResult == Node.PushResult.OK;

        nodeToSplit.value2 = Node.EMPTY_SLOT;
        nodeToSplit.value3 = Node.EMPTY_SLOT;
        if (nodeToSplit.next != null) {
            Node oldNext = nodeToSplit.next;
            oldNext.prev = newNode;
        }
        nodeToSplit.next = newNode;
    }

    enum ContainsResult {
        TRUE,
        FALSE,
        TRY_NEXT
    }

    enum PushResult {
        OK,
        FULL,
        NEXT_HAS_SMALLER,
        ALREADY_EXIST
    }
}
