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

    // TODO: allow for file picking support
    /**
     * Selection of words that may be picked.
     */
    public static final String[] WORD_BANK = {
        // Coding languages that are at least 4 letters long
        "javascript", "typescript", "python", "cobol", "scratch", "csharp", "java", "actionscript",

        // Filipino slang
        "ngani", "penge", "omsim", 
    
        // Programming terms
        "exception", "compiler", "interpreter", "method", "variable", "class", "process", "entry point",
        "memory", "programming",

        // Group picks
        "banana", "ironman", "spiderman", "superhero", "thanos", "godzilla", "system",
        "universe", "planet", "future", "random", "folder", "programming",

        "start", "stage", "reset", "guide", "focus", "block", "dodge", "build", "guard", "track",
        "moves", "teams", "troll", "climb", "shoot"
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
    private int unguessedCharactersLeft = -1;

    /**
     * Gets the selected word.
     * @return the selected word, or an empty string if there is currently none.
     */
    public String getSelectedWord() { return this.selectedWord == null ? "" : this.selectedWord;}

    /**
     * Sets the selected word.
     * @param word
     */
    public void setSelectedWord(String word) { this.selectedWord = word;}

    // Internal variable to hold the Scanner class.
    private Scanner scanner;

    // Constructors
    public Game(Scanner s) {
        this.scanner = s;

    }

    public Game() {
        this.scanner = new java.util.Scanner(System.in);

    }

    // Methods
    /**
     * Checks if a character is within the selected word.
     * @param guess
     * @return true if present, false otherwise or if no word is selected.
     */
    public boolean checkGuess(char guess) {

        // Assure that the selected word is not null.
        if (this.getSelectedWord() == null) return false;

        return this.getSelectedWord().contains((guess + "").toLowerCase());
    } 

    /**
     * Asks the user for input. Catches invalid inputs and returns an empty string if it happens.
     * @return
     */
    public String safeInput() {

        String output = "";

        try {
            // Get the next line.
            output = scanner.nextLine();
        
        } catch (Exception e) {
            Canvas.print(e.getMessage());
        }

        return output.toLowerCase();
    }

    /**
     * Sets up the wordstate for the game. Also randomizes what word is selected.
     * @param pullFromWordBank
     */
    public void setup(boolean pullFromWordBank) {
        // Pull word from word bank if is specified
        if (pullFromWordBank) {
            Random r = new Random();
            this.setSelectedWord(Game.WORD_BANK[r.nextInt(Game.WORD_BANK.length-1)]);
        }
        // Set wordstate and replace all non-whitespace characters with underscores
        wordState = "";
        for (char c : this.getSelectedWord().toCharArray()) wordState += c == ' ' ? " " : "_";
    }

    /**
     * Game loop.
     */
    public void start() {

        // Sets up selected word.
        this.setup(this.selectedWord == null);

        // Primary game loop.
        while (true) {

            // Clear screen.
            Canvas.clear();

            // Print art.
            Canvas.print(Game.ASCII_ART[guessesRemaining == 0 ? 0 : 5-(guessesRemaining - 1)]);

            // Print current word status
            Canvas.print(this.wordState + "\n" + "guesses left: " + guessesRemaining);

            // Check game state.
            if (guessesRemaining == 0 || unguessedCharactersLeft == 0 || !wordState.contains("_")) break;
            
            // Ask user.
            Canvas.print("enter your guess below");
            String output = safeInput();

            // Check for empty input.
            if (output.equals("")) continue;  

            // Check for non-alphabetical characters by using their character code.
            int charcode = (int) output.charAt(0);

            if (charcode < 65) continue;
            if (charcode > 122) continue;
            if (charcode > 90 && charcode < 97) continue;
            
            
            // Coalese and check output.
            boolean correct = this.checkGuess(output.charAt(0));

            if (correct) {
                // Reset wordstate and unguessed characters.
                unguessedCharactersLeft = 0;
                
                // Refresh wordstate.
                // Iterate through selected word characters.
                for (int index = 0; index < this.getSelectedWord().length(); index++) {

                    // Check if current character matches guess.
                    if (selectedWord.charAt(index) == output.charAt(0)) {
                        // Add guess to wordstate.
                        // 
                        // selectedWord = cobol
                        // output = c;
                        // output.charAt(0) = c;
                        //
                        // Prints as
                        // c____
                        StringBuilder sb = new StringBuilder(wordState);
                        sb.setCharAt(index, output.charAt(0));
                        wordState = sb.toString();
                    } else {
                        unguessedCharactersLeft++;
                    }

                }
            } else {
                // Deduct guesses.
                this.guessesRemaining--;
            }
        }  

        // Check if the player ran out of guesses. By this point, the while loop is
        // now broken. If they did, they lose. Otherwise, they win.
        if (guessesRemaining == 0) {
            Canvas.print("gg go next");
        } else {
            Canvas.print("gg wp");
        }
    }
}
