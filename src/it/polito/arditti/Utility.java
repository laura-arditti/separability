package it.polito.arditti;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utility {
    private final double[] utility;
    private final Game game;

    public Utility(Game game, double[] utility) {
        this.game = game;
        this.utility = utility;
    }

    public Utility(Game game){
        this.game = game;
        this.utility= new double[game.getNConfigurations()];
    }

    public Separation getSeparation() {
        Separation separation = new Separation();
        for (int set = 1; set<1<<game.nPlayers; set++){
            List<List<int[]>> pairList = new ArrayList<>();
            List<Integer> playerList = new ArrayList<>();
            List<Integer> maxOtherActions = new ArrayList<>();
            List<Integer> otherPlayerList = new ArrayList<>();
            for(int player = 0; player<game.nPlayers; player++){
                if (((set>>player)&1)==1){
                    pairList.add(game.getPairsOfActions(player));
                    playerList.add(player);
                }
                else {
                    maxOtherActions.add(game.nActions[player]);
                    otherPlayerList.add(player);
                }
            }
            int[] position = new int[playerList.size()];
            for (int i = 0; i<position.length; i++){
                position[i]=0;
            }

            int[] otherActions = new int[game.nPlayers-playerList.size()];
            for (int i = 0; i<otherActions.length; i++){
                otherActions[i]=0;
            }

            boolean outsideFinished = false;
            while(!outsideFinished) {

                boolean insideFinished = false;
                while (!insideFinished) {
                    List<int[]> cube = new ArrayList<>();
                    for (int i = 0; i < position.length; i++) {
                        cube.add(pairList.get(i).get(position[i]));
                    }

                    List<Integer> grayCodes = GrayCode.getCodes(cube.size());

                    double sum = 0;
                    double sign = 1;
                    for (int vertex : grayCodes) {
                        int[] strategy = new int[game.nPlayers];
                        for (int i = 0; i< playerList.size(); i++){
                            strategy[playerList.get(i)] = pairList.get(i).get(position[i])[(vertex>>i)&1];
                        }
                        for (int i = 0; i< otherPlayerList.size(); i++){
                            strategy[otherPlayerList.get(i)] = otherActions[i];
                        }
                        sum += sign*this.getUtility(strategy);
                        sign=-sign;
                    }

                    if( sum != 0){
                        Set<Integer> group = new HashSet<>(playerList);
                        separation.add(group);
                    }

                    insideFinished = true;
                    for (int i = 0; i < position.length; i++) {
                        if (position[i] < pairList.get(i).size()-1) {
                            position[i]++;
                            insideFinished = false;
                            break;
                        }
                        position[i] = 0;
                    }
                }
                outsideFinished = true;
                for (int i = 0; i < otherActions.length; i++) {
                    if (otherActions[i] < maxOtherActions.get(i)-1) {
                        otherActions[i]++;
                        outsideFinished = false;
                        break;
                    }
                    otherActions[i] = 0;
                }
            }
        }
        Separation result = new Separation();
        for (Set<Integer> group : separation.groups) {
            boolean keep = true;
            for (Set<Integer> otherGroup : separation.groups){
                if (otherGroup.containsAll(group)) {
                    if(!group.equals(otherGroup)){
                        keep = false;
                    }
                }
            }
            if (keep){
                result.add(group);
            }
        }
        return result;
    }

    private double getUtility(int[] strategy) {
        int index = 0;
        int factor = 1;
        for (int player =0; player< strategy.length; player++){
            index += factor*strategy[player];
            factor *= game.nActions[player];
        }
        return this.utility[index];
    }

    private void setUtility(int[] strategy, double value) {
        int index = 0;
        int factor = 1;
        for (int player =0; player< strategy.length; player++){
            index += factor*strategy[player];
            factor *= game.nActions[player];
        }
        this.utility[index] = value;
    }

    public double getValue(Configuration configuration) {
        double value = this.getUtility(configuration.getActions());
        return value;
    }

    public void setValue(Configuration configuration, double value) {
        this.setUtility(configuration.getActions(),value);
    }
}
