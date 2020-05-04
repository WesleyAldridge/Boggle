# Boggle

A Boggle game made in Java.

### Rules of Boggle:
(Copied from: https://plentifun.com/rules-for-playing-boggle-word-game):

#### Requirements:
- A 4 × 4 square grid.
- 16 cubes with different letters printed on each of their faces. In this 2D implementation, only the top face of each cube is visible, however the "Shuffle Dice" button works as normal.
- A 3 minute timer.

#### How to play:

- Place all the letter cubes in the cube grid.
- (Optional) Scramble the cubes in the cube grid.
- Start the timer.
- As the timer begins, all players have to simultaneously begin writing all the words that can be formed from the 16 letters that show up in the tray of the cube grid. In this implementation of Boggle, there is one player playing against a computer.
- Players must stop as soon as the timer counts down to zero.

#### Rules:

- Words need to be constructed from alphabets that appear on the faces of connecting adjacent cubes. This neighboring cube can be present horizontally, vertically, or diagonally.
- From the first cube-face a player chooses, no tiles can be skipped to jump to another one when forming a particular word.
- No cube can be used more than once in the same word.
- The direction of sequence of the letters appearing on the cubes is not specific.
- Only the words that can be found in an English dictionary are allowed.
- Slang or abbreviations are not allowed.
- No proper nouns are allowed.
- The created words must have a minimum of 3 letters. Words with less than three letters are not awarded any points.
- Typically, variations of a word with addition of prefix, suffix, or plurals are allowed. However in this implementation, plurals are not allowed.

#### Scoring:
- Words with 3 and 4 letters are awarded 1 point each.
- Words made up of 5 letters are awarded 2 points each.
- Each 6-letter word will add 3 points to the score.
- Each 7-letter word will increment the points tally by 5.
- Words that have 8 or more letters will add 11 points each to the total.
- If a word appears in the list of two or more players, then the word is struck off and is not considered for scoring.
- The alphabets ‘q’ and ‘u’ are printed on a single face, and the use of this tile counts as two letters.



See [./screenshots/](./screenshots/) for example images of the Boggle game.


![](/screenshots/example_game.png "Example Game")
