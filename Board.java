/*
Makayla Ballenger
CS 202 - Final Project
Class: Board
 */
package finalproject_monopoly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Board {
    //Attributes
    private String[] array;
    private Queue<String> chanceQueue;
    private Queue<String> communityQueue;
    private Map<String, Integer> board;
    private Map<String, Integer> rent;
    //Default Constructor
    public Board(){
        array = new String[41];
        chanceQueue = new LinkedList<String>();
        communityQueue = new LinkedList<String>();
        board = new HashMap<String, Integer>();
        rent = new HashMap<String, Integer>();
    }
    //Fill Board Method
    public void fillBoard(){
        array[0]="GO";
        array[1]="Jacobs Field, Cleveland";
        array[2]="Community Chest";
        array[3]="Texas Stadium, Dallas";
        array[4]="Income Tax";
        array[5]="O'Hare International Airport";
        array[6]="Grand Ole Opry, Nashville";
        array[7]="Chance";
        array[8]="Gateway Arch, St. Louis";
        array[9]="Mall of America, Minneapolis";
        array[10]="Just Visiting";
        array[11]="Centennial Olympic Park, Atlanta";
        array[12]="Cell Phone Service";
        array[13]="Red Rocks Amphitheatre, Denver";
        array[14]="Liberty Bell, Philadelphia";
        array[15]="Los Angeles International Airport";
        array[16]="South Beach, Miami";
        array[17]="Community Chest";
        array[18]="Johnson Space Center, Houston";
        array[19]="Pioneer Square, Seattle";
        array[20]="Free Parking";
        array[21]="Camelback Mountain, Phoenix";
        array[22]="Chance";
        array[23]="Waikiki Beach, Honolulu";
        array[24]="Disney World, Orlando";
        array[25]="John F Kennedy International Airport";
        array[26]="French Quarter, New Orleans";
        array[27]="Hollywood, Los Angeles";
        array[28]="Internet Service";
        array[29]="Golden Gate Bridge, San Francisco";
        array[30]="Go to Jail";
        array[31]="Las Vegas Boulevard, Las Vegas";
        array[32]="Wrigley Field, Chicago";
        array[33]="Community Chest";
        array[34]="White House, Washington D.C.";
        array[35]="Hartsfield Jackson Atlanta International Airport";
        array[36]="Chance";
        array[37]="Fenway Park, Boston";
        array[38]="Interest on Credit Card Debt";
        array[39]="Times Square, New York";
        array[40]="IN JAIL";
    }
    
    //Passed Test
    //Get a value from the board array.
    public String getValue(int location){
        return array[location];
    }
    
    public int location(String place){
        for(int i=0; i<array.length; i++){
            if(array[i] == place){
                return i;
            }
        }
        return -1;
    }
    
    public void hashTable(){
        board.put("GO",2000000);
        board.put("Jacobs Field, Cleveland", 600000);
        board.put("Texas Stadium, Dallas", 600000);
        board.put("O'Hare International Airport", 2000000);
        board.put("Grand Ole Opry, Nashville", 1000000);
        board.put("Gateway Arch, St. Louis", 1000000);
        board.put("Mall of America, Minneapolis", 1200000);
        board.put("Centennial Olympic Park, Atlanta", 1400000);
        board.put("Cell Phone Service", 1500000);
        board.put("Red Rocks Amphitheatre, Denver", 1400000);
        board.put("Liberty Bell, Philadelphia", 1600000);
        board.put("Los Angeles International Airport", 2000000);
        board.put("South Beach, Miami", 1800000);
        board.put("Johnson Space Center", 1800000);
        board.put("Pioneer Square, Seattle", 2000000);
        board.put("Camelback Mountain, Phoenix", 2200000);
        board.put("Waikiki Beach, Honolulu", 2200000);
        board.put("Disney World, Orlando", 2400000);
        board.put("John F Kennedy International Airport", 2000000);
        board.put("French Quarter, New Orleans", 2600000);
        board.put("Hollywood, Los Angeles", 2600000);
        board.put("Internet Service", 1500000);
        board.put("Golden Gate Bridge, San Francisco", 2800000);
        board.put("Las Vegas Boulevard, Las Vegas", 3000000);
        board.put("Wrigley Field, Chicago", 3000000);
        board.put("White House, Washington D.C.", 3200000);
        board.put("Hartsfield Jackson Atlanta International Airport", 2000000);
        board.put("Fenway Park, Boston", 3500000);
        board.put("Times Square, New York", 4000000);
    }
    
    public int getValueHash(String string){
        return board.get(string);
    }
   
    public void fillChanceQueue(){
        chanceQueue.add("Summoned for Jury Duty. Go back 3 spaces.");
        chanceQueue.add("Beat the heat by splashing in the world's largest interactive fountain. Advance to Centennial Olympic Park.");
        chanceQueue.add("Make a donation for disaster relief. Pay $150K.");
        chanceQueue.add("Take your family on a surprise vacation. Advance to Disney World.");
        chanceQueue.add("Advance to GO. Collect $2M.");
        chanceQueue.add("Accept the position of CEO of a high-powered investment-banking firm. Collect a signing bonus of $1.5M.");
        chanceQueue.add("You are acquitted. GET OUT OF JAIL FREE.");
        chanceQueue.add("Your city does a tax revaluation. Pay $250K for each house. Pay $1M for each hotel.");
        chanceQueue.add("A group of guests wins a class action suit against your hotel. Pay each player $500K.");
        chanceQueue.add("Advance to the nearest airport and pay the owner twice the rent to which he/she is entitled. If the airport is unowned, you may buy it from the bank.");
        chanceQueue.add("Take the red eye. Advance token to the nearest airport and pay the owner twice the rent to which he/she is entitled. If the airport is unowned, you may buy it from the bank.");
        chanceQueue.add("Convicted for Identity Theft. Go directly to Jail. Do not pass GO, do not collect $2M.");
        chanceQueue.add("Bad weather forces your flight to get rerouted through Chicago. Advance to O'Hare International Airport. If you pass GO, collect $2M.");
        chanceQueue.add("Advance token to the nearest service provider. If unowned, you may buy it from the bank. If owned, roll the dice and pay the owner 100K times the amount rolled.");
        chanceQueue.add("Get a tax break for driving a hybrid. Collect $500K.");
        chanceQueue.add("Celebrate New Year's Eve in The Big Apple. Advance to Times Square.");
    }
    public String drawChanceCard(){
        String card = chanceQueue.remove();
        chanceQueue.add(card);
        return card;
    }
    
    public void fillCommunityQueue(){
        communityQueue.add("You are runner up on a Reality TV Show. Collect $100K.");
        communityQueue.add("Your computer network gets hit with a virus. Pay $1M.");
        communityQueue.add("Your trust fund becomes available. Collect $450K.");
        communityQueue.add("You win the lottery. Collect $1M.");
        communityQueue.add("You appear on a TV morning news show to promote your new book. Receive $1M in additional royalty from increased sales.");
        communityQueue.add("Arrested for Insider Trading. Go directly to Jail. Do not pass GO, do not collect $2M.");
        communityQueue.add("Advance to GO. Collect $2M.");
        communityQueue.add("You file as a candidate for Mayor. Collect $500K from each player as constituent support.");
        communityQueue.add("You owe back taxes. Pay $1.5M in fines.");
        communityQueue.add("Receive a Presidental Paradon. GET OUT OF JAIL FREE.");
        communityQueue.add("Sell your lifetime, 50-yard line, season tickets on e-Bay. Collect $200K");
        communityQueue.add("Made the winning bid for dance lessons with celebrity coach. Pay $500K.");
        communityQueue.add("Get discovered while visiting Hollywood. Sign into a multi-movie contract. Collect $2M");
        communityQueue.add("You coordinate Opening Day activities at Jacobs Field. Collect $250K for your services.");
        communityQueue.add("Win big at the casinos in Atlantic City. Collect $1M.");
        communityQueue.add("Redo the landscaping at all of your properties. Pay $400K for each house. Pay $1.15M for each hotel.");
    }
    public String drawCommunityCard(){
        String card = communityQueue.remove();
        communityQueue.add(card);
        return card;
    }
    
    public void rentHash(){
        
    }
}

