package stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> implements StackInterface<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    private Object[] array; // 요소를 담을 배열
    private int size; // 요소 개수


    // 생성자1 (초기 공간 할당 X)
    public Stack() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자2 (초기 공간 할당 O)
    public Stack(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    private void resize() {

        // 빈 배열인 경우 (capacity is 0)
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        int arrayCapacity = array.length; // 현재 용적 크기.

        if (size == arrayCapacity) { // 용적이 가득 찰 경우.
            int newSize = arrayCapacity * 2;

            array = Arrays.copyOf(array, newSize);
            return;
        }

        if (size < (arrayCapacity / 2)) {
            int newCapacity = arrayCapacity / 2;

            array = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, newCapacity));
        }
    }

    @Override
    public E push(E item) {
        if (size == array.length) {
            resize();
        }
        array[size] = item;
        size++;

        return item;
    }

    @Override
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        E obj = (E) array[size - 1];

        array[size - 1] = null; // 요소 삭제.

        size--; // 사이즈 1 감소.
        resize(); // 용적 재할당.

        return obj;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        return (E) array[size - 1];
    }

    @Override
    public int search(Object value) {
        if (value == null) {
            for (int idx = size-1; idx >= 0; idx--) {
                if (array[idx] == null) {
                    return size - idx;
                }
            }
        } else {
            for (int idx = size - 1; idx >= 0; idx--) {
                if (array[idx].equals(value)) {
                    return size - idx;
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        resize();
    }

    @Override
    public boolean empty() {
        return size == 0;
    }


}
