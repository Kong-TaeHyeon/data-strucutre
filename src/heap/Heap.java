package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private Object[] array;

    public Heap() {
        this(null);
    }

    public Heap(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    public Heap(int capacity) {
        this(capacity, null);
    }

    public Heap(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    private int getParent(int index) {
        return index / 2;
    }

    private int getLeftChild(int index) {
        return index * 2;
    }

    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];

        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

    public void add(E value) {
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }

        siftUp(size + 1, value); // 가장 마지막에 추가되는 위치와 넣을 값(타겟)을 넘겨줌.
        size++; // 정상적으로 재배치가 끝나면 사이즈를 증가.
    }

    public void siftUp(int index, E value) {
        if (comparator != null) {
            siftUpComparator(index, value, comparator);
        }
        else {
            siftUpComparable(index, value);
        }
    }

    private void siftUpComparator(int idx, E target, Comparator<? super E> comparator) {

        // root 노드보다 클 때까지만 탐색한다.
        while (idx > 1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if (comparator.compare(target, (E) parentVal) >= 0) {
                break;
            }

            array[idx] = parentVal;
            idx = parent;
        }

        array[idx] = target;
    }

    private void siftUpComparable(int idx, E target) {

        Comparable<? super E> comp = (Comparable<? super E>) target;

        while (idx > 1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if (comp.compareTo((E) parentVal) >= 0) {
                break;
            }

            array[idx] = parentVal;
            idx = parent;
        }

        array[idx] = comp;
    }

    public E remove(int index) {
        if (array[index] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[index];
        E target;

        if (size == 1) {
            target = null;
        }

        else {
            target = (E) array[size];
        }

        array[size] = null; // 타겟 노드를 비운다.
        siftDown(index, target);

        return result;
    }

    private void siftDown(int index, E target) {
        if (comparator != null) {
            siftDownComparator(index, target, comparator);
        }
        else {
            siftDownComparable(index, target);
        }
    }

    private void siftDownComparator(int idx, E target, Comparator<? super E> comparator) {

        array[idx] = null;
        size--;

        int parent = idx;
        int child;

        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent); // 오른쪽 자식  인덱스.

            Object childVal = array[child];

            if (right <= size && comparator.compare((E) childVal, (E) array[right]) > 0) {
                child = right;
                childVal = array[child];
            }

            if (comparator.compare(target, (E) childVal) <= 0) {
                break;
            }

            array[parent] = childVal;
            parent = child;
        }

        array[parent] = target;

        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    private void siftDownComparable(int idx, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;

        array[idx] = null;
        size--;

        int parent = idx;
        int child;

        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent);

            Object childVal = array[child];

            if (right <= size && ((Comparable<? super E>)childVal).compareTo((E) array[right]) > 0) {
                child = right;
                childVal = array[child];
            }

            if (comp.compareTo((E) childVal) <= 0) {
                break;
            }

            array[parent] = childVal;
            parent = child;
        }

        array[parent] = comp;

        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    public int size() {
        return this.size;
    }

    public E peek() {
        if (array[1] == null) {
            throw new NoSuchElementException();
        }
        return (E) array[1];
    }

    public boolean isEmpty() {
        return size== 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size + 1);
    }
}
