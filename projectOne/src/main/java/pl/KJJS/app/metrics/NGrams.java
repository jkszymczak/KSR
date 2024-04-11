package pl.KJJS.app.metrics;

import java.util.ArrayList;
import java.util.List;

public class NGrams {

    public static double f(double n, double n1, double n2) {

        return 2 / ((n - n1 + 1) * (n - n1 + 2) - (n - n2 + 1) * (n - n2));
    }

    public static String[] splitToSubstring(String s, int n) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < s.length()-n+1; i++) {

            substrings.add(s.substring(i,i+n));
        }
        return substrings.toArray(new String[0]);
    }

    public static double calculateDistance(String w1, String w2) {
        int n1 = 2;
        int n2 = 5;
        int n1Length = w1.length();
        int n2Length = w2.length();
        int n = Math.max(n1Length,n2Length);
        double fn = f(n,n1,n2);
        int result =0;
        for (int i = n1; i <= n2; i++) {
            String[] substrings = splitToSubstring(w1,i);
            for (String s:substrings){
                if(w2.contains(s)){

                    result++;
                }
            }
        }
        return 1-(result*fn);
    }
}
