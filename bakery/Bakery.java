package ru.itacademy.bakery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by асер on 24.08.2016.
 */
public class Bakery {
    private static Bakery ourBakery = new Bakery();
    public static List<Thread> threads = new ArrayList<>();
    private static int BAKERS = 1;
    private static int VISITORS = 3;

    private final ArrayBlockingQueue<Bread> ourQueue = new ArrayBlockingQueue<Bread>(5);

    public static Bakery getOurBakery() {
        return ourBakery;
    }

    public synchronized ArrayBlockingQueue<Bread> getOurQueue() {
        return ourQueue;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Bakery start working.");
        Thread.sleep(2000);
        for (int i = 0; i < BAKERS; i++) {
            Baker baker = new Baker(i + 1);
            Thread bakerThread = new Thread(baker);
            threads.add(bakerThread);
            bakerThread.start();
        }
        for (int i = 0; i < VISITORS; i++) {
            Visitor visitor = new Visitor(i + 1);
            Thread visitorThread = new Thread(visitor);
            threads.add(visitorThread);
            visitorThread.start();
            Thread.sleep(9000);
        }
    }
}
