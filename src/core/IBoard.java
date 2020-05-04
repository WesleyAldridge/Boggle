/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author Wesley Aldridge 
 */
public interface IBoard {
    
    public static final int NUMBER_OF_DICE = 16;
    public static final int GRID = 4;
    
    void populateDice();
    ArrayList shakeDice();
    
    
}
