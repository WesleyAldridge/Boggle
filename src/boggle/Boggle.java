/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package boggle;

import core.Board;
import inputOutput.ReadDataFile;
import java.util.ArrayList;
//import javax.swing.JOptionPane;
import userInterface.BoggleUi;

/**
 *
 * @author Wesley Aldridge 
 */
public class Boggle {

    /**
     * @param args the command line arguments
     */
    
    static ArrayList<String> diceData = new ArrayList<String>();      //holds dice data
    static String diceFile = "BoggleData.txt";                        //dice data source
    
    static ArrayList<String> dictData = new ArrayList<String>();      //holds dictionary data
    static String dictFile = "Dictionary.txt";                        //dict data source
    
    
    
    
    
    public static void main(String[] args) {        
        //outputs from Assignment 1:
        //System.out.println("Welcome to Boggle!");
        //JOptionPane.showMessageDialog(null, "Let's Play Boggle!");
        
        //variables to read data from files about letters (BoggleData)
        //and Dictionary (valid words)
        ReadDataFile readDiceData = new ReadDataFile(diceFile);
        ReadDataFile readDictData = new ReadDataFile(dictFile);
        
        //populate these variables with the file data
        readDiceData.populateData();
        readDictData.populateData();
        
        Board board = new Board(readDiceData.getData(), readDictData.getData());
        
        //must populate dice BEFORE shaking dice!
        board.populateDice();
        diceData = board.shakeDice();
        
        //This must come after shakeDice!!
        BoggleUi boggleui = new BoggleUi(board);
        
        
        //Output from Assignment 1
        //board.displayBoard();
        
        
        //Output number of objects in dictionary data
        //System.out.println("There are " + readDictData.getData().size() + " entries in the dictionary.");
    }
    
}
