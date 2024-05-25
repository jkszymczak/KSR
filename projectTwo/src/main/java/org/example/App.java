package org.example;

import Builders.FuzzyQuantifierBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.*;
import LinguisticSummarization.LinguisticSummaryGenerator;
import LinguisticSummarization.LinguisticSummaryType;
import LinguisticSummarization.QualityMeasures;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    static FuzzyQuantifier createAbsolute(List<BlockGroup> data){
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, data.size())
                .withType(QuantifierType.absolute)
                .createLabel().withLabel("few").createMembershipFunction().createTriangle(0,0,350).build().build()
                .createLabel().withLabel("hundreds").createMembershipFunction().createTrapezoidal(100,300,800,1000).build().build()
                .createLabel().withLabel("around 1000").createMembershipFunction().createGaussian(1000,300).build().build()
                .createLabel().withLabel("close to 2000-3000").createMembershipFunction().createTrapezoidal(1000,2000,3000,4500).build().build()
                .createLabel().withLabel("approximately 5000").createMembershipFunction().createGaussian(5000,800).build().build()
                .createLabel().withLabel("nearly 6000").createMembershipFunction().createGaussian(6000,1000).build().build()
                .createLabel().withLabel("above 7500").createMembershipFunction().createTrapezoidal(7000,8000,11000,11000).build().build()
                .end();
    }

    static FuzzyQuantifier createRelative() {
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, 1)
                .withType(QuantifierType.relative)
                .createLabel().withLabel("almost_none").createMembershipFunction().createTrapezoidal(0,0,0.05,0.2).build().build()
                .createLabel().withLabel("around 20 %").createMembershipFunction().createGaussian(0.2,0.05).build().build()
                .createLabel().withLabel("nearly 1/3").createMembershipFunction().createTrapezoidal(0.25,0.3,0.36,0.45).build().build()
                .createLabel().withLabel("approximately half").createMembershipFunction().createTrapezoidal(0.35,0.45,0.55,0.65).build().build()
                .createLabel().withLabel("most").createMembershipFunction().createTrapezoidal(0.55,0.7,0.8,1).build().build()
                .createLabel().withLabel("almost all").createMembershipFunction().createTrapezoidal(0.8,0.95,1,1).build().build()
                .end();
    }

    static SummarizerQualifier createBedroomToRoomRatio(List<BlockGroup> data){
        return SummarizerQualifierBuilder.builder(null)
                .onRange(1.0,10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                    .withCandidates(data).withLabel("insufficient share of bedrooms")
                    .createMembershipFunction().createTrapezoidal(0,1,1.5,2.5).build()
                .build().end();

    }

    static SummarizerQualifier fullDataSet(List<BlockGroup> data){
        FuzzySet fuzzySet = new FuzzySet(data,new SetMembership(),"all",null);
        return new SummarizerQualifier(fuzzySet);
    }

    public static void main( String[] args )
    {
        String path = "dataBasePrep/prepared.csv";

//        System.out.println( "Hello World!" );
        List<BlockGroup> data = CSV.readCSV(path);
//        System.out.println(data.get(10).toString());
        LinguisticSummaryGenerator lsG = new LinguisticSummaryGenerator(createAbsolute(data),createBedroomToRoomRatio(data),fullDataSet(data),"have",null,"BlockGroups",new QualityMeasures(), LinguisticSummaryType.First);
        System.out.println(lsG.generateSummaries().toString());

    }
}
