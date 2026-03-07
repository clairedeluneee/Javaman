package com.clairedeluneee;

import java.util.Random;
import java.util.Scanner;

/**
 * Our primary game object. This is where the game logic goes.
 * @author Claire
 */
public class Game {
    // -------------------------------------------
    // Variables + Mutators / Accessor methods
    // -------------------------------------------

    /**
     * Selection of words that may be picked.
     */
    public static final String[] WORD_BANK = {
        // Coding languages that are at least 4 letters long
        "javascript", "typescript", "python", "cobol", "scratch", "csharp", "java", "actionscript",

        // Filiino slang
        "ngani", "penge", "omsim", 
    
        // Programming terms
        "exception", "compiler", "interpreter", "method", "variable", "class", "process", "entry point"
    };

    /**
     * ASCII art used for visualizing how many attempts remain.
     * Pulled from https://gist.github.com/chrishorton/8510732aa9a80a03c829b09f12e20d9c 
     * */
    public static final String[] ASCII_ART = {
        """
            +---+
            |   |
                |
                |
                |
                |
        =========
        """,
        """
            +---+
            |   |
            O   |
                |
                |
                |
        =========   
        """,
        """
            +---+
            |   |
            O   |
            |   |
                |
                |
        =========
        """,
        """
            +---+
            |   |
            O   |
           /|   |
                |
                |
        =========      
        """,
        """
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
        =========               
        """,
        """
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
        =========
        """,
        """
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
        =========        
        """
    };

    // Internal variable to keep track of how many attempts the player has left.
    private int guessesRemaining = 6;
    /**
     * Returns how many guesses the player has left.
     * @return Guesses remaining
     */
    public int getGuessesRemaining() { return this.guessesRemaining; }

    // Internal variable to keep track of the current selected word.
    private String selectedWord;

    // Internal variable to keep track of the state of the current word.
    private String wordState;

    // Internal variable to keep track of how many letters remain to be guessed.
    private int unguessedCharactersLeft = 0;

    public String getSelectedWord() { return this.selectedWord;}
    public void setSelectedWord(String word) { this.selectedWord = word;}

    // Methods
    /**
     * Checks if a character is within the selected word.
     * @param guess
     * @return true if present, false otherwise or if no word is selected.
     */
    public boolean checkGuess(char guess) {

        // Assure that the selected word is not null.
        if (this.selectedWord == null) return false;

        // Iterates through each character of the selected word. Breaks and returns true if it is the case.
        // Each iteration is lowecased along with the answer to ensure that cases won't matter.
        for (int characterIndex = 0; characterIndex < this.selectedWord.length(); characterIndex++) {
            if (this.selectedWord.toLowerCase().indexOf(characterIndex) == (guess + "").toLowerCase().charAt(0)) return true;
        }

        // If code keeps going, this means that the character guessed is NOT part of the selected word.
        return false;
    } 

    /**
     * Asks the user for input. Catches invalid inputs and returns an empty string if it happens.
     * @return
     */
    public String safeInput() {

        String output = "";

        try (Scanner scanner = new Scanner(System.in)) {
            // Get the next line.
            output = scanner.nextLine();

            // Close the scanner. Should prevent memory leaks.
            scanner.close(); 
        
        } catch (Exception e) {
            
        }

        return output;
    }

    /**
     * Game loop.
     */
    public void start() {
        // Pull word from word bank
        Random r = new Random();
        this.selectedWord = Game.WORD_BANK[r.nextInt(Game.WORD_BANK.length-1)];

        // Set wordstate and replace all non-whitespace characters with underscores
        wordState = "";
        for (char c : this.selectedWord.toCharArray()) wordState += c == ' ' ? " " : "_";

        while (true) {
            // Check game state
            if (guessesRemaining == 0 || unguessedCharactersLeft == 0) break;

            // Clear screen.
            Canvas.clear();

            // Print art.
            Canvas.print(Game.ASCII_ART[guessesRemaining == 0 ? 0 : guessesRemaining - 1]);

            // Print current word status
            Canvas.print(this.wordState + "\n" + "guesses left: " + guessesRemaining);

            // Ask user
            Canvas.print("enter your guess below");
            String output = safeInput();

            // Check for empty input
            if (output.equals("")) continue;  

            // Check for non-alphabetical characters by using their character code
            int charcode = (int) output.charAt(0);

            if (charcode < 65) continue;
            if (charcode > 122) continue;
            if (charcode > 90 && charcode < 97) continue;
            
            
            // Coalese and check output
            boolean correct = this.checkGuess(output.charAt(0));

            if (correct) {
                // Reset wordstate and unguessed characters
                wordState = "";
                unguessedCharactersLeft = 0;
                for (int characterIndex = 0; characterIndex < this.selectedWord.length(); characterIndex++) {
                    if (this.selectedWord.toLowerCase().indexOf(characterIndex) == (output.charAt(0) + "").toLowerCase().charAt(0)) {
                        wordState += output.charAt(0);
                    } else {
                        if (this.selectedWord.toLowerCase().indexOf(characterIndex) == ' ') {
                            wordState += " ";
                        } else {
                            wordState += "_";
                            unguessedCharactersLeft++;
                        }
                    }
                }                
            } else {
                this.guessesRemaining--;
            }
        }  

        if (guessesRemaining == 0) {
            Canvas.print("gg go next");
        } else {
            Canvas.print("gg wp");
        }
    }
}
