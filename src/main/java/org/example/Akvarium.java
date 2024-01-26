package org.example;

import java.util.*;

public class Akvarium {
    private static Akvarium akvarium;
    private volatile Koordinat koordinat;
    private volatile Vector<Baliq> baliqlar;
    private  int capacity;
    public volatile static int number = 1;
    public static Random random;



    private Akvarium() {
    }
    public static Akvarium getAkvarium() {
        if (akvarium == null) {
            akvarium = new Akvarium();
        }
        return akvarium;
    }

    public void start() {
        random = new Random();
        this.baliqlar = new Vector<>();
        this.koordinat = new Koordinat(random.nextInt(14) + 1
                , random.nextInt(14) + 1, random.nextInt(14) + 1);
        this.capacity=koordinat.getX()* koordinat.getY()* koordinat.getZ();
        int n = random.nextInt(19) + 1;
        int m = random.nextInt(19) + 1;

        while ((n+m)>(capacity)){
            n = random.nextInt(19) + 1;
            m = random.nextInt(19) + 1;
        }
        for (int i = 0; i < n; i++) {
            addBaliq(true);
        }
        for (int i = 0; i < m; i++) {
            addBaliq(false);
        }
        Iterator<Baliq> iterator = getBaliqlar().iterator();
        while (iterator.hasNext()) {
            Baliq element = iterator.next();
            element.setName("fish " + Akvarium.number++);
            element.start();
        }
        while (true) {
            if (getBaliqlar().size() >= capacity) {
                getBaliqlar().forEach(Thread::stop);
                System.out.println("sigim kopayib ketdi  ");
                break;
            }
            akvarium.print();
            if (!isCollision()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        }
    }
    public synchronized void print() {
        long male = getBaliqlar().stream().filter(Baliq::getGender).count();
        long female = getBaliqlar().stream().filter(e -> !e.getGender()).count();
        System.out.println("**********************");
        System.out.println("Total amount fish = " + getBaliqlar().size());
        System.out.println("Male Fish = " + male);
        System.out.println("Female Fish = " + female);
        System.out.println("**********************");
    }

    
    public synchronized void addBaliq(boolean gender) {
        Baliq baliq = new Baliq(gender, random.nextInt(19) + 1);
        baliq.setKoordinat(new Koordinat(random
                .nextInt(this.koordinat.getX()), random
                .nextInt(this.koordinat.getY()), random
                .nextInt(this.koordinat.getZ())));
        baliqlar.add(baliq);
    }

    public synchronized void deleteBaliq(Baliq baliq) {
        baliqlar.remove(baliq);
    }

    public synchronized Baliq addBaliq() {
        Baliq baliq = new Baliq(random.nextBoolean(), random.nextInt(19) + 1);
        baliq.setKoordinat(new Koordinat(random.nextInt(this.koordinat.getX()),
                random.nextInt(this.koordinat.getY()), random.nextInt(this.koordinat.getZ())));
        baliqlar.add(baliq);
        return baliq;
    }

    private synchronized boolean isCollision() {
        Vector<Baliq> baliqlar1 = new Vector<>(baliqlar);
        Vector<Baliq> list = new Vector<>();

        if (baliqlar1.size() > 1) {
            for (int i = 0; i < baliqlar1.size(); i++) {
                for (int j = i + 1; j < baliqlar1.size(); j++) {
                    Baliq baliq1 = baliqlar1.get(i);
                    Baliq baliq2 = baliqlar1.get(j);
                    if (getBaliqlar().size() >= capacity) {
                        break;
                    }
                    if (!list.contains(baliq1) && baliq1.getKoordinat().equals(baliq2.getKoordinat()) &&
                            ((baliq1.getGender() && !baliq2.getGender()) ||
                                    (!baliq1.getGender() && baliq2.getGender()))) {
                        Baliq baliq = akvarium.addBaliq();
                        baliq.setName("fish " + Akvarium.number++);
                        baliq.start();
                        System.out.println("Child = " + baliq);
                        list.add(baliq2);
                        break;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized Vector<Baliq> getBaliqlar() {
        return baliqlar;
    }
    public synchronized Koordinat getKoordinat() {
        return koordinat;
    }

}
