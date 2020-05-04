/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package inputOutput;

import com.sun.istack.internal.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author Wesley Aldridge 
 */
public class ReadDataFile implements IReadDataFile {
    private Scanner scanner;
    private String fileName;
    private ArrayList<String> dataFromTheFile;
    
    
    public ReadDataFile (String name) {     //CONSTRUCTOR
        fileName = name;
        dataFromTheFile = new ArrayList<String>();
    }
    
    
     /**
     * @return the dataFromTheFile
     */
    public ArrayList<String> getData() {
        return dataFromTheFile;
    }
    
    
    @Override
    public void populateData() {
        
        try {
        URL url = getClass().getResource("/data/" + fileName);
        File file = new File(url.toURI());
        scanner = new Scanner(new File(url.toURI()));
        
            while(scanner.hasNext() == true){
                dataFromTheFile.add(scanner.next());
            }
            scanner.close();
        } catch (URISyntaxException|FileNotFoundException|InputMismatchException ex) {
            //Logger.getLogger(ReadDataFile.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR: " + ex.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
        
}


