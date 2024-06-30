package list.linkedList.doublyLinkedList;

public class Node<E> {
    Node<E> next;
    Node<E> previous;
    public E data;

    public Node(E data) {
        this.data = data;
        next = null;
        previous = null;
    }
}
