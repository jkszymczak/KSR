package FuzzyCalculations;

import Database.BlockGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuzzySet {
    private SumQuaLabel membershipFunction;
    private Map<Integer,Member> elements;
//    private String label;
//    private Columns column;

    public FuzzySet(List<BlockGroup> candidates,MembershipFunction func , String label, Columns column) {
        this.membershipFunction = new SumQuaLabel(func,column,label);
//        this.label = label;
//        this.column = column;
        this.assignMemberships(candidates);
    }
    /** This function calculates membership values for candidates, and removes every element with 0 value of membership,
     * to change it to not remove any members, delete filter */
    public void assignMemberships(List<BlockGroup> candidates){
//        this.elements =
         this.elements = candidates.stream()
                .map(v -> this.assignMembership(v))
                .filter(v -> v.getMembership() != 0.0) // Yep this one
                .collect(Collectors.toMap(k -> k.getElement().getIndex(), v->v));
    }
    public Member assignMembership(BlockGroup candidate){
        return new Member(candidate,this.membershipFunction.evaluate(candidate));
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction.getMembershipFunction();
    }

    public Map<Integer,Member> getElements() {
        return elements;
    }

    public String getLabel() {
        return membershipFunction.getLabel();
    }

    public Columns getColumn() {
        return membershipFunction.getColumn();
    }
}
