package it.polito.arditti;

public class Splitting {
    private final Separation[] splitting;

    public Splitting(Separation[] separations) {
        this.splitting = separations;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int player = 0; player<splitting.length; player++) {
            result.append("Separation of player " + player + ": " + splitting[player].toString() + "\r\n" );
        }
        return result.toString();
    }
}
