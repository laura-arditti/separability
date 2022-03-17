package it.polito.arditti;

import java.util.ArrayList;
import java.util.List;

public class Game {

    protected final GameForm gameForm;
    protected final Utility[] utilities;

    public Game(int nPlayers, int[] nActions, double[][] utilities) {
        this.gameForm = new GameForm(nPlayers,nActions);
        this.utilities = new Utility[nPlayers];
        for (int player = 0; player<nPlayers; player++){
            this.utilities[player] = new Utility(gameForm,utilities[player]);
        }
    }

    public DirectedHypergraph getDirectedHypergraph() {
        int nPlayers = gameForm.nPlayers;
        Hypergraph[] hypergraphs = new Hypergraph[nPlayers];
        for (int player = 0; player<nPlayers; player++){
            hypergraphs[player] = utilities[player].getHypergraph();
        }
        DirectedHypergraph directedHypergraph = new DirectedHypergraph(hypergraphs);
        return directedHypergraph;
    }

    public GameForm getGameForm() {
        return gameForm;
    }

    public Utility[] getUtilities() {
        return utilities;
    }
}
