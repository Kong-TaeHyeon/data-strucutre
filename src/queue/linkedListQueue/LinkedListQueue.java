package queue.linkedListQueue;

import queue.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedListQueue<E> implements Queue<E>, Cloneable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {
        Node<E> newNode = new Node<>(value);

        if (size == 0) { // 비어있는 경우.
            head = newNode;
        }

        else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E element = head.data;

        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        head = nextNode;
        size--;

        return element;
    }

    public E remove() {

        E element = poll();

        if (element == null) {
            throw new NoSuchElementException();
        }

        return element;
    }

    @Override
    public E peek() {

        if (size == 0) {
            return null;
        }

        return head.data;
    }

    public E element() {
        E element = peek();

        if (element == null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        /**
         * head 데이터부터 x 가 null 이 될 때까지 value 랑 x 의 데이터 (x.data) 랑
         * 같은지를 비교하고 같을 경우 true 를 반환한다.
         */

        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (Node<E> x = head; x != null;) {
            Node<E> next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }

        size = 0;
        head = tail = null;
    }


    // 추가 구현
    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            array[idx++] = (E) x.data;
        }

        return array;
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;

        // 얕은 복사를 위한 s 배열.
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        return a;
    }

    public Object clone() {
        try {
            LinkedListQueue<E> clone = (LinkedListQueue<E>) super.clone();
            clone.head = null;
            clone.tail = null;
            clone.size = 0;


            for (Node<E> x = head; x != null; x = x.next) {
                clone.offer(x.data);
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sort() {
        sort(null);
    }

    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int i = 0;
        // 배열 => Queue 로 복사.
        for (Node<E> x = head; x != null; x = x.next, i++) {
            x.data = (E) a[i];
        }
    }
}
