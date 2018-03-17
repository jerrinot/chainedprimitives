package info.jerrinot.chainedprimitives;

final class Node {
    int value;
    Node next;
    Node prev;

    Node(int value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
