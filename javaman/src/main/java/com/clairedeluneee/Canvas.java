package com.clairedeluneee;

/**
 * Abstract class used for displaying various stuff onto the console.
 * @author Claire
 */
public abstract class Canvas {
    /**
     * Clears the screen.
     */
    public static void clear() {
        System.out.print("\u001b[2J");
        System.out.flush();
    }

    /**
     * Shorthand for system.out.println.
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
