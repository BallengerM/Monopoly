/*
Makayla Ballenger
CS 202 - Final Project
Class: Dice
 */
package finalproject_monopoly;

import java.util.Random;

public class Dice {
    
    private Random r1;
    private Random r2;
    
    public Dice(){
        r1 = new Random();
        r2 = new Random();
    }
    
    public int roll1(){
        return r1.nextInt(6)+1;
    }
    public int roll2(){
        return r2.nextInt(6)+1;
    }
    public int totalRoll(){
        return roll1() + roll2();
    }
}
