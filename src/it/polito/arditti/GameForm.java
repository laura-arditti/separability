package it.polito.arditti;

import java.util.ArrayList;
import java.util.List;

public class GameForm {
    protected final int nPlayers;
    protected final int[] nActions;

    public GameForm(int nPlayers, int[] nActions) {
        this.nPlayers = nPlayers;
        this.nActions = nActions;
    }

    public List<int[]> getPairsOfActions (int player){
        List<int[]> pairsOfActions = new ArrayList<>();
        for (int a1=0; a1<nActions[player]; a1++){
            for (int a2=a1+1; a2<nActions[player]; a2++){
                pairsOfActions.add(new int[]{a1, a2});
            }
        }
        return pairsOfActions;
    }

    public int getNConfigurations(){
        int result = 1;
        for (int player = 0; player<nPlayers; player++){
            result*=nActions[player];
        }
        return result;
    }
}
