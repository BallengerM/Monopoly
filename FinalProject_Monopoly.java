/*
Makayla Ballenger
CS 202 - Professor Bowe
Final Project
References: Mr. Bowe, as always!
          : https://stackoverflow.com/questions/22452930/terminating-a-java-program
 */
package finalproject_monopoly;

import java.util.InputMismatchException;
import java.util.Scanner;


public class FinalProject_Monopoly {

    
    public static void main(String[] args) {
        //Initializes all needed objects
        Board theBoard = new Board();
        Dice theDice = new Dice();
        Game theGame = new Game();
        Scanner in = new Scanner(System.in);
        theBoard.fillBoard();
        theBoard.hashTable();
        theBoard.fillChanceQueue();
        theBoard.fillCommunityQueue();
        int freeParking = 0;
        
        try{
        //Starting the game
        System.out.println("Welcome to Monopoly!");
        System.out.println("How many players will be playing? Please enter a number between 2 and 6.");
        int number = in.nextInt();
        if(number < 2 || number > 6){
            System.out.println("Please enter a number between 2 and 6.");
            number = in.nextInt();
            if(number < 2 || number > 6){
                System.out.println("That number does not work!");
                System.exit(0);
            }
        }
        int size = number;
        int number2 = number;
        
        //Clears out \n
        in.nextLine();
        System.out.println("What are your names?");
        while (number > 0){
            String name = in.nextLine();
            theGame.addPlayer(new Player(name,15000000,0));
            number--;
        }
        int[] startingArray = new int[size];
        for(int i=0; i<startingArray.length; i++){
            startingArray[i]=theDice.totalRoll();
        }
        int starting = findLargest(startingArray);
        String startingPlayer = theGame.getPlayerName(starting);
        System.out.println("Congrats " + startingPlayer + " you get to go first!");
        
        //Setting up for game play
        theGame.clearPlayers();
        System.out.println("Please re-enter your names in the order that you will be playing");
        while (number2 > 0){
            String name = in.nextLine();
            theGame.addPlayer(new Player(name,15000000,0));
            number2--;
        }
        
        int player = 0;
        int chanceCounter = 0;
        int communityCounter = 0; 
        
        printBoard();
        
        //Loop
        while(theGame.amountofPlayers() != 0){
            
            int doubleCounter = 0;
            
            int menuChoice = playerMenu(theGame,player);
            
            if(menuChoice == 1){
            //Roll dice
            int die1 = theDice.roll1();
            int die2 = theDice.roll2();
            int totalRoll = die1 + die2;
            System.out.println("You rolled a " + die1 + " and a " + die2);
            //If you roll doubles
            if (checkDouble(die1,die2) == true){
                if (doubleCounter == 3){
                    //Set location to jail
                    System.out.println("You rolled doubles three times, you are now in jail.");
                }
                else{
                    //Movement Stuff
                    int location = theGame.getPlayerLocation(player);
                    if (location == 39){
                        location = totalRoll - 1;
                        //In this case, you pass go
                        int currentmoney = theGame.getPlayerMoney(player);
                        currentmoney = currentmoney + 2000000;
                        theGame.setPlayerMoney(player, currentmoney);
                    }
                    else if (location == 40){
                        System.out.println("CONGRATS! You rolled doubles!");
                        location = 10;
                        //You are not allowed to move after you get out of jail
                        player++;
                        if(player == theGame.amountofPlayers()){
                            player = 0;
                        }
                    }
                    else{
                        location = location + totalRoll;
                    }
                    theGame.setPlayerLocation(player, location);
                    int playerlocation = theGame.getPlayerLocation(player);
                    String placeName = theBoard.getValue(playerlocation);
                    System.out.println("You are now at " + placeName + ".");
                    if (playerlocation == 7 || playerlocation ==  22 || playerlocation == 36){
                        if(chanceCounter == 16){
                            chanceCounter = 0;
                        }
                        chanceCounter++;
                        //Chance Card
                        System.out.println(theBoard.drawChanceCard());
                        chanceDraw(theGame,chanceCounter,player,freeParking);
                    }
                    else if (playerlocation == 2 || playerlocation == 17 || playerlocation == 33){
                        if(communityCounter == 16){
                            communityCounter = 0;
                        }
                        communityCounter++;
                        //Community Chance Card
                        System.out.println(theBoard.drawCommunityCard());
                        communityDraw(theGame,communityCounter,player,freeParking);
                        
                    }
                    else if (playerlocation == 4){
                        //Income Tax
                    }
                    else if (playerlocation == 38){
                        //Interest on Credit Card Debt
                        int amount = 750000;
                        freeParking = freeParking + amount;
                        int playerMoney = theGame.getPlayerMoney(player);
                        playerMoney = playerMoney - amount;
                        theGame.setPlayerMoney(player, playerMoney);
                    }
                    else if (playerlocation == 20){
                        //Free Parking
                        int playerMoney = theGame.getPlayerMoney(player);
                        playerMoney = playerMoney + freeParking; 
                        theGame.setPlayerMoney(player, playerMoney);
                    }
                    else if (playerlocation == 30){
                        //Go to jail
                        theGame.setPlayerLocation(player, 40);
                    }
                    else{
                        //PROPERTIES
                        int playerLocation = theGame.getPlayerLocation(player);
                        int placePrice = theBoard.getValueHash(placeName);
                        //Possibilities
                            //Property is owned by you
                            if (theGame.checkPlayerProperty(player, placeName) == true){
                                System.out.println("You own " + placeName + ".");
                            }
                            //Property is unowned
                            else if (findOwner(theGame,placeName) == -1){
                                System.out.println(placeName + " is unowned!");
                                System.out.println("Would you like to buy " + placeName + " for " + placePrice + "? Please type Yes or No.");
                                System.out.println("You have " + theGame.getPlayerMoney(player) + " dollars.");
                                String response = in.nextLine();
                                if ("Yes".equals(response) || "yes".equals(response)){
                                    //Add to the player's property
                                    theGame.addPlayerProperty(player, placeName);
                                    int currentMoney = theGame.getPlayerMoney(player);
                                    currentMoney = currentMoney - placePrice;
                                    theGame.setPlayerMoney(player, currentMoney);
                                }
                                else if ("No".equals(response) || "no".equals(response)){
                                    //Start the auction process
                                    System.out.println("We will now start the auction process.");
                                    System.out.println("The bidding will start at 10000.");
                                    for(int i=0; i<theGame.amountofPlayers(); i++){
                                        theGame.setPlayerAuction(i, 10000);
                                    }
                                    System.out.println("The bid cannot exceed 15000000.");
                                    System.out.println("You will each have three opportunities to make a bid. If you do not want to bid, keep entering 10000.");
                                    //Create a loop that will cycle through everyone and get their current bid
                                    int x = 0;
                                    while(x <= 3){
                                        for(int i=0; i<theGame.amountofPlayers(); i++){
                                            String name = theGame.getPlayerName(i);
                                            System.out.println(name + ", what would you like to bid?");
                                            int bidAmt = in.nextInt();
                                            if(bidAmt >= 10000 && bidAmt <= 15000000){
                                                theGame.setPlayerAuction(i, bidAmt);
                                            }
                                            else{
                                                System.out.println("Remember...Your bid must be between 10000 and 15000000");
                                                System.out.println("Try again!");
                                                int bidAmt2 = in.nextInt();
                                                theGame.setPlayerAuction(i, bidAmt2);
                                            }
                                        x++;
                                        }
                                    }
                                    int winningPlayer = 0;
                                    for(int y=0; y<theGame.amountofPlayers(); y++){
                                        if(theGame.getPlayerAuction(y) > theGame.getPlayerAuction(winningPlayer)){
                                            winningPlayer = y;
                                        }
                                    }
                                    theGame.addPlayerProperty(winningPlayer, placeName);
                                    int money = theGame.getPlayerMoney(winningPlayer);
                                    money = money - (theBoard.getValueHash(placeName));
                                    theGame.setPlayerMoney(winningPlayer, money);
                                    System.out.println("Congrats " + theGame.getPlayerName(winningPlayer) + "! You have won the auction. The property has been added to your account and the money has been given to the bank.");
                                }
                            }
                            //Property is owned by an opponent
                            else{
                                int owner = findOwner(theGame,placeName);
                                String name = theGame.getPlayerName(owner);
                                System.out.println(placeName + " is owned by " + name + ".");
                                //Pay the proper amount of rent to the owner
                                int rent = rentAmount(theGame,theBoard,playerLocation,owner);
                                int payer = theGame.getPlayerMoney(player);
                                int reciever = theGame.getPlayerMoney(owner);
                                payer = payer - rent;
                                reciever = reciever + rent;
                                theGame.setPlayerMoney(player, payer);
                                theGame.setPlayerMoney(owner, reciever);
                            }
                    }
                }
            }
            //DID NOT ROLL DOUBLES
            else{
                //Movement Stuff
                int location = theGame.getPlayerLocation(player);
                if(location == 39){
                    location = totalRoll - 1;
                    //In this case, you pass go
                    int currentmoney = theGame.getPlayerMoney(player);
                    currentmoney = currentmoney + 2000000;
                    theGame.setPlayerMoney(player, currentmoney);
                }
                else if(location == 40){
                    System.out.println("You are currently in jail. You did not roll doubles to get out of jail.");
                    //GIVE THE OPTION TO PAY TO GET OUT
                }
                else{
                    location = location + totalRoll;
                }
                theGame.setPlayerLocation(player, location);
                int playerlocation = theGame.getPlayerLocation(player);
                String placeName = theBoard.getValue(playerlocation);
                System.out.println("You are now at " + placeName + ".");
                if (playerlocation == 7 || playerlocation ==  22 || playerlocation == 36){
                    if(chanceCounter == 16){
                        chanceCounter = 0;
                    }
                    chanceCounter++;
                    //Chance Card
                    System.out.println(theBoard.drawChanceCard());
                    chanceDraw(theGame,chanceCounter,player,freeParking);
                }
                else if (playerlocation == 2 || playerlocation == 17 || playerlocation == 33){
                    if(communityCounter == 16){
                            communityCounter = 0;
                    }
                    communityCounter++;
                    //Community Chance Card
                    System.out.println(theBoard.drawCommunityCard());
                    communityDraw(theGame,communityCounter,player,freeParking);
                }
                else if (playerlocation == 4){
                    //Income Tax
                }
                else if (playerlocation == 38){
                    //Interest on Credit Card Debt
                    int amount = 750000;
                    freeParking = freeParking + amount;
                    int playerMoney = theGame.getPlayerMoney(player);
                    playerMoney = playerMoney - amount;
                    theGame.setPlayerMoney(player, playerMoney);
                }
                else if (playerlocation == 20){
                    //Free Parking
                    int playerMoney = theGame.getPlayerMoney(player);
                    playerMoney = playerMoney + freeParking; 
                    theGame.setPlayerMoney(player, playerMoney);
                }
                else if (playerlocation == 30){
                    //Go to jail
                    theGame.setPlayerLocation(player, 40);
                }
                else if (playerlocation == 10){
                    System.out.println("Say hi to your enemies in jail!");
                }
                else{
                    //PROPERTIES
                    int playerLocation = theGame.getPlayerLocation(player);
                    int placePrice = theBoard.getValueHash(placeName);
                    //Possibilities
                        //Property is owned by you
                        if (theGame.checkPlayerProperty(player, placeName) == true){
                            System.out.println("You own " + placeName + ".");
                        }
                        //Property is unowned
                        else if (findOwner(theGame,placeName) == -1){
                            System.out.println(placeName + " is unowned!");
                            System.out.println("Would you like to buy " + placeName + " for " + placePrice + "?");
                            System.out.println("You have " + theGame.getPlayerMoney(player) + " dollars.");
                            String response = in.nextLine();
                            if ("Yes".equals(response) || "yes".equals(response)){
                                //Add to the player's property
                                theGame.addPlayerProperty(player, placeName);
                                int currentMoney = theGame.getPlayerMoney(player);
                                currentMoney = currentMoney - placePrice;
                                theGame.setPlayerMoney(player, currentMoney);
                            }      
                            else if ("No".equals(response) || "yes".equals(response)){
                                //Start the auction process
                                System.out.println("We will now start the auction process.");
                                System.out.println("The bidding will start at 10000.");
                                for(int i=0; i<theGame.amountofPlayers(); i++){
                                    theGame.setPlayerAuction(i, 10000);
                                    System.out.println("Hello");
                                }
                                System.out.println("The bid cannot exceed 15000000.");
                                System.out.println("You will each have three opportunities to make a bid. If you do not want to bid, keep entering 10000.");
                                //Create a loop that will cycle through everyone and get their current bid
                                int x = 0;
                                while(x <= 3){
                                    for(int i=0; i<theGame.amountofPlayers(); i++){
                                        String name = theGame.getPlayerName(i);
                                        System.out.println(name + ", what would you like to bid?");
                                        int bidAmt = in.nextInt();
                                        if(bidAmt >= 10000 && bidAmt <= 15000000){
                                            theGame.setPlayerAuction(i, bidAmt);
                                        }
                                        else{
                                            System.out.println("Remember...Your bid must be between 10000 and 15000000");
                                            System.out.println("Try again!");
                                            int bidAmt2 = in.nextInt();
                                            theGame.setPlayerAuction(i, bidAmt2);
                                        }
                                    x++;
                                    }
                                }
                                int winningPlayer = 0;
                                for(int y=0; y<theGame.amountofPlayers(); y++){
                                    if(theGame.getPlayerAuction(y) > theGame.getPlayerAuction(winningPlayer)){
                                        winningPlayer = y;
                                    }
                                }
                                theGame.addPlayerProperty(winningPlayer, placeName);
                                int money = theGame.getPlayerMoney(winningPlayer);
                                money = money - (theBoard.getValueHash(placeName));
                                theGame.setPlayerMoney(winningPlayer, money);
                                System.out.println("Congrats " + theGame.getPlayerName(winningPlayer) + "! You have won the auction. The property has been added to your account and the money has been given to the bank.");
                            }
                        }
                        //Property is owned by an opponent
                        else{
                            int owner = findOwner(theGame,placeName);
                            String name = theGame.getPlayerName(owner);
                            System.out.println(placeName + " is owned by " + name + ".");
                            //Pay the proper amount of rent to the owner
                            int rent = rentAmount(theGame,theBoard,playerLocation,owner);
                            int payer = theGame.getPlayerMoney(player);
                            int reciever = theGame.getPlayerMoney(owner);
                            payer = payer - rent;
                            reciever = reciever + rent;
                            theGame.setPlayerMoney(player, payer);
                            theGame.setPlayerMoney(owner, reciever);
                        }
                } 
            player++;
            if(player == theGame.amountofPlayers()){
                player = 0;
            }    
            }
            }
            else if (menuChoice == 2){
                System.out.println("You have " + theGame.getPlayerMoney(player) + " dollars.");
            }
            else if (menuChoice == 3){
                int playerlocation = theGame.getPlayerLocation(player);
                String placeName = theBoard.getValue(playerlocation);
                System.out.println("You are at " + placeName + ".");
            }
            else if (menuChoice == 4){
                System.out.println(theGame.getPlayerName(player) + "'s Properties.");
                System.out.println("---------------------");
                for(int i=0; i<theGame.amountofProperties(player); i++){
                    System.out.println(theGame.getPlayerProperty(player, i));
                }
                System.out.println("---------------------");
            }
            else if (menuChoice == 5){
                System.out.println("Which property would you like to mortgage?");
                System.out.println("Make sure you enter the property name exactly how it is on the board.");
                String response = in.nextLine();
                theGame.removePlayerProperty(player, response);
                int value = theBoard.getValueHash(response);
                int playerMoney = theGame.getPlayerMoney(player);
                playerMoney = playerMoney - value;
                theGame.setPlayerMoney(player, playerMoney);
            }
            else if (menuChoice == 6){
                System.out.println("Which properties would you like to put houses on?");
            }
            else if (menuChoice == 7){
                System.out.println("Which properties would you like to put hotels on?");
            }
            else if (menuChoice == 8){
                System.out.println("We are sorry to see you go.");
                theGame.removePlayer(player);
            }
            else{
                System.out.println("That was not a choice.");
            }
        }
        }
        catch(IndexOutOfBoundsException | InputMismatchException | NullPointerException ex){
            System.out.println("Something went wrong.");
        }
    }
    
