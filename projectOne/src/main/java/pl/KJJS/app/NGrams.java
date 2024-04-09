package pl.KJJS.app;

import java.util.ArrayList;
import java.util.List;

public class NGrams {

    double f(double n, double n1, double n2) {

        return 2 / ((n - n1 + 1) * (n - n1 + 2) - (n - n2 + 1) * (n - n2));
    }

    public static String[] splitToSubstring(String s, int n) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if(i+n-1>=s.length()){
                continue;
            }
            substrings.add(s.substring(i,i+n));
        }
        return substrings.toArray(new String[0]);
    }

    public static double calculateDistance(String w1, String w2) {

        int n1Length = w1.length();
        int n2Length = w2.length();
        int nLength = Math.max(n1Length, n2Length);
        return 0;
    }
}
