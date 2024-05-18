package FuzzyCalculations;

import java.util.List;

public class FuzzySet {
    MembershipFunction membershipFunction;
    List<Member> elements;
    String label;

    public FuzzySet(List<BlockGroup> candidates,MembershipFunction membershipFunction) {
        this.membershipFunction = membershipFunction;
    }
    private void assignMembership(BlockGroup membership){
        return;
    }

}
