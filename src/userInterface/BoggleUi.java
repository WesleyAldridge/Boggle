/* 
 *  Boggle Program by Wesley Aldridge
 *  https://github.com/WesleyAldridge
 */
package userInterface;

import core.Board;
import java.awt.*;
//import static java.awt.GridBagConstraints.LINE_END;
//import static java.awt.GridBagConstraints.FIRST_LINE_END;
//import static java.awt.GridBagConstraints.LINE_START;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Wesley Aldridge    
 */
public class BoggleUi {
    JFrame jframe;
    JMenuBar jmenubar;
    JMenu jmenu;
    JMenuItem jmenuitem1;
    JMenuItem jmenuitem2;
    JButton[][] jbuttonArray = new JButton[4][4];
    JPanel centerPanel;
    JPanel westPanel;
    JPanel eastPanel;
    JPanel southPanel;
    JPanel boggleGrid;
    JScrollPane jscrollpane;
    JLabel currentWordLabel;
    JButton boggleButton;       //the 16 Boggle buttons
    JButton shakeButton;
    JTextPane textPane;
    JPanel innerCurrentWord;
    JButton submitWord;
    JPanel score;
    JLabel scoreLabel;
    JPanel clock;
    JLabel timeLeft;
    JScrollPane scrollpane;
    Board board;
    
    
    Timer timer;
    ResetListener resetListener = new ResetListener();  // listener for resetting the game board
    ExitListener exitListener = new ExitListener();
    ClockListener clockListener = new ClockListener();
    int timeRemaining = 180; // 180 seconds = 3 minutes
    int minutes, seconds;
    
    
        Random rand = new Random(); // for producing random numbers
        int computerScore; // num of words computer found
        int playerScore = 0; // player's numeric tally of points, initialize to 0
    
        //listeners
        BoggleButtonListener bogglebuttonlistener = new BoggleButtonListener();
        SubmitWordListener submitWordListener = new SubmitWordListener();
        ClearWordListener clearWordListener = new ClearWordListener();
    
        //colors
        Color lightBlue = new Color(225,234,244,255);
        Color disabled = new Color(244,244,244,255);
        Color highlighted = new Color(215,234,216,255);
        Color tenSecondsLeft = new Color(255,0,0,255);
        Color defaultTimeColor;
        //style
        DefaultStyledDocument doc;
        StyleContext context;
        Style strike;
    
        //arrays and ArrayLists
        boolean[][] letterUsed = new boolean[4][4]; //holds which letters have already been used
        ArrayList<String> foundWords = new ArrayList<>(); //list of words player found
        ArrayList<Boolean> computerFound = new ArrayList<>(); //true|false of if computer found or not
        ArrayList<Integer> stillNotFound = new ArrayList<>(); //words the computer hasn't found yet
        
        //Button
        JButton clearWord;
        

///////// </END VARIABLES> /////////
/*
  
     
        
        
        
        
*/        
///////// <BEGIN CLASSES AND METHODS> /////////       
        
     
    
    //Custom Constructor
    public BoggleUi(Board board) {
        this.board = board;
        initComponents();
    }    
        
        
        
