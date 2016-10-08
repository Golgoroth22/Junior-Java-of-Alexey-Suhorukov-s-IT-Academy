package ru.itacademy.bakery;

/**
 * Created by асер on 25.08.2016.
 */
public class Baker implements Runnable {
    private String name;

    public Baker(int name) {
        this.name = name + "";
    }

    @Override
    public void run() {
        while (true) {
            try {
                cooking();
            } catch (Exception e) {
            }
        }
    }

    private void cooking() throws InterruptedException {
        Bakery bakery = Bakery.getOurBakery();
        Bread bread = new Bread();
        Thread.sleep(5000);
        if (bakery.getOurQueue().offer(bread)) {
            System.out.println("Baker №" + name + " create bread and added to Queue. Breads in queue : " + (bakery.getOurQueue().size()));
        } else {
            System.out.println("Queue is full. Waiting for visitor.");
            bakery.getOurQueue().put(bread);
            System.out.println("Baker №" + name + " create bread and added to Queue. Breads in queue : " + (bakery.getOurQueue().size()));
        }
    }
}
