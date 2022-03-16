package it.polito.arditti;

public class PotentialGame extends Game{
    private Utility potential;

    public PotentialGame(int nPlayers, int[] nActions, double[][] utilities) {
        super(nPlayers, nActions, utilities);
    }

    public Utility getPotential() {
        if(this.potential == null){
            potential = new Utility(this);
            Configuration start = new Configuration(this);
            Configuration next = start.getNext();
            while(next != null) {
                Configuration closer = start.getCloser();
                int closerPlayer = start.getCloserPlayer();
                double value = potential.getValue(closer) + utilities[closerPlayer].getValue(next) - utilities[closerPlayer].getValue(closer);
                potential.setValue(next, value);
                start = next;
                next = start.getNext();
            }
        }
        return this.potential;
    }

    public Splitting getNormalizedSplitting() {
        Separation separation = this.getPotential().getSeparation();
        Splitting splitting = separation.toSplitting(this.nPlayers);
        return splitting;
    }
}
