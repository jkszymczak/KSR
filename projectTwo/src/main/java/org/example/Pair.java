package org.example;

public class Pair <F,S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "( " + first.toString() + ", " + second.toString() + " )";
    }

    public String toWrite() {
        return first.toString() + ". [ " + second.toString() + " ]";
    }
}
