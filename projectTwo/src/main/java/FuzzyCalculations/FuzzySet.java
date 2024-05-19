package FuzzyCalculations;

import java.util.List;

public class FuzzySet implements Label{
    MembershipFunction membershipFunction;
    List<Member> elements;
    String label;

    public FuzzySet(List<BlockGroup> candidates,MembershipFunction membershipFunction) {
        this.membershipFunction = membershipFunction;
    }
    public void assignMembership(List<BlockGroup> candidates){
        return;
    }

}
