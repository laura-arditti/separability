package it.polito.arditti;

import java.util.ArrayList;
import java.util.List;

public class GrayCode {
    public static List<Integer> getCodes(int n) {
        if(n==0){
            List<Integer> result = new ArrayList<Integer>();
            result.add(0);
            return result;
        }

        List<Integer> result = getCodes(n-1);
        int numToAdd = 1<<(n-1);

        for(int i=result.size()-1; i>=0; i--){ //iterate from last to first
            result.add(numToAdd+result.get(i));
        }

        return result;
    }
}
