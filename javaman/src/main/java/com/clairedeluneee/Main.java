package com.clairedeluneee;
public class Main {
    static Game game;
    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    public static void main(String[] args) {
        game = new Game(scanner);
        game.start();
    }
}