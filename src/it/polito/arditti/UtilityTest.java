package it.polito.arditti;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void getHypergraph() {
        int nPlayers = 3;
        int[] nActions = {2, 2, 2};
        GameForm gameForm = new GameForm(nPlayers, nActions);
        double[] utilityValues = {
                // x_2 = 0
                1,0,
                2,3,
                // x_2 = 1
                1,0,
                2,3,
        };
        Utility utility = new Utility(gameForm, utilityValues);
        Hypergraph actual = utility.getHypergraph();
        Hypergraph expected = new Hypergraph();
        expected.groups.add(new HashSet<>(Arrays.asList(0, 1)));
        System.out.println("Expected " + expected);
        System.out.println("Actual " + actual);
        assertAll(actual.groups.stream().map(group -> () ->
                assertTrue(expected.groups.stream().anyMatch(other -> other.containsAll(group)))));
        assertAll(expected.groups.stream().map(group -> () ->
                assertTrue(actual.groups.stream().anyMatch(other -> other.containsAll(group)))));
    }
}