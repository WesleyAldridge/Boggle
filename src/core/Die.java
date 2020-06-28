/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package core;

//import static core.IBoard.NUMBER_OF_DICE;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Wesley Aldridge 
 */
public class Die implements IDie {
    
    private ArrayList<String> diceSideData = new ArrayList<String>(); //stores dice data for the sides of the die
    

    @Override
    public String rollDie(int n) {
        
        Random rand = new Random();
        int side = rand.nextInt(NUMBER_OF_SIDES); //0-5
        return diceSideData.get(side);
    }

    @Override
    public void addLetter(String letter) {
        
        diceSideData.add(letter);
        
    }

    @Override
    public void displayLetters() {      //displayAllLetters

        for(String x: diceSideData) {
            System.out.print(x);
        }
        System.out.println();
        
    }
   
}
