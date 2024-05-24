package FuzzyCalculations;

import Database.BlockGroup;

public class Member{
    private BlockGroup element;
    private double membership;
    public Member(BlockGroup element, double membership){
        this.element = element;
        this.membership = membership;
    }

    public double getMembership() {
        return membership;
    }

    public BlockGroup getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "Member{" +
                "element=" + element.toString() +
                ", membership=" + membership +
                '}';
    }
}
