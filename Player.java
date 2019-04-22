/*
Makayla Ballenger
CS 202 - Final Project
Class: Player
 */
package finalproject_monopoly;

import java.util.ArrayList;


public class Player {
    
    private String name;
    private int money;
    private int location;
    private ArrayList<String> properties;
    private int auctionBid; 
    
    public Player(String playerName, int playerMoney, int playerLocation){
        name = playerName;
        money = playerMoney;
        location = playerLocation;
        properties = new ArrayList<String>();
        auctionBid = 0;
    }
    
    public void setName(String playerName){
        name = playerName;
    }
    public void setMoney(int moneyAmt){
        money = moneyAmt;
    }
    public void setLocation(int place){
        location = place;
    }
    public void addProperty(String propertyName){
        properties.add(propertyName);
    }
    
    public String getName(){
        return name;
    }
    public int getMoney(){
        return money;
    }
    public int getLocation(){
        return location;
    }
    public String getProperty(int location){
        return properties.get(location);
    }
    public boolean ownProperty(String propertyName){
        for(int i=0; i<properties.size(); i++){
            if (properties.get(i) == propertyName){
                return true;
            }
        }
        return false;
    }
    public int amountofProperties(){
        return properties.size();
    }
    public void setAuctionBid(int amount){
        auctionBid = amount;
    }
    public int getAuctionBid(){
        return auctionBid;
    }
    public void removeProperty(String propertyName){
        int propertyLocation = 0;
        for(int i=0; i<properties.size(); i++){
            if(properties.get(i) == propertyName){
                propertyLocation = i;
            }
        }
        properties.remove(propertyLocation);
    }
}
