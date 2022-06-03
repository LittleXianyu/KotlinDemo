package com.example.myapplication.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ScriptTest {
    public static final void main1(String[] args) {
        HashMap<Integer, Integer> hashMap = new HashMap();
        hashMap.put(1, 2);
        hashMap.put(1, 2);
        hashMap.put(2, 3);
        hashMap.put(3, 3);
        Integer integer = new Integer(1);
        System.out.println("hashcode: " + integer.hashCode());
        if (hashMap.containsKey(3)) {
            System.out.println("3 hashcode: " + hashMap.get(1));
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println("hashcode ==> : " + entry.getKey().hashCode());
        }
    }

    public static final void main(String[] args) {
        InnerThread innerThread = new InnerThread();
//        for(int i=0;i<3;i++){
//            new Thread(() -> {
//                innerThread.testConsume();
//            }).start();
//        }
//        for(int i=0;i<3;i++){
//            new Thread(() -> {
//                innerThread.testProduct();
//            }).start();
//        }
        LinkedBlockingQueueTest linkedBlockingQueueTest = new LinkedBlockingQueueTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                linkedBlockingQueueTest.testProduct();
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                linkedBlockingQueueTest.testConsume();
            }).start();
        }


        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class InnerThread {
        // 大于 0 是生产
        volatile Integer integer = 0;

        public void testProduct() {
            if (integer == 3) { // 最多三个，等待消费
                try {
                    System.out.println("testProduct start wait");

                    wait();
                    System.out.println("testProduct end wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + ", testProduct start sleep");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                integer++;
                // 最小化同步代码块，但是integer对象的操作还是需要同步的，用volatile
                synchronized (this) {

                    notify();
                    System.out.println(Thread.currentThread().getName() + ", testProduct call notify");

                    // 需要释放InnerThread锁对象，才会执行wait后的代码，所以这里sleep结束后wait才会执行
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        public synchronized void testConsume() {
            if (integer == 0) {
                try {
                    System.out.println("testConsume start wait");
                    wait();
                    System.out.println("testConsume end consume ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                integer--;
                notify();

            }
        }
    }

    public static class LinkedBlockingQueueTest {
        volatile LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(2);

        public  void testProduct() {
            try {
                String random = "put: " + Math.random();
                linkedBlockingQueue.put(random);
                System.out.println(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public  void testConsume() {
            try {
                System.out.println("take: " + linkedBlockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
