package FuzzyCalculations;

import Database.BlockGroup;

import java.util.*;
import java.util.stream.Collectors;

public class FuzzySet {
    private SumQuaLabel membershipFunction;
    private String label;
    private Map<Integer,Member> elements;
    String conjunction = "AND";

//    private String label;
//    private Columns column;

    public FuzzySet(List<BlockGroup> candidates,MembershipFunction func , String label, Columns column) {
        this.membershipFunction = new SumQuaLabel(func,column,label);
        this.label = label;
//        this.column = column;
        this.assignMemberships(candidates);
    }
    private FuzzySet(Map<Integer,Member> members,String label){
        this.elements = members;
        this.label = label;
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

    public FuzzySet and(FuzzySet other){
        Map<Integer,Member> first = this.getElements();
        Map<Integer,Member> second = other.getElements();
        Map<Integer,Member> result = new HashMap<>();
        Set firstkeys = first.keySet();
        Set secondkeys = second.keySet();
        Set<Integer> keys = new HashSet<>(firstkeys);
        keys.addAll(secondkeys);
        for (Integer index:keys){
            Member firstmember = null;
            Member secondmember = null;
            if (first.containsKey(index)){
                firstmember = first.get(index);
            }
            if (second.containsKey(index)){
                secondmember = second.get(index);
            }
            if (firstmember != null && secondmember != null){
                result.put( index,new Member(firstmember.getElement(),
                        Math.min(firstmember.getMembership(),secondmember.getMembership())
                ));
            }

        }
        return new FuzzySet(result,this.label+" "+this.conjunction+" "+other.getLabel());
    }

    public MembershipFunction getMembershipFunction() {
        return this.membershipFunction.getMembershipFunction();
    }

    public Map<Integer,Member> getElements() {
        return this.elements;
    }

    public String getLabel() {
        return this.label;
    }

    public Columns getColumn() {
        return membershipFunction.getColumn();
    }

    @Override
    public String toString() {
        return "FuzzySet{" +
                "membershipFunction=" + ((this.membershipFunction == null) ? "NULL" : membershipFunction.toString()) +
                ", label='" + label.toString() + '\'' +
                ", elements=" + elements.toString() +
                '}';
    }
}
