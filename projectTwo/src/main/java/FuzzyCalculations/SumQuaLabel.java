package FuzzyCalculations;

import Database.BlockGroup;

public class SumQuaLabel extends Label{
    Columns column;
    public SumQuaLabel(MembershipFunction membershipFunction,Columns column, String label) {
        super(membershipFunction, label);
        this.column = column;
    }

    public double evaluate(BlockGroup x){

        try {
            return this.membershipFunction.evaluate(x.getColumns().get(column.toString()));
        } catch (Exception e){
            throw e;
        }

    }

    public Columns getColumn() {
        return this.column;
    }

}
