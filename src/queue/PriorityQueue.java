package queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E> implements Queue<E>{

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] array;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    public PriorityQueue(int capacity) {
        this(capacity, null);
    }

    public PriorityQueue(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    // 받은 인덱스의 부모 노드 인덱스를 반환
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

    @Override
    public boolean offer(E value) {
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }

        // 가장 마지막에 추가되는 위치와 넣을 값
        siftUp(size + 1, value);
        size++;
        return true;
    }

    private void siftUp(int index, E target) {
        if (comparator != null) {
            siftUpComparator(index, target, comparator);
        }

        else {
            siftUpComparable(index, target);
        }
    }

    private void siftUpComparator(int index, E target, Comparator<? super E> comparator) {

        while (index > 1) {
            int parent = getParent(index);
            Object parentVal = array[parent];

            // 타겟 노드 우선순위(값)이 부모노드보다 작으면 반복문 종료.
            if (comparator.compare(target, (E) parentVal) < 0) {
                break;
            }

            array[index] = parentVal;
            index = parent;
        }

        array[index] = target;
    }

    private void siftUpComparable(int index, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;

        while (index > 1) {
            int parent = getParent(index);
            Object parentVal = array[parent];

            if (comp.compareTo((E) parentVal) < 0) {
                break;
            }

            array[index] = parentVal;
            index = parent;
        }
        array[index] = comp;
    }

    @Override
    public E poll() {
        if (array[1] == null) {
            return null;
        }

        return remove();
    }

    public E remove() {
        if (array[1] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[1];
        E target;

        if (size == 1) {
            target = null;
        }

        else {
            target = (E) array[size];
        }

        array[size] = null;
        size--;
        siftDown(1, target);

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

    private void siftDownComparator(int index, E target, Comparator<? super E> comparator) {

        array[index] = null; // 삭제할 인덱스의 노드를 삭제.

        int parent = index;
        int child;

        while ((child = getLeftChild(parent)) <= size) {

            int right = getRightChild(parent);
            Object childVal = array[child];

            if (right <= size && comparator.compare((E) childVal, (E) array[right]) < 0) {
                child = right;
                childVal = array[child];
            }

            if (comparator.compare(target, (E) childVal) > 0) {
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

    private void siftDownComparable(int index, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;

        array[index] = null;

        int parent = index;
        int child;

        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent);
            Object childVal = array[child];

            if (right <= size && ((Comparable<? super E>)childVal).compareTo((E)array[right]) < 0) {
                child = right;
                childVal = array[child];
            }

            if (comp.compareTo((E) childVal) > 0) {
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
        return size;
    }

    @Override
    public E peek() {
        if (array[1] == null) {
            throw new NoSuchElementException();
        }
        return (E) array[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        for (int i = 1; i <= size; i++) {
            if (array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
    }
}
