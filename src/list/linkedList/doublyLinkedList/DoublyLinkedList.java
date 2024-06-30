package list.linkedList.doublyLinkedList;

import list.List;

import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    public Node<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index + 1 > size / 2) {
            Node<E> x = tail;

            for (int i = size - 1; i > index; i--) {
                x = x.previous;
            }
            return x;
        }

        else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;

        /**
         * 기존에 head 가 존재하는 경우.
         */
        if (head != null) {
            head.previous = newNode;
        }

        /**
         * head 를 삽입한 데이터로 변경.
         */
        head = newNode;
        size++;

        /**
         * 데이터가 아예 존재하지 않는 경우.
         */
        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);

        if (size == 0) { // 노드가 존재하지 않는 경우.
            addFirst(e);
        }
        else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
            size++;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(element);
        }

        else if (index == size) {
            addLast(element);
        }

        else {
            Node<E> newNode = new Node<>(element);

            Node<E> search = search(index); // 추가하려는 위치의 노드.
            Node<E> searchBefore = search.previous; // 추가하려는 위치의 이전 노드.

            // 연결 끊기.
            search.previous = null;
            searchBefore.next = null;

            // 새로운 연결.
            newNode.next = search;
            newNode.previous = searchBefore;

            search.previous = newNode;
            searchBefore.next = newNode;

            // 사이즈 증가.
            size++;
        }
    }


    public E remove() { // 가장 앞에 있는 요소 제거.
        Node<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E element = headNode.data;
        Node<E> nextNode = headNode.next;
        head.data = null;
        head.next = null;

        if (nextNode != null) {
            nextNode.previous = null;
        }

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }

        return element;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            E element = remove();
            return element;
        }

        Node<E> prevNode = search(index -1);
        Node<E> removedNode = prevNode.next;
        Node<E> nextNode = removedNode.next;

        E element = removedNode.data;

        prevNode.next = null;
        removedNode.next = null;
        removedNode.previous = null;
        removedNode.data = null;

        if (nextNode != null) {
            nextNode.previous = prevNode;
            prevNode.next = nextNode;
        }

        else {
            tail = prevNode;
        }

        size--;

        return element;
    }

    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        Node<E> x = head;

        for (; x != null; x = x.next) {
            if (value.equals(x.data)) {
                break;
            }
            prevNode = x;
        }

        if (x == null) {
            return false;
        }

        // 삭제하려는 노드가 head 인 경우.
        if (x.equals(head)) {
            remove();
        }

        else {
            Node<E> nextNode = x.next;

            if (nextNode != null) {
                nextNode.previous = prevNode;
                prevNode.next = nextNode;
            }
            else {
                tail = prevNode;
            }

            size--;
        }
        return true;
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E element) {
        Node<E> replaceNode = search(index);
        replaceNode.data = element;
    }

    @Override
    public int indexOf(Object o) {  // 정방향 탐색
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (o.equals(x.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int index = size - 1;
        for (Node<E> x = tail; x != null; x = x.previous) {
            if (o.equals(x.data)) {
                return index;
            }
            index--;
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (Node<E> x = head; x != null;) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x.previous = null;
            x = nextNode;
        }

        head = tail = null;
        size = 0;
    }
}
