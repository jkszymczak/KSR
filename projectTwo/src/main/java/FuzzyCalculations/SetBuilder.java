package FuzzyCalculations;

import Builders.Builder;

public interface SetBuilder<T,N extends Builder<?,?>,B> extends Builder<T,B> {
    N withMembershipFuntion(MembershipFunction MembershipFunction);
}
