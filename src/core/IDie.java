/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package core;

/**
 *
 * @author Wesley Aldridge 
 */
public interface IDie {
    
    public static final int NUMBER_OF_SIDES = 6;
    
    
    String rollDie(int n);
    void addLetter(String letter);
    void displayLetters();
    
    
    
    
}
