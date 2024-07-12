package queue.linkedListDeque;

public class Node<E> {

    E data;
    Node<E> next;
    Node<E> prev;

    Node(E data) {
        this.data = data;
        next = null;
        prev = null;
    }
}
