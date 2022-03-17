package it.polito.arditti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hypergraph {
    protected final Set<Set<Integer>> groups;

    public Hypergraph() {
        this.groups = new HashSet<>();
    }

    public void add(Set<Integer> group) {
        boolean isMaximal = true;
        List<Set<Integer>> subsets = new ArrayList<>();
        for (Set<Integer> otherGroup: groups) {
            if (otherGroup.containsAll(group)){
                isMaximal = false;
                break;
            } else if (group.containsAll(otherGroup)) {
                subsets.add(otherGroup);
            }
        }
        if(isMaximal){
            groups.add(group);
            groups.removeAll(subsets);
        }
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Set<Integer> group : this.groups) {
            result.append(group.toString() + ", " );
        }
        return result.toString();
    }

    public String toString(int tailPlayer) {
        StringBuffer result = new StringBuffer();
        for (Set<Integer> group : this.groups) {
            if (group.contains(tailPlayer)){
                Set<Integer> head = new HashSet<>(group);
                head.remove(tailPlayer);
                result.append(head + ", " );
            }
        }
        return result.toString();
    }

    public DirectedHypergraph toDirected(int nPlayers) {
        Hypergraph[] hypergraphs = new Hypergraph[nPlayers];
        for (int player = 0; player < nPlayers; player++){
            Hypergraph hypergraph = new Hypergraph();
            for (Set<Integer> group : this.groups
                 ) {
                if (group.contains(player)){
                    hypergraph.add(group);
                }
            }
            hypergraphs[player] = hypergraph;
        }
        DirectedHypergraph directedHypergraph = new DirectedHypergraph(hypergraphs);
        return directedHypergraph;
    }
}
