package it.polito.arditti;

import java.util.Arrays;

public class Configuration {
    private final Game game;
    private final int[] actions;
    private final int index;

    public Configuration(Game game) {
        this.game = game;
        this.actions = new int[game.nPlayers];
        for (int player = 0; player < game.nPlayers; player++) {
            this.actions[player] = 0;
        }
        this.index = 0;
    }

    public Configuration(Game game, int[] actions) {
        this.game = game;
        this.actions = actions;
        for (int player = 0; player < game.nPlayers; player++){
            if (actions[player]<game.nActions[player]-1){
                this.index = player;
                return;
            }
        }
        this.index = -1;
    }

    public Configuration getNext() {
        if( index < 0){
            return null;
        }
        int[] nextActions = Arrays.copyOf(actions,game.nPlayers);
        nextActions[index]++;
        for(int player = 0; player< index-1; player++){
            nextActions[player]=0;
        }
        Configuration next = new Configuration(game, nextActions);
        return next;
    }

    public Configuration getCloser() {
        int[] closerActions = Arrays.copyOf(actions,game.nPlayers);
        for(int player = 0; player< index; player++){
            closerActions[player]=0;
        }
        Configuration closer = new Configuration(game, closerActions);
        return closer;
    }

    public int getCloserPlayer() {
        return this.index;
    }

    public int[] getActions() {
        return this.actions;
    }
}
