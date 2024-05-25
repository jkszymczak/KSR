package LinguisticSummarization;

import Builders.FuzzyQuantifierBuilder;
import Builders.LinguisticSummaryBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;
import FuzzyCalculations.*;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LinguisticSummaryTest extends TestCase {

    public void testGenerate(){
        Map<String,Object> m1 = new HashMap<>();
        Map<String,Object> m2 = new HashMap<>();
        m1.put("index",1);
        m1.put("population",10.0);
        m1.put("households",9.0);

        m2.put("index",2);
        m2.put("population",15.0);
        m2.put("households",15.0);

        Map<String,Object> m3 = new HashMap<>();
        Map<String,Object> m4 = new HashMap<>();
        m3.put("index",3);
        m3.put("population",10.0);
        m3.put("households",15.0);

        m4.put("index",4);
        m4.put("population",20.0);
        m4.put("households",20.0);

        BlockGroup b1 = new BlockGroup(m1);
        BlockGroup b2 = new BlockGroup(m2);
        BlockGroup b3 = new BlockGroup(m3);
        BlockGroup b4 = new BlockGroup(m4);
        List<BlockGroup> blockGroups = new LinkedList<>();
        blockGroups.add(b1);
        blockGroups.add(b2);
        blockGroups.add(b3);
        blockGroups.add(b4);
        SummarizerQualifier sum1 = SummarizerQualifierBuilder.builder(true).createFuzzySet()
                .onColumn(Columns.population).withLabel("Mało").withCandidates(blockGroups).createMembershipFunction()
                .createTriangle(0,10,20).build().build().end();
        SummarizerQualifier sum2 = SummarizerQualifierBuilder.builder(true).createFuzzySet()
                .onColumn(Columns.households).withLabel("Tanie").withCandidates(blockGroups).createMembershipFunction()
                .createTrapezoidal(10,20,30,40).build().build().end();
//        System.out.println(sum1.fuzzySet.toString());
//        System.out.println(sum2.fuzzySet.toString());
        SummarizerQualifier joined = sum1.and(sum2);
        SummarizerQualifier clas = SummarizerQualifierBuilder.builder(false).createFuzzySet()
                .withMembershipFuntion(new SetMembership()).withLabel("Whole").withCandidates(blockGroups).onColumn(Columns.households).build().end();
        FuzzyQuantifier quantifier = FuzzyQuantifierBuilder.builder().createLabel()
                .createMembershipFunction().createTriangle(0,2,3).build()
                .withLabel("troche").build()
                .createLabel().withLabel("więcej").createMembershipFunction().createTriangle(2,3,4).build()
                .build().withType(QuantifierType.absolute).end();
        LinguisticSummary summary = LinguisticSummaryBuilder.builder().withSubject("Block groups")
                .withLinguisticSummaryType(LinguisticSummaryType.First).withSummarizatorConjunction("eee")
                .withSummarizator(joined).withQualifier(clas).withQuantifier(quantifier).build();
        System.out.println(summary.generateSummaries().toString());
        LinguisticSummary summary2 = LinguisticSummaryBuilder.builder().withSubject("Block groups")
                .withLinguisticSummaryType(LinguisticSummaryType.Second).withSummarizatorConjunction("eee")
                .withQualifierConjunction("hello")
                .withSummarizator(joined).withQualifier(clas).withQuantifier(quantifier).build();
        System.out.println(summary2.generateSummaries().toString());
    }

}