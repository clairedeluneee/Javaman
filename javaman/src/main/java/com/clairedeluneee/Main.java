package com.clairedeluneee;

/**
 * This is a rudimentary version of the game Hangman in the form of a Java 21 terminal program. 
 * Wordbank can be edited in code, however, using a wordbank file is planned.
 */

/**
 * The main class of the method.
 */
public class Main {

    // The game variable.
    static Game game;

    // The scanner used for input.
    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    public static void main(String[] args) {
        game = new Game(scanner);
        game.start();
    }
}