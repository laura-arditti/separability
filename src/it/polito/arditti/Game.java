package it.polito.arditti;

import java.util.ArrayList;
import java.util.List;

public class Game {
    protected final int nPlayers;
    protected final int[] nActions;
    protected final Utility[] utilities;

    public Game(int nPlayers, int[] nActions, double[][] utilities) {
        this.nPlayers = nPlayers;
        this.nActions = nActions;
        this.utilities = new Utility[nPlayers];
        for (int player = 0; player<nPlayers; player++){
            this.utilities[player] = new Utility(this,utilities[player]);
        }
    }

    public Splitting getSplitting() {
        Separation[] separations = new Separation[nPlayers];
        for (int player = 0; player<nPlayers; player++){
            separations[player] = utilities[player].getSeparation();
        }
        Splitting splitting = new Splitting(separations);
        return splitting;
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
