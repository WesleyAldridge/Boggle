/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package core;

import static core.IDie.NUMBER_OF_SIDES;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Wesley Aldridge 
 */
public class Board implements IBoard {

    
    ArrayList<String> storeDiceData = new ArrayList<String>(); //boggle data
    private ArrayList<String> storeDictData = new ArrayList<String>();
    ArrayList<Die> storeGameDice;
    private ArrayList<String> storeGameData = new ArrayList<String>();
    
    
    //constructor
    public Board (ArrayList<String> dice, ArrayList<String> dict) {
        storeDiceData = dice;
        storeDictData = dict;

        storeGameDice = new ArrayList<Die>();
    }
    
       
    @Override
    public void populateDice() {
        
        Die die;
        
        int counter = 0;
        int i = 0;
        int j = 0;
        
        for(i = 0; i < NUMBER_OF_DICE; i++){
            die = new Die();
            //System.out.print("Die " + i + ": ");  Assignment 2 output
            
            for(j = 0; j < NUMBER_OF_SIDES; j++) {
                die.addLetter(storeDiceData.get(counter));
                counter++;
            }
            
            //die.displayLetters();                 Assignment 2 output
            storeGameDice.add(die);
        }
        
        
        }
        

    @Override
    public ArrayList shakeDice() {
        storeGameData.clear();
        int i, j, counter = 0;
        boolean hasBeenUsed;
        ArrayList<Integer> diceOptions = new ArrayList<Integer>();
        diceOptions.addAll(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
        //^This array will store all of the dice options. When we select a die using
        //Random, we will remove that dice from this list of options.
        //That way we can only select dice that haven't been selected yet before.
        
          //Loop through all 16 dice:
            for(i = 0; i < 16; i++) {
              //Generate a number 0-15; this number represents
              //one of the 16 dice:
                Random rand = new Random();
                int n = rand.nextInt(16-i);
                
              //Roll the random die to get a random side, and then store that side
              //in the storeGameData ArrayList.
                storeGameData.add(storeGameDice.get(diceOptions.get(n)).rollDie(n));
                
              //Remove that die from the list of options!!
                diceOptions.remove(n);  
            }
        
        
        return storeGameData;
    }//end shakeDice();

    public ArrayList<String> getStoreGameData() {
        return storeGameData;
    }
    
    public void setStoreGameData(ArrayList alist) {
        storeGameData.clear();
        storeGameData.addAll(alist);
    }
    
    public void displayBoard() {
        System.out.println("Boggle board:");
        
        int i,j, counter = 0;
        
        for(i = 0; i < 4; i++) {
            for(j = 0; j < 4; j++) {
               System.out.print(getStoreGameData().get(counter) + " ");   
               counter++;
            }
            System.out.println(" ");
        }
        
    }

    public ArrayList<String> getStoreDictData() {
        return storeDictData;
    }
    
    
}


