package pl.KJJS.app;

public class HammingDistance {
    public static int calculateDistance(Boolean[] v1,Boolean[] v2){
        // Zapytać kacpra czy tu powinniśmy liczyć różnice w długości jako różnice między wektorami
        int distance =0;
        for (int i = 0; i < v1.length; i++) {
            if(v1[i]!=v2[i]){
                distance++;
            }

        }

        return distance;
    }
}
