package it.polito.arditti;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int nPlayers = 4;
        int[] nActions = {3, 4, 3, 2};
        Random rd = new Random();
        double[][] utilities = new double[4][72];
        for (int x = 0; x <72 ; x++) {
            for (int player = 0; player < nPlayers; player++) {
                utilities[player][x] = rd.nextDouble();
             }
        }
        //for (int x = 0; x < 12; x++) {
           // for (int player = 0; player < nPlayers; player++) {
          //      utilities[player][x] = 0;
         //   }
        //}
        Game game = new Game(nPlayers, nActions, utilities);
        Splitting splitting = game.getSplitting();
        System.out.println(splitting);

        PotentialGame potentialGame = new PotentialGame(nPlayers, nActions, utilities);
        Splitting normalizedSplitting = potentialGame.getNormalizedSplitting();
        System.out.println(normalizedSplitting);

    }
}
