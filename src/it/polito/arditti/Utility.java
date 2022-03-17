package it.polito.arditti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utility {
    private final double[] utility;
    private final GameForm gameForm;

    public Utility(GameForm gameForm, double[] utility) {
        this.gameForm = gameForm;
        this.utility = utility;
    }

    public Utility(GameForm gameForm){
        this.gameForm = gameForm;
        this.utility= new double[gameForm.getNConfigurations()];
    }

    public Hypergraph getHypergraph() {
        Hypergraph hypergraph = new Hypergraph();
        for (int set = 1; set<1<< gameForm.nPlayers; set++){
            List<List<int[]>> pairList = new ArrayList<>();
            List<Integer> playerList = new ArrayList<>();
            List<Integer> maxOtherActions = new ArrayList<>();
            List<Integer> otherPlayerList = new ArrayList<>();
            for(int player = 0; player< gameForm.nPlayers; player++){
                if (((set>>player)&1)==1){
                    pairList.add(gameForm.getPairsOfActions(player));
                    playerList.add(player);
                }
                else {
                    maxOtherActions.add(gameForm.nActions[player]);
                    otherPlayerList.add(player);
                }
            }
            int[] position = new int[playerList.size()];
            for (int i = 0; i<position.length; i++){
                position[i]=0;
            }

            int[] otherActions = new int[gameForm.nPlayers-playerList.size()];
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
                        int[] strategy = new int[gameForm.nPlayers];
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
                        hypergraph.add(group);
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
        Hypergraph result = new Hypergraph();
        for (Set<Integer> group : hypergraph.groups) {
            boolean keep = true;
            for (Set<Integer> otherGroup : hypergraph.groups){
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
            factor *= gameForm.nActions[player];
        }
        return this.utility[index];
    }

    private void setUtility(int[] strategy, double value) {
        int index = 0;
        int factor = 1;
        for (int player =0; player< strategy.length; player++){
            index += factor*strategy[player];
            factor *= gameForm.nActions[player];
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
