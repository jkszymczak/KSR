package FuzzyCalculations;

public abstract class Label{
    MembershipFunction membershipFunction;
    String label;

    public Label(MembershipFunction membershipFunction, String label) {
        this.membershipFunction = membershipFunction;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
}
