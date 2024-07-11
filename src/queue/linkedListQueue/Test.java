package queue.linkedListQueue;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {

        LinkedListQueue<Integer> original = new LinkedListQueue<>();
        original.offer(10);

        LinkedListQueue<Integer> copy = original;
        LinkedListQueue<Integer> clone = (LinkedListQueue) original.clone();

        copy.offer(20);
        clone.offer(30);

        System.out.println("original LinkedListQueue");
        int i = 0;

        for (Object a : original.toArray()) {
            System.out.println(i + "번 째 data = " + a);
            i++;
        }

        System.out.println("\ncopy LinkedListQueue");
        i = 0;

        for (Object a : copy.toArray()) {
            System.out.println(i + "번 째 data = " + a);
            i++;
        }

        System.out.println("\nclone LinkedListQueue");
        i = 0;

        for (Object a : clone.toArray()) {
            System.out.println(i + "번 째 data = " + a);
            i++;
        }

        System.out.println("\noriginal LinkedListQueue reference : " + original);
        System.out.println("\ncopy LinkedListQueue reference : " + copy);
        System.out.println("\nclone LinkedListQueue reference : " + clone);


        // 정렬 테스트
        LinkedListQueue<Student> q = new LinkedListQueue<>();
        q.offer(new Student("학생1", 10));
        q.offer(new Student("학생2", 40));
        q.offer(new Student("학생3", 20));
        q.offer(new Student("학생4", 30));

        q.sort();

        for (Object a : q.toArray()) {
            System.out.println(a);
        }
    }
}

class Student implements Comparable<Student> {
    String name;
    int score;

    Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String toString() {
        return "이름 : " + name + "\t성적 : " + score;
    }

    @Override
    public int compareTo(Student o) {
        return score - o.score;
    }
}
