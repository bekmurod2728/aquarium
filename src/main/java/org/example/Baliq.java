package org.example;

import java.util.Objects;

public class Baliq extends Thread{
    private  boolean gender;
    private  int deadline;
    private volatile Koordinat koordinat;
    private volatile long begun;
    private static Akvarium akvarium=Akvarium.getAkvarium();;


    public Baliq(boolean gender, int deadline) {
        this.gender = gender;
        this.deadline = deadline;
        this.begun=System.currentTimeMillis();
    }

    @Override
    public void run() {
        long deadline2=System.currentTimeMillis()+deadline*1000;
        while (deadline2>=begun){

            begun+=1000;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            move();

        }
            System.out.println("Dead fish " + this);
            akvarium.deleteBaliq(this);
    }
    private synchronized void move() {
            int direction = Akvarium.random.nextInt(6);
            int son2;
            switch (direction) {
                case 0 : {
                    son2= this.koordinat.getX();
                    if (this.koordinat.getX() < akvarium.getKoordinat().getX()){
                        this.koordinat.setX(++son2);}
                    else {this.koordinat.setX(--son2);}
                }break;
                case 1 : {
                    son2= this.koordinat.getX();
                    if (this.koordinat.getX() >0) {this.koordinat.setX(--son2);}
                    else {this.koordinat.setX(++son2);}
                }break;
                case 2 : {
                    son2= this.koordinat.getY();
                    if (this.koordinat.getY() < akvarium.getKoordinat().getY())
                    {this.koordinat.setY(++son2);}else {this.koordinat.setY(--son2);}
                }break;
                case 3 : {
                    son2= this.koordinat.getY();
                    if (this.koordinat.getY() >0) {this.koordinat.setY(--son2);} else {this.koordinat.setY(++son2);}
                }break;
                case 4 : {
                    son2= this.koordinat.getZ();
                    if (this.koordinat.getZ() < akvarium.getKoordinat().getZ())
                    {this.koordinat.setZ(++son2);}else {this.koordinat.setZ(--son2);}
                }break;
                case 5 : {
                    son2= this.koordinat.getZ();
                    if (this.koordinat.getZ() >0) {this.koordinat.setZ(--son2);}else {this.koordinat.setZ(++son2);}
                }break;
            }

    }


    public Koordinat getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(Koordinat koordinat) {
        this.koordinat = koordinat;
    }

    public boolean getGender() {
        return gender;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baliq baliq = (Baliq) o;
        return gender == baliq.gender && deadline == baliq.deadline && begun == baliq.begun && Objects.equals(koordinat, baliq.koordinat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, deadline, koordinat, begun);
    }

    @Override
    public String toString() {
        return "Baliq{" +
                "name="+getName()+
                ", gender=" + gender +
                ", deadline=" + deadline +
                '}'+"\n";
    }


}
