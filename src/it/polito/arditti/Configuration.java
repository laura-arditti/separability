package it.polito.arditti;

import java.util.Arrays;

public class Configuration {
    private final GameForm gameForm;
    private final int[] actions;
    private final int index;

    public Configuration(GameForm gameForm) {
        this.gameForm = gameForm;
        this.actions = new int[gameForm.nPlayers];
        for (int player = 0; player < gameForm.nPlayers; player++) {
            this.actions[player] = 0;
        }
        this.index = 0;
    }

    public Configuration(GameForm gameForm, int[] actions) {
        this.gameForm = gameForm;
        this.actions = actions;
        for (int player = 0; player < gameForm.nPlayers; player++){
            if (actions[player]<gameForm.nActions[player]-1){
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
        int[] nextActions = Arrays.copyOf(actions,gameForm.nPlayers);
        nextActions[index]++;
        for(int player = 0; player< index-1; player++){
            nextActions[player]=0;
        }
        Configuration next = new Configuration(gameForm, nextActions);
        return next;
    }

    public Configuration getCloser() {
        int[] closerActions = Arrays.copyOf(actions,gameForm.nPlayers);
        for(int player = 0; player< index; player++){
            closerActions[player]=0;
        }
        Configuration closer = new Configuration(gameForm, closerActions);
        return closer;
    }

    public int getCloserPlayer() {
        return this.index;
    }

    public int[] getActions() {
        return this.actions;
    }
}
