package main;

import engine.ASCII;

public class Algotraz {
    public static void main(String[] args) {
        String fileName = "src/data/ascii/welcome.txt";
        
        
        
        ASCII ascii = new ASCII(fileName);
        System.out.println(ascii);

    }
}