    public static boolean checkDouble(int number1, int number2){
        if (number1 == number2){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static int findLargest(int[] array){
        int largest = 0;
        for(int i=0; i<array.length; i++){
            if(array[i]>array[largest]){
                largest = i;
            }
        }
        return largest;
    }
    
    public static int findOwner(Game theGame, String placeName){
        for(int i=0; i<theGame.amountofPlayers(); i++){
            for(int j=0; j<theGame.amountofProperties(i); j++){
                if (theGame.checkPlayerProperty(j, placeName) == true){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int playerMenu(Game theGame, int player){
        System.out.println(theGame.getPlayerName(player)+ ", make your choice.");
        System.out.println("1. Roll the dice.");
        System.out.println("2. See your money.");
        System.out.println("3. See your location.");
        System.out.println("4. See your properties.");
        System.out.println("5. Mortgage properties.");
        System.out.println("6. Buy houses.");
        System.out.println("7. Buy hotels.");
        System.out.println("8. Drop out of the game.");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        return choice;
    }
    
    public static void chanceDraw(Game theGame, int chanceCounter, int player, int freeparking){
        if(chanceCounter == 1){
            int playerLocation = theGame.getPlayerLocation(player);
            playerLocation = playerLocation - 3;
            theGame.setPlayerLocation(player, playerLocation);
        }
        else if(chanceCounter == 2){
            theGame.setPlayerLocation(player, 11);
        }
        else if(chanceCounter == 3){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney - 150000;
            theGame.setPlayerMoney(player, playerMoney);
            freeparking = freeparking + 150000;
        }
        else if(chanceCounter == 4){
            theGame.setPlayerLocation(player, 24);
        }
        else if(chanceCounter == 5){
            theGame.setPlayerLocation(player, 0);
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 2000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(chanceCounter == 6){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 1500000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(chanceCounter == 7){
            theGame.addPlayerProperty(player, "GET OUT OF JAIL FREE");
        }
        else if(chanceCounter == 8){
            //Subtract $250K per house
            //Subtract $1M per hotel
        }
        else if(chanceCounter == 9){
            //Pay each player 500K
            for(int i=0; i<theGame.amountofPlayers(); i++){
                int playerMoney = theGame.getPlayerMoney(i);
                playerMoney = playerMoney + 500000;
                theGame.setPlayerMoney(i,500000);
            }
            int numofplayers = theGame.amountofPlayers();
            int amount = numofplayers * 500000;
            int currentMoney = theGame.getPlayerMoney(player);
            currentMoney = currentMoney - amount;
            theGame.setPlayerMoney(player, currentMoney);
        }
        else if(chanceCounter == 10){
            //Advance to nearest airport - pay twice the rent
        }
        else if(chanceCounter == 11){
            //Advance to nearest airport - pay twice the rent
        }
        else if(chanceCounter == 12){
            //Go to jail
            theGame.setPlayerLocation(player, 40);
        }
        else if(chanceCounter == 13){
            //O'Hare International Airport - if you pass go collect $2M
        }
        else if(chanceCounter == 14){
            //Nearest service provider - pay $100K times the amount rolled
        }
        else if(chanceCounter == 15){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 500000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(chanceCounter == 16){
            theGame.setPlayerLocation(player, 39);
        }
    }
    public static void communityDraw(Game theGame, int communityCounter, int player, int freeparking){
        if(communityCounter == 1){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 100000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 2){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney - 1000000;
            theGame.setPlayerMoney(player, playerMoney);
            freeparking = freeparking + 1000000;
        }
        else if(communityCounter == 3){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 450000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 4){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 1000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 5){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 1000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 6){
            theGame.setPlayerLocation(player, 40);
        }
        else if(communityCounter == 7){
            theGame.setPlayerLocation(player, 0);
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 2000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 8){
            //Collect $500K from each player
            for(int i=0; i<theGame.amountofPlayers(); i++){
                int playerMoney = theGame.getPlayerMoney(i);
                playerMoney = playerMoney - 500000;
                theGame.setPlayerMoney(i,500000);
            }
            int numofplayers = theGame.amountofPlayers();
            int amount = numofplayers * 500000;
            int currentMoney = theGame.getPlayerMoney(player);
            currentMoney = currentMoney + amount;
            theGame.setPlayerMoney(player, currentMoney);
        }
        else if(communityCounter == 9){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney - 1500000;
            theGame.setPlayerMoney(player, playerMoney);
            freeparking = freeparking + 1500000;
        }
        else if(communityCounter == 10){
            theGame.addPlayerProperty(player, "GET OUT OF JAIL FREE");
        }
        else if(communityCounter == 11){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 200000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 12){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney - 500000;
            theGame.setPlayerMoney(player, playerMoney);
            freeparking = freeparking + 500000;
        }
        else if(communityCounter == 13){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 2000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 14){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 250000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 15){
            int playerMoney = theGame.getPlayerMoney(player);
            playerMoney = playerMoney + 1000000;
            theGame.setPlayerMoney(player, playerMoney);
        }
        else if(communityCounter == 16){
            //Pay $400K for each house
            //Pay $1.15M for each hotel
        }
    }
    public static int rentAmount(Game theGame, Board theBoard, int propertyLocation, int reciever){
        if(propertyLocation == 1){
            if(theGame.checkPlayerProperty(reciever, "Texas Stadium, Dallas") == true){
                return 40000;
            }
            else{
                return 20000;
            }
        }
        else if(propertyLocation == 3){
            if(theGame.checkPlayerProperty(reciever, "Jacobs Field, Cleveland") == true){
                return 80000;
            }
            else{
                return 40000;
            }
        }
        else if(propertyLocation == 6){
            if(theGame.checkPlayerProperty(reciever, "Mall of America, Minneapolis") == true && theGame.checkPlayerProperty(reciever, "Gateway Arch, St. Louis") == true){
                return 120000;
            }
            else{
                return 60000;
            }
        }
        else if(propertyLocation == 8){
            if(theGame.checkPlayerProperty(reciever, "Mall of America, Minneapolis") == true && theGame.checkPlayerProperty(reciever, "Grand Ole Opry, Nashville") == true){
                return 120000;
            }
            else{
                return 60000;
            }
        }
        else if(propertyLocation == 9){
            if(theGame.checkPlayerProperty(reciever, "Gateway Arch, St. Louis") == true && theGame.checkPlayerProperty(reciever, "Grand Ole Opry, Nashville") == true){
                return 160000;
            }
            else{
                return 80000;
            }
        }
        else if(propertyLocation == 11){
            if(theGame.checkPlayerProperty(reciever, "Red Rocks Amphitheatre, Denver") == true && theGame.checkPlayerProperty(reciever, "Liberty Bell, Philadelphia") == true){
                return 200000;
            }
            else{
                return 100000;
            }
        }
        else if(propertyLocation == 13){
            if(theGame.checkPlayerProperty(reciever, "Centennial Olympic Park, Atlanta") == true && theGame.checkPlayerProperty(reciever, "Liberty Bell, Philadelphia") == true){
                return 200000;
            }
            else{
                return 100000;
            }
        }
        else if(propertyLocation == 14){
            if(theGame.checkPlayerProperty(reciever, "Red Rocks Amphitheatre, Denver") == true && theGame.checkPlayerProperty(reciever, "Centennial Olympic Park, Atlanta") == true){
                return 240000;
            }
            else{
                return 120000;
            }
        }
        else if(propertyLocation == 16){
            if(theGame.checkPlayerProperty(reciever, "Johnson Space Center, Houston") == true && theGame.checkPlayerProperty(reciever, "Pioneer Square, Seattle") == true){
                return 280000;
            }
            else{
                return 140000;
            }
        }
        return 0;
    }
    public static void printBoard(){
        System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("|        Free           |     Camelback Mtn.    |         Chance        |      Waikiki Beach    |     Disney World      |    John F. Kennedy    |     French Quarter    |       Hollywood       |        Internet       |  Golden Gate Bridge   |         Go To         |");
        System.out.println("|       Parking         |        Phoenix        |                       |        Honolulu       |       Orlando         |       Airport         |      New Orleans      |      Los Angeles      |        Service        |     San Francisco     |         Jail          |");
        System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("|    Pioneer Square     |                                                                                                                                                                                                                       |  Las Vegas Boulevard  |");                    
        System.out.println("|        Seattle        |                                                                                                                                                                                                                       |        Nevada         |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("| Johnson Space Center  |                                                                                                                                                                                                                       |    Wrigley Field      |");
        System.out.println("|        Houston        |                                                                                                                                                                                                                       |       Chicago         |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|       Community       |                                                                                                                                                                                                                       |      Community        |");
        System.out.println("|         Chest         |                                                                                                                                                                                                                       |        Chest          |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|      South Beach      |                                                                                                                                                                                                                       |      White House      |");
        System.out.println("|        Miami          |                                                                                                                                                                                                                       |     Washington, DC    |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|      Los Angeles      |                                                                                                                                                                                                                       |   Hartsfield Jackson  |");
        System.out.println("|       Airport         |                                                                                                                                                                                                                       |    Atlanta Airport    |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|     Liberty Bell      |                                                                                                                                                                                                                       |        Chance         |");
        System.out.println("|     Philadelphia      |                                                                                                                                                                                                                       |                       |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|       Red Rock        |                                                                                                                                                                                                                       |      Fenway Park      |");
        System.out.println("| Amphitheatre Airport  |                                                                                                                                                                                                                       |        Boston         |");
        System.out.println("_________________________                                                                                                                                                                                                                       _________________________");
        System.out.println("|      Cell Phone       |                                                                                                                                                                                                                       |      Times Square     |");
        System.out.println("|       Service         |                                                                                                                                                                                                                       |        New York       |");
        System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("|        Just           |    Mall of America    |      Gateway Arch     |         Chance        |    Grand Ole Opry     |         O'Hare        |         Income        |     Texas Stadium     |       Community       |     Jacobs Field      |           GO          |");
        System.out.println("|       Visting         |      Minneapolis      |       St. Louis       |                       |       Nashville       |         Airport       |          Tax          |        Dallas         |         Chest         |       Cleveland       |                       |");
        System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
    }
}