    //ResetListener
    public class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == jmenuitem1 || e.getSource() == shakeButton) {
                board.shakeDice();
                int counter = -1;
                for(int i = 0; i < 4; i++) {
                    for(int j = 0; j < 4; j++) {
                        counter++;
                        jbuttonArray[i][j].setText(board.getStoreGameData().get(counter));
                    }
                }
                
                reEnableAll();//reEnables all dice and resets list of used letters
                              
                //also reset list of words computer found
                foundWords.clear(); //list of words player found
                computerFound.clear(); //true|false of if computer found or not
                stillNotFound.clear();
                
                textPane.setText("");
                currentWordLabel.setText("");
                scoreLabel.setText("0");
                playerScore = 0;//reset score to 0
                timeLeft.setText("3:00");
                jframe.revalidate();
                jframe.repaint();
                timer.stop();
                timeRemaining = 180; //(3 minutes, 0 seconds)
                timer.start();
            } 
        }
    }
    
    
    
    //Clear Word Listener
    public class ClearWordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == clearWord) {
                //clear the current word
                //clear used letters
                //re-enable disabled dice
                
                currentWordLabel.setText("");
                reEnableAll();//re-enables all dice and clears out used letters
                clearWord.setEnabled(false);
                submitWord.setEnabled(false);
            } 
        }
    }
    
    
    
    //SubmitWordListener
    public class SubmitWordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int i, j;
            boolean matchesDict;//if it matches a word in the dictionary
            boolean matchesPrev;//if it matches a previously used word
            
            if(e.getSource() == submitWord) {
                i = 0;
                j = 0;
                matchesDict = false;
                matchesPrev = false;
                
                //words must be at least 3 letters long:
                if(currentWordLabel.getText().length() < 3){
                    JOptionPane.showMessageDialog(null, "Words must be at least 3 letters long!");
                    currentWordLabel.setText("");
                    reEnableAll();
                }
                else {//word is at least 3 letters
                //check if it has been used before:
                while(matchesPrev == false && i < foundWords.size()) {  
                    if(currentWordLabel.getText().equals(foundWords.get(i))) {
                        //if submitted word matches a previously used word, error!
                        matchesPrev = true;
                        JOptionPane.showMessageDialog(null, "Word has already been found!");
                        currentWordLabel.setText("");
                        reEnableAll();
                    }
                    i++;
                }
                
                //if word hasn't been used before, check if matches dictionary
                if(matchesPrev == false){                        
                    while(matchesDict == false && (j < board.getStoreDictData().size())) {
                            if(board.getStoreDictData().get(j).toLowerCase().equals(currentWordLabel.getText().toLowerCase())){
                                matchesDict = true;
                            }
                        j++;
                    }
                    
                                        
                    //if doesn't match dictionary
                    if(matchesDict == false) {
                        JOptionPane.showMessageDialog(null, "Invalid Word");
                        currentWordLabel.setText("");
                        reEnableAll();
                    }
                    //if does match dictionary
                    else /*(matchesDict == true)*/ {
                        textPane.setText(textPane.getText() + currentWordLabel.getText() + "\n");
                        foundWords.add(currentWordLabel.getText());
                        calculateDuring(currentWordLabel.getText());//calculates score while timer is still running
                        currentWordLabel.setText("");
                        reEnableAll();
                    }
                }
                }
                
                clearWord.setEnabled(false);
                submitWord.setEnabled(false);
            } 
        }
    }
    
    //Boggle Button Listener
     public class BoggleButtonListener implements ActionListener {
         //this is for the 16 game dice
         int i, j;
         
        @Override
        public void actionPerformed(ActionEvent e) {
            
            for(i = 0; i < 4; i++) {
                for(j = 0; j < 4; j++) {
                    
                    if(e.getSource() == jbuttonArray[i][j]) {//find which die was triggered
                        
                        //set current word label
                        currentWordLabel.setText(currentWordLabel.getText() + jbuttonArray[i][j].getText());
                        
                        //disable this letter
                        letterUsed[i][j] = true;
                        jbuttonArray[i][j].setBackground(highlighted);
                        jbuttonArray[i][j].setEnabled(false);
                        
                        //reEnable unused
                        //disable anything 2 from it
                        reEnableUnused();
                        disableSurrounding(i, j);
                    }  
                }
            }
            
            //enable the clear button
            clearWord.setEnabled(true);
            submitWord.setEnabled(true);
            
            
            
            //highlight used letters
            highlightUsed();
            
        }
    }
     
    //highlightUsed
    void highlightUsed() { 
        int i,j;
        for(i = 0; i < 4; i ++) {
            for(j = 0; j < 4; j++) {
                if(letterUsed[i][j] == true) {
                    jbuttonArray[i][j].setBackground(highlighted);
                }                    
            }
        }
    }
    
    //reEnableAll
    void reEnableAll() {
        //for creating a new word
        int i, j;
        for(i = 0; i < 4; i ++) {
            for(j = 0; j < 4; j++) {
                    jbuttonArray[i][j].setEnabled(true);
                    jbuttonArray[i][j].setBackground(lightBlue);
                    letterUsed[i][j] = false;
            }
        }
    }
     
    void reEnableUnused() {
        //for selecting a sebsequent letter
        int i, j;
        for(i = 0; i < 4; i ++) {
            for(j = 0; j < 4; j++) {
                if(letterUsed[i][j] == false) {
                    jbuttonArray[i][j].setEnabled(true);
                    jbuttonArray[i][j].setBackground(lightBlue);
                }
            }
        }
    }
     
    //disableSurrounding
    void disableSurrounding(int i, int j) {
        
        int x, y;
        
        //disable i
        if(i-2 >= 0) {
            if(i-3 >= 0){
                for(y = 0; y<4; y++) {
                    jbuttonArray[i-2][y].setEnabled(false);
                    jbuttonArray[i-3][y].setEnabled(false);
                    
                    jbuttonArray[i-2][y].setBackground(disabled);
                    jbuttonArray[i-3][y].setBackground(disabled);
                    //jbuttonArray[i-2][y].setFont(new Font("Sans Serif", Font.PLAIN, 24));
                }
            }
            else {
                for(y = 0; y<4; y++) {
                    jbuttonArray[i-2][y].setEnabled(false);
                    jbuttonArray[i-2][y].setBackground(disabled);
                }
            }
        }
        
        
        if(i+2 <= 3) {
            if(i+3 <= 3) {
                for(y = 0; y<4; y++) {
                    jbuttonArray[i+2][y].setEnabled(false);
                    jbuttonArray[i+3][y].setEnabled(false);
                    jbuttonArray[i+3][y].setBackground(disabled);
                    jbuttonArray[i+2][y].setBackground(disabled);
                }
            }
            else {
                for(y = 0; y<4; y++) {
                    jbuttonArray[i+2][y].setEnabled(false);
                    jbuttonArray[i+2][y].setBackground(disabled);
                }
            }
        }
        
        
        
        //disable j
        if(j-2 >= 0) {
            if(j-3 >= 0){
                for(x = 0; x<4; x++) {
                    jbuttonArray[x][j-2].setEnabled(false);
                    jbuttonArray[x][j-3].setEnabled(false);
                    jbuttonArray[x][j-2].setBackground(disabled);
                    jbuttonArray[x][j-3].setBackground(disabled);
                }
            }
            else {
                for(x = 0; x<4; x++) {
                    jbuttonArray[x][j-2].setEnabled(false);
                    jbuttonArray[x][j-2].setBackground(disabled);
                }
            }
        }
        
        
        if(j+2 <= 3) {
            if(j+3 <= 3) {
                for(x = 0; x<4; x++) {
                    jbuttonArray[x][j+2].setEnabled(false);
                    jbuttonArray[x][j+3].setEnabled(false);
                    jbuttonArray[x][j+2].setBackground(disabled);
                    jbuttonArray[x][j+3].setBackground(disabled);
                }
            }
            else {
                for(x = 0; x<4; x++) {
                    jbuttonArray[x][j+2].setEnabled(false);
                    jbuttonArray[x][j+2].setBackground(disabled);
                }
            }
        }
    }
     
    
    //clock listener / Timer
    public class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            //timeRemaining starts at 180 (3 minutes)
            minutes = timeRemaining/60;
            seconds = timeRemaining%60;
            
            if (timeRemaining-- > 0) {
                //time left.
                if(seconds < 10){
                    if(minutes == 0) {
                        //10 seconds left on the clock
                        timeLeft.setForeground(tenSecondsLeft);
                    }
                    else {
                        timeLeft.setForeground(defaultTimeColor);
                    }
                    timeLeft.setText(String.valueOf(minutes) + ":0" +  String.valueOf(seconds));
                }
                else if (!(seconds < 10)){
                    timeLeft.setForeground(defaultTimeColor);
                    timeLeft.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                }
            }
            else {
                //out of time.
                timeLeft.setForeground(defaultTimeColor);
                timeLeft.setText("0:00");
                timer.stop();
                
                disableAll();//disables all the dice
                
                //CALCULATE COMPUTER'S SCORE
                if(foundWords.size() <= 0) {//found 0 words
                    JOptionPane.showMessageDialog(null, "Time's up!\nYou found 0 words.\nYour score is 0.");
                }
                else {//found at least 1 word
                    //calculate computer's score
                    if(foundWords.size() < 7) {
                        computerScore = rand.nextInt(foundWords.size());
                    }
                    else {//found 3 or more words
                        computerScore = rand.nextInt(foundWords.size()/2)+1;
                    }
                    //^This algorithm punishes you for finding a small amount of words
                    //but rewards you for finding a large amount of words.
                    
                    JOptionPane.showMessageDialog(null, "Time's up!\nThe Computer is comparing words.");

                    strikeOut(computerScore);

                    calculateScore();//calculates and displays player's FINAL score

                    JOptionPane.showMessageDialog(null, "The computer found " + computerScore + 
                                                        " of player's " + foundWords.size() + ".");
                
                }
                
            }
        }
    }
    
    
    //Calculate During Play
    void calculateDuring(String s){
        int length = s.length();
        
            if(length < 5) {
                playerScore += 1;
            }
            else if(length == 5){
                playerScore += 2;
            }
            else if(length == 6){
                playerScore += 3;
            }
            else if(length ==7){
                playerScore += 4;
            }
            else if(length > 7) {
                playerScore += 11;
            }
        scoreLabel.setText("" + playerScore);
    }
    
    
    //CALCULATE SCORE
    void calculateScore() {
        //calculate's players FINAL score
        int i, j, length;
        playerScore = 0; //reset score to 0 to re-calculate it.
        
        for(i = 0; i < stillNotFound.size(); i++) {
            length = foundWords.get(stillNotFound.get(i)).length();
            if(length < 5) {
                playerScore += 1;
            }
            else if(length == 5){
                playerScore += 2;
            }
            else if(length == 6){
                playerScore += 3;
            }
            else if(length ==7){
                playerScore += 4;
            }
            else if(length > 7) {
                playerScore += 11;
            }
        }
        
        scoreLabel.setText("" + playerScore);
    }
    
    //DISABLE ALL
    void disableAll() {
        int i,j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                jbuttonArray[i][j].setEnabled(false);
                jbuttonArray[i][j].setBackground(disabled);
            }
        }
    }
    
    //STRIKE OUT
    void strikeOut(int computerScore) {
        int i, j, position = 0;
        int numOfFoundWords = foundWords.size();
        int currentWordPosition;
        int currentWord;
        
        //initialize computerFound to false:
        //and initialize stillNotFound to all words:
        for(i = 0; i < numOfFoundWords; i++){
            computerFound.add(false);
            stillNotFound.add(i);//words the computer hasn't found yet
        }  
        
        //go through computerFound and mark the words the computer found as true:
        for(i = 0; i < computerScore; i++){
            //take a word from the list of words the computer hasn't found yet:
            currentWordPosition = rand.nextInt(stillNotFound.size());
            currentWord = stillNotFound.get(currentWordPosition);
                //the computer has now found it:
                computerFound.set(currentWord, true);
                
                //since it was found, remove it from list of unfound words:
                stillNotFound.remove(currentWordPosition);
        }
        
        //stillNotFound is a list that holds indices that correspond to words in the list
        //of foundWords. It starts as: {0,1,2,3,...,foundWords.size()-1}.
        //then we pick a random position from this list, such as 3, this is currentWordPosition.
        //position 3 corresponds to the index 3 in the list of foundWords. 3 is currentWord.
        //then remove the index at position 3: stillNotFound.remove(3); this removes 3 (the currentWord).
        //Now you have: {0,1,2,4,...,foundWords.size()-2}.
        //and you generate a new random number: if you get 3 again, then:
        //and the word at position 3 is 4. so currentWordPosition is 3, and currentWord is 4.
        
        //after this^, you will have three lists:
        //foundWords: list of all words the player found (Strings)
        //computerFound: true|false list of whether computer found word or not (booleans)
        //stillNotFound: list of all words player found that computer didn't (indices)
        
        
        //Now we want to re-print the list of found words,
        //but cross out the words the computer found:
        //first, be sure to erase the textPane:
        
        try{
        doc.remove(0, textPane.getText().length());
        }
        catch(BadLocationException ex){
        }
        
        position = 0;//initialize position to 0.
        
        for(i = 0; i < numOfFoundWords; i++){//recall: numOfFoundWords is num of words player found
            try {
                if(computerFound.get(i) == true) {//if this is a word the computer found
                    doc.insertString (position, foundWords.get(i) + "\n", strike);
                }
                else {//computer did not find this word
                    doc.insertString (position, foundWords.get(i) + "\n", null);
                }
                position += foundWords.get(i).length() + 1;
            } catch (BadLocationException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            }
        }   
    }
    
    
    //EXIT LISTENER
    public class ExitListener implements ActionListener {
        
        //display a JOptionPane message confirming the user wants to exit
        //using method showConfirmDialog();
        //if yes, exit by calling method System.exit(); passing the value 0 as arg
        //if no, do not exit the application.
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int userChoice = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if(userChoice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }    
    
    
    
    void initComponents() {                                     //DO:
        jframe = new JFrame();  
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //SET DEFAULT CLOSE OPERATION OF JFRAME
        jframe.setLayout(new BorderLayout());                   //USE DEFAULT MANAGER LAYOUT BorderLayout  
        jframe.setSize(500,500);  
        jframe.setResizable(false);
        
        
        //Menubar and Menu
        jmenubar = new JMenuBar();
            jmenu = new JMenu("Boggle");
                    jmenuitem1 = new JMenuItem("New Game");
                    jmenuitem1.addActionListener(resetListener);
                    jmenu.add(jmenuitem1);
                    jmenuitem2 = new JMenuItem("Exit");
                    jmenuitem2.addActionListener(exitListener);
                    jmenu.add(jmenuitem2);
            jmenubar.add(jmenu);
            jframe.setJMenuBar(jmenubar);
                    
                    
        
            //WEST PANEL (Board)
            westPanel = new JPanel();
            westPanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
            westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
            
            
            //Initialize letterHasBeenUsed to false;
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j<4;j++) {
                    letterUsed[i][j] = false;
                }
            }
            
        
            boggleGrid = new JPanel();
            boggleGrid.setLayout(new GridLayout(4,4,1,1));
            
                int counter = -1;   //0-15
                //Create 16 buttons for dice letters
                for(int i = 0; i < 4; i++) {    //rows
                    for(int j = 0; j < 4; j++) {//columns
                        
                        counter++; //keep track of which die we're on. (0-15)
                        
                        //create new die for the board
                        boggleButton = new JButton();
                        boggleButton.setPreferredSize(new Dimension(100, 100));
                        
                        //button style
                        boggleButton.setBackground(lightBlue);
                        boggleButton.setFont(new Font("Sans Serif", Font.PLAIN, 24));
                        
                        //set text of die
                        boggleButton.setText(board.getStoreGameData().get(counter));
                        
                        //add action listener
                        boggleButton.addActionListener(bogglebuttonlistener);
                        
                        //store generated die in 2d array
                        jbuttonArray[i][j] = boggleButton;
                        boggleGrid.add(boggleButton);
                    }
                    
                }
            westPanel.add(boggleGrid);
            
         
         
            //EAST PANEL
            eastPanel = new JPanel();
            eastPanel.setBorder(BorderFactory.createTitledBorder("Words Found"));
            eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
            eastPanel.setMaximumSize(new Dimension(200,800));
                
                //TEXTBOX
                
                doc = new DefaultStyledDocument();
                context = new StyleContext();
                strike = context.addStyle("strikethru", null);
                StyleConstants.setStrikeThrough(strike, true);
                StyleConstants.setForeground(strike, Color.RED);
                textPane = new JTextPane(doc);
                
                textPane.setEditable(false);
                textPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(0, 4, 0, 2)));//top, left, bottom, right
                textPane.setPreferredSize(new Dimension(250,150));
                textPane.setMaximumSize(new Dimension(250,150));
                textPane.setMinimumSize(new Dimension(250,150));
                textPane.setFont(new Font("Sans Serif", Font.PLAIN, 16));
                textPane.setAlignmentX(0.5f);
                
                
                
                
                
                //SCROLLPANE
                scrollpane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollpane.setPreferredSize(new Dimension(250,150));
                scrollpane.setMaximumSize(new Dimension(250,150));
                scrollpane.setMinimumSize(new Dimension(250,150));
                eastPanel.add(scrollpane);
                
                //CLOCK
                clock = new JPanel();
                clock.setLayout(new GridBagLayout());   //this makes the timer
                                                        //vertically centered
                clock.setBorder(BorderFactory.createTitledBorder("Time Left"));
                clock.setPreferredSize(new Dimension(250,150));
                clock.setMaximumSize(new Dimension(250,150));
                clock.setMinimumSize(new Dimension(250,150));
                clock.setSize(250,150);
                
                timer = new Timer(1000, clockListener);    //one second in ms, actionlistener
                timer.start();
                /////
                                
                timeLeft = new JLabel("3:00");
                timeLeft.setFont(new Font("Serif", Font.PLAIN, 64));
                defaultTimeColor = timeLeft.getForeground();
                clock.setAlignmentX(0.5f);
                clock.add(timeLeft);
                
                eastPanel.add(clock);
                     
                //SHAKE BUTTON
                shakeButton = new JButton("Shuffle Cubes");
                shakeButton.setPreferredSize(new Dimension(250,120));
                shakeButton.setMaximumSize(new Dimension(250,120));
                shakeButton.setMinimumSize(new Dimension(250,120));
                shakeButton.setSize(250,120);
                shakeButton.setAlignmentX(0.5f);
                
                //button style
                shakeButton.setFont(new Font("Sans Serif", Font.PLAIN, 16));
                
                shakeButton.addActionListener(resetListener);
                eastPanel.add(shakeButton);
         
        

            //SOUTH PANEL
            southPanel = new JPanel();  
            southPanel.setLayout(new FlowLayout());
            southPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
            
            //inner current word panel
            innerCurrentWord = new JPanel();
            innerCurrentWord.setLayout(new GridBagLayout());
            
            
            //current word label
            currentWordLabel = new JLabel("", SwingConstants.CENTER);
            currentWordLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            currentWordLabel.setPreferredSize(new Dimension(200,60));
            currentWordLabel.setMaximumSize(new Dimension(200,60));
            currentWordLabel.setMinimumSize(new Dimension(200,60));
            
            
            //empty left column
            //this is to push the current word label over to the right
            //my OCD made me do it
            JPanel leftColumn = new JPanel();
            leftColumn.setPreferredSize(new Dimension(10,30));
            leftColumn.setMaximumSize(new Dimension(10,30));
            leftColumn.setMinimumSize(new Dimension(10,30));
            GridBagConstraints emptyColumn = new GridBagConstraints();
            emptyColumn.gridx = 0;
            
            GridBagConstraints word = new GridBagConstraints();
            word.gridx = 1;
            
            //clear word button
            clearWord = new JButton("Clear");
            clearWord.setEnabled(false);
            clearWord.addActionListener(clearWordListener);
            GridBagConstraints submit = new GridBagConstraints();
            submit.gridx = 2;         
            
            innerCurrentWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
            innerCurrentWord.setPreferredSize(new Dimension(300,60));
            innerCurrentWord.setMaximumSize(new Dimension(300,60));
            innerCurrentWord.setMinimumSize(new Dimension(300,60));
                        
            innerCurrentWord.add(leftColumn, emptyColumn);
            innerCurrentWord.add(currentWordLabel, word);
            innerCurrentWord.add(clearWord, submit);
            
            submitWord = new JButton("Submit Word");
            submitWord.setEnabled(false);
            submitWord.setPreferredSize(new Dimension(200,50));
            submitWord.setMaximumSize(new Dimension(200,50));
            submitWord.setMinimumSize(new Dimension(200,50));
            submitWord.setSize(200,40);
            submitWord.addActionListener(submitWordListener);
            
            score = new JPanel();
            scoreLabel = new JLabel("");
            scoreLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            score.setBorder(BorderFactory.createTitledBorder("Score"));
            score.setPreferredSize(new Dimension(140,60));
            score.setMaximumSize(new Dimension(140,60));
            score.setMinimumSize(new Dimension(140,60));
            score.setSize(140,60);
            score.add(scoreLabel);
            
            southPanel.add(innerCurrentWord);
            southPanel.add(submitWord);
            southPanel.add(score);

        
        //Add to Frame
        jframe.getContentPane().add(westPanel, BorderLayout.WEST);
        jframe.getContentPane().add(eastPanel, BorderLayout.EAST);
        jframe.getContentPane().add(southPanel, BorderLayout.SOUTH);
        jframe.pack();
        jframe.setVisible(true);
    }
    
    
    
}
