package it.polito.arditti;

public class DirectedHypergraph {
    private final Hypergraph[] hypergraphs;

    public DirectedHypergraph(Hypergraph[] hypergraphs) {
        this.hypergraphs = hypergraphs;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int player = 0; player < hypergraphs.length; player++) {
            result.append("Hyperlink head sets of player " + player + ": " + hypergraphs[player].toString(player) + "\r\n" );
        }
        return result.toString();
    }
}
