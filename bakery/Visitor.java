package ru.itacademy.bakery;

/**
 * Created by асер on 24.08.2016.
 */
public class Visitor implements Runnable {
    private String name;
    private int canEat = 4;

    public Visitor(int name) {
        this.name = name + "";
    }

    @Override
    public void run() {
        Bakery bakery = Bakery.getOurBakery();
        System.out.println("Visitor №" + name + " entered the bakery.");

        while (canEat != 0) {
            if (bakery.getOurQueue().poll() != null) {
                canEat--;
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Visitor №" + name + " eat one bread. Can eat: " + canEat + ". Breads in queue : " + bakery.getOurQueue().size());
            } else {
                try {
                    System.out.println("Queue is empty. Waiting!!! Visitor №" + name + " is hungry!");
                    bakery.getOurQueue().take();
                    canEat--;
                    System.out.println("Visitor №" + name + " eat one bread. Can eat: " + canEat + ". Breads in queue : " + bakery.getOurQueue().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Visitor №" + name + " left the bakery.");
    }
}
