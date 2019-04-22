/*
Makayla Ballenger
CS 202 - Final Project
Class: Game
 */
package finalproject_monopoly;

import java.util.ArrayList;

public class Game {
    
    private ArrayList<Player> players;
    
    public Game(){
        players = new ArrayList<Player>();
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    public String getPlayerName(int location){
        Player player = players.get(location);
        return player.getName();
    }
    public int getPlayerLocation(int location){
        Player player = players.get(location);
        return player.getLocation();
    }
    public void setPlayerLocation(int arrayLocation, int place){
        Player player = players.get(arrayLocation);
        player.setLocation(place);
    }
    public int getPlayerMoney(int location){
        Player player = players.get(location);
        return player.getMoney();
    }
    public void setPlayerMoney(int arrayLocation, int amount){
        Player player = players.get(arrayLocation);
        player.setMoney(amount);
    }
    public String getPlayerProperty(int location, int propertylocation){
        Player player = players.get(location);
        return player.getProperty(propertylocation);
    }
    public void removePlayer(int location){
        players.remove(location);
    }
    public void clearPlayers(){
        players.clear();
    }
    public boolean checkPlayerProperty(int location, String propertyName){
        Player player = players.get(location);
        if (player.ownProperty(propertyName) == true){
            return true;
        }
        else{
            return false;
        }
    }
    public void addPlayerProperty(int location, String propertyName){
        Player player = players.get(location);
        player.addProperty(propertyName);
    }
    public int amountofPlayers(){
        return players.size();
    }
    public int amountofProperties(int location){
        Player player = players.get(location);
        return player.amountofProperties();
    }
    public void setPlayerAuction(int location, int amount){
        Player player = players.get(location);
        player.setAuctionBid(amount);
    }
    public int getPlayerAuction(int location){
        Player player = players.get(location);
        return player.getAuctionBid();
    }
    public void removePlayerProperty(int location, String propertyName){
        Player player = players.get(location);
        player.removeProperty(propertyName);
    }
}
