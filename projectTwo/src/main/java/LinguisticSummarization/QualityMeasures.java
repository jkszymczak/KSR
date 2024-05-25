package LinguisticSummarization;

import FuzzyCalculations.Member;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.QuantifierType;
import FuzzyCalculations.SummarizerQualifier;

public class QualityMeasures {
    double r(SummarizerQualifier summarizer,SummarizerQualifier qualifier,QuantifierType quantifierType){
        SummarizerQualifier joined = summarizer.and(qualifier);
        Double summaryzatorSum=0.0;
        for (Member m:joined.getElements().values()){
            summaryzatorSum+=m.getMembership();
        }
        if(quantifierType == QuantifierType.absolute){
            return summaryzatorSum;
        }

        Double qualifierSum = 0.0;
        for (Member m:qualifier.getElements().values()){
            qualifierSum+=m.getMembership();
        }
        return summaryzatorSum/qualifierSum;
        //te 3 wzorki
//        return 0;
    }
     double t1(SummarizerQualifier summarizer,SummarizerQualifier qualifier,QuantifierLabel qualifierLabel,QuantifierType quantifierType){
        double r = this.r(summarizer, qualifier, quantifierType);
        return qualifierLabel.evaluate(r);
    };
    double t2(){
        return 0;
    };
    double t3(){
        return 0;
    };
    double t4(){
        return 0;
    };
    double t5(){
        return 0;
    };
    double t6(){
        return 0;
    };

    double t7(){
        return 0;
    };
    double t8(){
        return 0;
    };
    double t9(LinguisticSummary linguisticSummary){
        return 0;
    };
    double t10(){
        return 0;
    };
    double t11(){
        return 0;
    };

}
