package git.mechanician.task;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName Test
 * @Author GitDatSanvich
 * @Date 2019/8/27 21:01
 **/
public class Test {
    public static void main(String[] args) {

        ConcurrentLinkedQueue<Integer> linkedQueue = new ConcurrentLinkedQueue<>();

        Thread thread1 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                System.out.print("加!" + i);
                linkedQueue.offer(i);
            }
        });
        Thread thread2 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                if (!linkedQueue.isEmpty()) {
                    Integer poll = linkedQueue.poll();
                    System.out.println(poll);
                } else {
                    System.err.println("空了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                if (!linkedQueue.isEmpty()) {
                    Integer poll = linkedQueue.poll();
                    System.out.println(poll);
                } else {
                    System.err.println("空了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });
        Thread thread4 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                if (!linkedQueue.isEmpty()) {
                    Integer poll = linkedQueue.poll();
                    System.out.println(poll);
                } else {
                    System.err.println("空了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });
        Thread thread5 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                if (!linkedQueue.isEmpty()) {
                    Integer poll = linkedQueue.poll();
                    System.out.println(poll);
                } else {
                    System.err.println("空了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });
        Thread thread6 = new Thread(() -> {
            int i = 1;
            while (true) {
                i++;
                if (!linkedQueue.isEmpty()) {
                    Integer poll = linkedQueue.poll();
                    System.out.println(poll);
                } else {
                    System.err.println("空了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });
        thread3.start();
        thread2.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread1.start();
    }
}
