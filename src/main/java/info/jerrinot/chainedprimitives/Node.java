package info.jerrinot.chainedprimitives;

import java.util.List;

final class Node {
    public static final int EMPTY_SLOT = Integer.MAX_VALUE;

    private int value0;
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private int value5;
    private int value6;
    private int value7;

    Node next;
    Node prev;

    Node(int value, Node prev, Node next) {
        this.value0 = value;
        this.value1 = EMPTY_SLOT;
        this.value2 = EMPTY_SLOT;
        this.value3 = EMPTY_SLOT;
        this.value4 = EMPTY_SLOT;
        this.value5 = EMPTY_SLOT;
        this.value6 = EMPTY_SLOT;
        this.value7 = EMPTY_SLOT;
        this.prev = prev;
        this.next = next;
    }

    void transferToList(List<Integer> list) {
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
        } else {
            return;
        }

        if (!isEmpty(value4)) {
            list.add(value4);
        } else {
            return;
        }

        if (!isEmpty(value5)) {
            list.add(value5);
        } else {
            return;
        }

        if (!isEmpty(value6)) {
            list.add(value6);
        } else {
            return;
        }

        if (!isEmpty(value7)) {
            list.add(value7);
        } else {
            return;
        }
    }

    RemoveResult remove(int value) {
        assert value != EMPTY_SLOT;

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
        } else if (value4 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value4 == value) {
            return removeFrom4();
        } else if (value5 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value5 == value) {
            return removeFrom5();
        } else if (value6 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value6 == value) {
            return removeFrom6();
        } else if (value7 > value) {
            return RemoveResult.NOT_FOUND;
        } else if (value7 == value) {
            return removeFrom7();
        }
        return RemoveResult.NOT_FOUND;
    }

    ContainsResult contains(int value) {
        assert value != EMPTY_SLOT;

        if (next != null && next.value0 <= value) {
            return ContainsResult.TRY_NEXT;
        }
        if (value0 == value) {
            return ContainsResult.TRUE;
        } else if (value0 > value) {
            return ContainsResult.FALSE;
        } else if (value1 == value) {
            return ContainsResult.TRUE;
        } else if (value1 > value) {
            return ContainsResult.FALSE;
        } else if (value2 == value) {
            return ContainsResult.TRUE;
        } else if (value2 > value) {
            return ContainsResult.FALSE;
        } else if (value3 == value) {
            return ContainsResult.TRUE;
        } else if (value3 > value) {
            return ContainsResult.FALSE;
        }  else if (value4 == value) {
            return ContainsResult.TRUE;
        } else if (value4 > value) {
            return ContainsResult.FALSE;
        }  else if (value5 == value) {
            return ContainsResult.TRUE;
        } else if (value5 > value) {
            return ContainsResult.FALSE;
        }  else if (value6 == value) {
            return ContainsResult.TRUE;
        } else if (value6 > value) {
            return ContainsResult.FALSE;
        }  else if (value7 == value) {
            return ContainsResult.TRUE;
        } else if (value7 > value) {
            return ContainsResult.FALSE;
        }
        return ContainsResult.FALSE;
    }

    PushResult tryPushValue(int value) {
        assert value != EMPTY_SLOT;

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
        } else if (value < value3) {
            return pushInto3(value);
        } else if (value == value3) {
            return PushResult.ALREADY_EXIST;
        } else if (value < value4) {
            return pushInto4(value);
        } else if (value == value4) {
            return PushResult.ALREADY_EXIST;
        } else if (value < value5) {
            return pushInto5(value);
        } else if (value == value5) {
            return PushResult.ALREADY_EXIST;
        }  else if (value < value6) {
            return pushInto6(value);
        } else if (value == value6) {
            return PushResult.ALREADY_EXIST;
        } else if (value == value7) {
            return PushResult.ALREADY_EXIST;
        } else {
            return pushInto7(value);
        }
    }

    private RemoveResult removeFrom7() {
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom6() {
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom5() {
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom4() {
        value4 = value5;
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom3() {
        value3 = value4;
        value4 = value5;
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom2() {
        value2 = value3;
        value3 = value4;
        value4 = value5;
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom1() {
        value1 = value2;
        value2 = value3;
        value3 = value4;
        value4 = value5;
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return RemoveResult.REMOVED;
    }

    private RemoveResult removeFrom0() {
        value0 = value1;
        value1 = value2;
        value2 = value3;
        value3 = value4;
        value4 = value5;
        value5 = value6;
        value6 = value7;
        value7 = EMPTY_SLOT;
        return isEmpty(value0) ? RemoveResult.NODE_EMPTIED : RemoveResult.REMOVED;
    }

    private PushResult pushInto7(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value;
        return PushResult.OK;
    }

    private PushResult pushInto6(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value;
        return PushResult.OK;
    }

    private PushResult pushInto5(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value;
        return PushResult.OK;
    }

    private PushResult pushInto4(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value4;
        value4 = value;
        return PushResult.OK;
    }

    private PushResult pushInto3(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value4;
        value4 = value3;
        value3 = value;
        return PushResult.OK;
    }

    private PushResult pushInto2(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value4;
        value4 = value3;
        value3 = value2;
        value2 = value;
        return PushResult.OK;
    }

    private PushResult pushInto1(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value4;
        value4 = value3;
        value3 = value2;
        value2 = value1;
        value1 = value;
        return PushResult.OK;
    }

    private PushResult pushInto0(int value) {
        if (!isEmpty(value7)) {
            return PushResult.FULL;
        }
        value7 = value6;
        value6 = value5;
        value5 = value4;
        value4 = value3;
        value3 = value2;
        value2 = value1;
        value1 = value0;
        value0 = value;
        return PushResult.OK;
    }

    private boolean isEmpty(int value) {
        return value == EMPTY_SLOT;
    }

    static void split(Node nodeToSplit) {
        Node newNode = new Node(nodeToSplit.value4, nodeToSplit, nodeToSplit.next);
        Node.PushResult pushResult = newNode.tryPushValue(nodeToSplit.value5);
        assert pushResult == Node.PushResult.OK;
        pushResult = newNode.tryPushValue(nodeToSplit.value6);
        assert pushResult == Node.PushResult.OK;
        pushResult = newNode.tryPushValue(nodeToSplit.value7);
        assert pushResult == Node.PushResult.OK;

        nodeToSplit.value4 = Node.EMPTY_SLOT;
        nodeToSplit.value5 = Node.EMPTY_SLOT;
        nodeToSplit.value6 = Node.EMPTY_SLOT;
        nodeToSplit.value7 = Node.EMPTY_SLOT;
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

    enum RemoveResult {
        TRY_NEXT,
        REMOVED,
        NODE_EMPTIED,
        NOT_FOUND
    }
}
