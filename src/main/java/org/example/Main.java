package org.example;

import java.util.Iterator;

public class Main {
    private static Akvarium akvarium;
    public static void main(String[] args)  {
        akvarium=Akvarium.getAkvarium();
        akvarium.start();
    }







}
