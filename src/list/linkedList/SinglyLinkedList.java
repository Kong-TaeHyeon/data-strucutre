package list.linkedList;

import list.List;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    private Node<E> head; // Linked List 의 첫 부분.
    private Node<E> tail; // Linked List 의 마지막 부분.
    private int size; // 요소 개수.

    // 생성자.
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    // 특정 위치의 노드를 반환하는 메서드
    private Node<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> x = head;

        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = head;
        head = newNode;
        size++;

        /**
         * 다음에 가리킬 노드가 없는 경우( = 데이터가 새 노드밖에 없는 경우)
         * 따라서, 새 노드가 처음이자 마지막 노드가 된다!
         */
        if (head.next == null) {
            tail = newNode;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        Node<E> newNode = new Node<>(value);

        if (size == 0) {
            addFirst(value);
            return;
        }

        /**
         * 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 하고,
         * tail 이 가리키는 노드를 새 노드로 바꿔준다.
         */
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        // 가장 앞에 추가하는 경우.
        if (index == 0) {
            addFirst(value);
            return;
        }

        // 추가하는 index 가 가장 끝일 경
        if (index == size) {
            addLast(value);
            return;
        }

        // 중간 위치를 삽입하는 경우.
        Node<E> prevNode = search(index - 1);
        Node<E> nextNode = prevNode.next;
        Node<E> newNode = new Node<>(value);

        prevNode.next = newNode;
        newNode.next = nextNode;
        size++;
    }

    // 가장 앞에 요소를 제거.
    public E remove() {
        Node<E> headNode = head;

        if (headNode == null)
            throw new NoSuchElementException();

        // 삭제할 노드를 반환.
        E element = headNode.data;

        Node<E> nextNode = head.next;

        // Head Node 제거.
        head.data = null;
        head.next = null;

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }

        return element;
    }

    @Override
    public E remove(int index) {
        if (index == 0) {
            return remove();
        }

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> prevNode = search(index - 1);
        Node<E> removeNode = prevNode.next;
        Node<E> nextNode = removeNode.next;

        E element = removeNode.data;
        prevNode.next = nextNode;

        if (prevNode.next == null) {
            tail = prevNode;
        }

        // 데이터 삭제.
        removeNode.next = null;
        removeNode.data = null;
        size--;

        return element;
    }

    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        boolean hasValue = false;
        Node<E> x = head;

        while (x != null) {
            if (value.equals(x.data)) {
                hasValue = true;
                break;
            }
            prevNode = x;
            x = x.next;
        }

        if (x == null) {
            return false;
        }

        prevNode.next = x.next;

        if (prevNode.next == null) {
            tail = prevNode;
        }
        x.data = null;
        x.next = null;
        size--;
        return true;
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = value;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
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
            x = nextNode;
        }

        head = tail = null;
        size = 0;
    }
}
