package list;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10; // 최소(기본) 배열 크기.
    private static final Object[] EMPTY_ARRAY = {}; // 빈 배열.

    private int size; // 요소 개수.

    Object[] array; // 요소를 담을 배열.

    // 생성자 1 (초기 공간 할당 X)
    public ArrayList() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자 2 (초기 공간 할당 O)
    public ArrayList(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    /**
     * 들어오는 데이터의 개수에 따라 '최적화'된 배열 크기를 갖어야 한다.
     * 이를 위해 요소의 개수에 따라 배열의 크기를 변경하기 위한 함수.
     */
    private void resize() {
       int array_capacity = array.length;

       // 배열의 용량이 0인 경우.
       if (Arrays.equals(array, EMPTY_ARRAY)) {
           array = new Object[DEFAULT_CAPACITY];
           return;
       }

       // 용량이 가득 찬 경우.
       if (size == array_capacity) {
           int new_capacity = array_capacity * 2;

           // copy
           array = Arrays.copyOf(array, new_capacity);
           return;
       }

       // 공간이 낭비되고 있는 경우.
       if (size < array_capacity / 2) {
           int new_capacity = array_capacity / 2;
           array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
       }
    }

    /**
     * 배열의 마지막 부분에 값을 추가한다.
     * @param value 마지막 부분에 추가할 요소의 값.
     */
    public void addLast(E value) {
        if (size == array.length) {
            resize();
        }
        array[size++] = value;
        size++;
    }


    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }


    @Override
    public void add(int index, E value) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            addLast(value);
        }

        else {
            if (size == array.length) { // 가득 차 있는 경우, 크기 재할당.
                resize();
            }

            // index 기준 후자에 있는 모든 요소를 한 칸씩 뒤로 밀기.
            for (int i = size; i > index; i--) {
                array[i] = array[i-1];
            }

            array[index] = value;
            size++;
        }

    }

    public void addFirst(E value) {
        add(0, value);
    }


    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E value = (E) array[index];

        for (int i = index; i < size -1; i++) {
            array[i] = array[i+1];
        }
        size--;
        resize();

        return value;
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }


    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) array[index];
    }


    @Override
    public void set(int index, E value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        array[index] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {

        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        // 모든 공간을 명시적으로 null 처리 해준다.
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;
        resize();
    }

    @Override
    public int indexOf(Object value) {

        int i = 0;
        for (i = 0; i < size; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        // 일차히는 것이 없을 경우.
        return -1;
    }

    public int lastIndexOf(Object value) {

        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public boolean contains(Object value) {

        if (indexOf(value) >= 0) {
            return true;
        }

        return false;
    }
}
