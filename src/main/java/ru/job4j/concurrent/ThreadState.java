package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));

        first.start();
        second.start();
        
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first.getState() " + first.getState());
            System.out.println("second.getState()" + second.getState());
        }
        System.out.println("Work done!");
    }
}
