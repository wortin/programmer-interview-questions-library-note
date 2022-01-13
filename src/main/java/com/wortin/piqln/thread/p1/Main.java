package com.wortin.piqln.thread.p1;

public class Main {

    public static void main(String[] args) {
        MyRunnable myRunnableA = new MyRunnable(20, "Ali", 0);
        MyRunnable myRunnableB = new MyRunnable(20, "baba\n", 1);
        Thread threadA = new Thread(myRunnableA, "A");
        Thread threadB = new Thread(myRunnableB, "B");
        threadA.start();
        threadB.start();
    }

    public static class MyRunnable implements Runnable {

        private int exeCount;

        private String printContent;

        private int order;


        private static boolean isStart = false;

        public MyRunnable(int exeCount, String printContent, int order) {
            this.exeCount = exeCount;
            this.printContent = printContent;
            this.order = order;
        }

        public void run() {
            while (true) {
                synchronized (MyRunnable.class) {
                    if (!isStart && order != 0) {
                        try {
                            MyRunnable.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isStart = true;
                    MyRunnable.class.notify();
                    if (exeCount-- == 0) return;
                    System.out.print(printContent);
                    try {
                        MyRunnable.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}