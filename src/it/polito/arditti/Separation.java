package it.polito.arditti;

import java.util.HashSet;
import java.util.Set;

public class Separation {
    protected final Set<Set<Integer>> groups;

    public Separation() {
        this.groups = new HashSet<>();
    }

    public void add(Set<Integer> group) {
        boolean isMaximal = true;
        for (Set<Integer> otherGroup: groups) {
            if (otherGroup.containsAll(group)){
                isMaximal = false;
                break;
            }
        }
        if(isMaximal){
            groups.add(group);
        }
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Set<Integer> group : this.groups) {
            result.append(group.toString() + ", " );
        }
        return result.toString();
    }

    public Splitting toSplitting(int nPlayers) {
        Separation[] separations = new Separation[nPlayers];
        for (int player = 0; player < nPlayers; player++){
            for (Set<Integer> group : this.groups
                 ) {
                if (group.contains(player)){
                    separations[player].add(group);
                }
            }
        }
        Splitting splitting = new Splitting(separations);
        return splitting;
    }
}
