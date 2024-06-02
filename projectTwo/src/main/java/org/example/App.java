package org.example;

import Builders.ContainerBuilder;
import Builders.FuzzyQuantifierBuilder;
import Builders.LinguisticSummaryBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.*;
import LinguisticSummarization.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    static FuzzyQuantifier createAbsolute(List<BlockGroup> data) {
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, data.size())
                .withType(QuantifierType.absolute)
                .createLabel().withLabel("few").createMembershipFunction().createTriangle(0, 0, 350).build().build()
                .createLabel().withLabel("hundreds").createMembershipFunction().createTrapezoidal(100, 300, 800, 1000).build().build()
                .createLabel().withLabel("around 1000").createMembershipFunction().createGaussian(1000, 300).build().build()
                .createLabel().withLabel("close to 2000-3000").createMembershipFunction().createTrapezoidal(1000, 2000, 3000, 4500).build().build()
                .createLabel().withLabel("approximately 5000").createMembershipFunction().createGaussian(5000, 800).build().build()
                .createLabel().withLabel("nearly 6000").createMembershipFunction().createGaussian(6000, 1000).build().build()
                .createLabel().withLabel("above 7500").createMembershipFunction().createTrapezoidal(7000, 8000, 11000, 11000).build().build()
                .end();
    }

    static FuzzyQuantifier createRelative() {
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, 1)
                .withType(QuantifierType.relative)
                .createLabel().withLabel("almost_none").createMembershipFunction().createTrapezoidal(0, 0, 0.05, 0.2).build().build()
                .createLabel().withLabel("around 20 %").createMembershipFunction().createGaussian(0.2, 0.05).build().build()
                .createLabel().withLabel("nearly 1/3").createMembershipFunction().createTrapezoidal(0.25, 0.3, 0.36, 0.45).build().build()
                .createLabel().withLabel("approximately half").createMembershipFunction().createTrapezoidal(0.35, 0.45, 0.55, 0.65).build().build()
                .createLabel().withLabel("most").createMembershipFunction().createTrapezoidal(0.55, 0.7, 0.8, 1).build().build()
                .createLabel().withLabel("almost all").createMembershipFunction().createTrapezoidal(0.8, 0.95, 1, 1).build().build()
                .end();
    }

    // Bedroom to Room ratio
    static SummarizerQualifier createInsufficientShareOfBedrooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("insufficient share of bedrooms")
                .createMembershipFunction().createTrapezoidal(0, 1, 1.5, 2.5).build()
                .build().end();

    }

    static SummarizerQualifier createLowBedroomProportion(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("low bedroom proportion")
                .createMembershipFunction().createTriangle(1.5, 3, 4).build()
                .build().end();
    }

    static SummarizerQualifier createBalancedRoomDistribution(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("balanced room distribution")
                .createMembershipFunction().createTrapezoidal(3, 4, 5, 6).build()
                .build().end();
    }

    static SummarizerQualifier createHighBedroomProportion(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("high bedroom proportion")
                .createMembershipFunction().createGaussian(7, 1).build()
                .build().end();
    }

    static SummarizerQualifier createExcessiveShareOfBedrooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("excessive share of bedrooms")
                .createMembershipFunction().createTrapezoidal(7, 9, 10, 10).build()
                .build().end();
    }

    // median household type
    static SummarizerQualifier createStudioApartmentsDominant(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("studio apartments dominant")
                .createMembershipFunction().createTrapezoidal(0, 0, 1, 2.5).build()
                .build().end();
    }

    static SummarizerQualifier createPredominantlySingleSmallFamily(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("predominantly single small family")
                .createMembershipFunction().createTrapezoidal(1.5, 2, 3, 4).build()
                .build().end();
    }

    static SummarizerQualifier createPredominantlySingleBigFamily(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("predominantly single big family")
                .createMembershipFunction().createTrapezoidal(3, 4, 6, 8).build()
                .build().end();
    }

    static SummarizerQualifier createMultiFamilyPrevalent(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("multi family prevalent")
                .createMembershipFunction().createTrapezoidal(5, 10, 21, 21).build()
                .build().end();
    }


    // Median house age
    static SummarizerQualifier createNewHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("new")
                .createMembershipFunction().createGaussian(7.5, 2).build()
                .build().end();
    }

    static SummarizerQualifier createMiddleAgedHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("middle aged")
                .createMembershipFunction().createTrapezoidal(8, 13, 30, 45).build()
                .build().end();
    }

    static SummarizerQualifier createRecentlyBuiltHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("recently built")
                .createMembershipFunction().createTrapezoidal(0, 0, 2, 7).build()
                .build().end();
    }

    static SummarizerQualifier createAgedHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("aged")
                .createMembershipFunction().createTrapezoidal(30, 45, 60, 60).build()
                .build().end();
    }

    // Median income methods
    static SummarizerQualifier createPoorArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("poor area")
                .createMembershipFunction().createTrapezoidal(0.49, 0.49, 1.5, 3).build()
                .build().end();
    }

    static SummarizerQualifier createLowIncomeArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("low income area")
                .createMembershipFunction().createTriangle(1.5, 3, 5.5).build()
                .build().end();
    }

    static SummarizerQualifier createBelowAverageIncome(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("below average income")
                .createMembershipFunction().createTrapezoidal(3.5, 5.5, 6.5, 8).build()
                .build().end();
    }

    static SummarizerQualifier createAboveAverageIncome(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("above average income")
                .createMembershipFunction().createTrapezoidal(6.5, 8.5, 9.5, 12.5).build()
                .build().end();
    }

    static SummarizerQualifier createWealthyArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("wealthy area")
                .createMembershipFunction().createTrapezoidal(0, 10, 13, 20).build()
                .build().end();
    }

    // population
    static SummarizerQualifier createPracticallyUnpopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("practically unpopulated")
                .createMembershipFunction().createTrapezoidal(3, 3, 500, 2000).build()
                .build().end();
    }

    static SummarizerQualifier createLowPopulation(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("low population")
                .createMembershipFunction().createTrapezoidal(3, 3000, 6000, 8400).build()
                .build().end();
    }

    static SummarizerQualifier createModeratelyPopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("moderately populated")
                .createMembershipFunction().createGaussian(10000, 2000).build()
                .build().end();
    }

    static SummarizerQualifier createHighlyPopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("highly populated")
                .createMembershipFunction().createGaussian(14500, 3000).build()
                .build().end();
    }

    static SummarizerQualifier createHugePopulation(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("huge population")
                .createMembershipFunction().createTrapezoidal(15000, 18000, 23000, 26000).build()
                .build().end();
    }

    static SummarizerQualifier createManToMan(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("man to man")
                .createMembershipFunction().createTrapezoidal(22000, 27000, 30000, 30000).build()
                .build().end();
    }

    // total rooms count
    static SummarizerQualifier createFewRooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("few rooms")
                .createMembershipFunction().createTrapezoidal(2, 2, 1000, 3000).build()
                .build().end();
    }

    static SummarizerQualifier createSparseRoomDistribution(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("sparse room distribution")
                .createMembershipFunction().createTriangle(300, 4000, 7700).build()
                .build().end();
    }

    static SummarizerQualifier createModerateRoomsCount(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("moderate rooms count")
                .createMembershipFunction().createTrapezoidal(3000, 7000, 11000, 15000).build()
                .build().end();
    }

    static SummarizerQualifier createManyRooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("many rooms")
                .createMembershipFunction().createTrapezoidal(11000, 15000, 21000, 27000).build()
                .build().end();
    }

    static SummarizerQualifier createExtremelyHighRoomsCount(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("extremely high rooms count")
                .createMembershipFunction().createTrapezoidal(20000, 28000, 34000, 34000).build()
                .build().end();
    }

    // median house value
    static SummarizerQualifier createPracticallyWorthless(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("practically worthless")
                .createMembershipFunction().createTrapezoidal(14999, 14999, 40000, 100000).build()
                .build().end();
    }

    static SummarizerQualifier createLowValueHomes(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("low value homes")
                .createMembershipFunction().createTrapezoidal(40000, 110000, 150000, 180000).build()
                .build().end();
    }

    static SummarizerQualifier createModeratelyPricedHomes(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("moderately priced homes")
                .createMembershipFunction().createGaussian(200000, 25000).build()
                .build().end();
    }

    static SummarizerQualifier createAboveAverageHomeValue(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("above average home value")
                .createMembershipFunction().createTrapezoidal(210000, 250000, 270000, 320000).build()
                .build().end();
    }

    static SummarizerQualifier createHighValueResidentialAreas(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("high value residential areas")
                .createMembershipFunction().createTrapezoidal(280000, 320000, 400000, 430000).build()
                .build().end();
    }

    static SummarizerQualifier createLuxuryEstates(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("luxury estates")
                .createMembershipFunction().createTrapezoidal(390000, 450000, 500001, 500001).build()
                .build().end();
    }

    // distance LA
    static SummarizerQualifier createWithinCityBoundsLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("within city bounds(LA)")
                .createMembershipFunction().createTrapezoidal(0.42, 0.42, 30, 60).build()
                .build().end();
    }

    static SummarizerQualifier createSuburbanProximityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("suburban proximity(LA)")
                .createMembershipFunction().createTrapezoidal(40, 50, 70, 90).build()
                .build().end();
    }

    static SummarizerQualifier createDistantSuburbsLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("distant suburbs(LA)")
                .createMembershipFunction().createTriangle(70, 100, 130).build()
                .build().end();
    }

    static SummarizerQualifier createNearbyCityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("nearby city(LA)")
                .createMembershipFunction().createTrapezoidal(110, 130, 200, 250).build()
                .build().end();
    }

    static SummarizerQualifier createRuralFringeLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("rural fringe(LA)")
                .createMembershipFunction().createTrapezoidal(200, 250, 380, 600).build()
                .build().end();
    }

    static SummarizerQualifier createFarFromCityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("far from city(LA)")
                .createMembershipFunction().createTrapezoidal(350, 600, 1020, 1020).build()
                .build().end();
    }

    // distance SF
    static SummarizerQualifier createWithinCityBoundsSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("within city bounds(SF)")
                .createMembershipFunction().createTrapezoidal(8, 10, 20, 30).build()
                .build().end();
    }

    static SummarizerQualifier createSuburbanProximitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("suburban proximity(SF)")
                .createMembershipFunction().createTrapezoidal(20, 35, 50, 70).build()
                .build().end();
    }

    static SummarizerQualifier createDistantSuburbsSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("distant suburbs(SF)")
                .createMembershipFunction().createTriangle(60, 80, 100).build()
                .build().end();
    }

    static SummarizerQualifier createNearbyCitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("nearby city(SF)")
                .createMembershipFunction().createTrapezoidal(80, 100, 150, 200).build()
                .build().end();
    }

    static SummarizerQualifier createRuralFringeSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("rural fringe(SF)")
                .createMembershipFunction().createTrapezoidal(150, 200, 350, 450).build()
                .build().end();
    }

    static SummarizerQualifier createFarFromCitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("far from city(SF)")
                .createMembershipFunction().createTrapezoidal(300, 500, 1000, 1000).build()
                .build().end();
    }


    static SummarizerQualifier fullDataSet(List<BlockGroup> data) {
        FuzzySet fuzzySet = new FuzzySet(data, new SetMembership(), "all", Columns.population);
        return new SummarizerQualifier(fuzzySet);
    }

    public static void main(String[] args) throws IOException {
        String path = "dataBasePrep/prepared.csv";
        List<BlockGroup> data = CSV.readCSV(path);

        Container bedroomToRoomRatio = ContainerBuilder.builder()
                .withSummarizerQualifier(createInsufficientShareOfBedrooms(data))
                .withSummarizerQualifier(createLowBedroomProportion(data))
                .withSummarizerQualifier(createBalancedRoomDistribution(data))
                .withSummarizerQualifier(createHighBedroomProportion(data))
                .withSummarizerQualifier(createExcessiveShareOfBedrooms(data))
                .build();

        Container meanHouseholdType = ContainerBuilder.builder()
                .withSummarizerQualifier(createStudioApartmentsDominant(data))
                .withSummarizerQualifier(createPredominantlySingleSmallFamily(data))
                .withSummarizerQualifier(createPredominantlySingleBigFamily(data))
                .withSummarizerQualifier(createMultiFamilyPrevalent(data))
                .build();

        Container medianHouseAge = ContainerBuilder.builder()
                .withSummarizerQualifier(createNewHouses(data))
                .withSummarizerQualifier(createMiddleAgedHouses(data))
                .withSummarizerQualifier(createRecentlyBuiltHouses(data))
                .withSummarizerQualifier(createAgedHouses(data))
                .build();

        Container medianIncome = ContainerBuilder.builder()
                .withSummarizerQualifier(createPoorArea(data))
                .withSummarizerQualifier(createLowIncomeArea(data))
                .withSummarizerQualifier(createBelowAverageIncome(data))
                .withSummarizerQualifier(createAboveAverageIncome(data))
                .withSummarizerQualifier(createWealthyArea(data))
                .build();

        Container population = ContainerBuilder.builder()
                .withSummarizerQualifier(createPracticallyUnpopulated(data))
                .withSummarizerQualifier(createLowPopulation(data))
                .withSummarizerQualifier(createModeratelyPopulated(data))
                .withSummarizerQualifier(createHighlyPopulated(data))
                .withSummarizerQualifier(createHugePopulation(data))
                .withSummarizerQualifier(createManToMan(data))
                .build();
        Container totalRoomsCount = ContainerBuilder.builder()
                .withSummarizerQualifier(createFewRooms(data))
                .withSummarizerQualifier(createSparseRoomDistribution(data))
                .withSummarizerQualifier(createModerateRoomsCount(data))
                .withSummarizerQualifier(createManyRooms(data))
                .withSummarizerQualifier(createExtremelyHighRoomsCount(data))
                .build();

        Container medianHouseValue = ContainerBuilder.builder()
                .withSummarizerQualifier(createPracticallyWorthless(data))
                .withSummarizerQualifier(createLowValueHomes(data))
                .withSummarizerQualifier(createModeratelyPricedHomes(data))
                .withSummarizerQualifier(createAboveAverageHomeValue(data))
                .withSummarizerQualifier(createHighValueResidentialAreas(data))
                .withSummarizerQualifier(createLuxuryEstates(data))
                .build();
        Container distanceLA = ContainerBuilder.builder()
                .withSummarizerQualifier(createWithinCityBoundsLA(data))
                .withSummarizerQualifier(createSuburbanProximityLA(data))
                .withSummarizerQualifier(createDistantSuburbsLA(data))
                .withSummarizerQualifier(createNearbyCityLA(data))
                .withSummarizerQualifier(createRuralFringeLA(data))
                .withSummarizerQualifier(createFarFromCityLA(data))
                .build();
        Container distanceSF = ContainerBuilder.builder()
                .withSummarizerQualifier(createWithinCityBoundsSF(data))
                .withSummarizerQualifier(createSuburbanProximitySF(data))
                .withSummarizerQualifier(createDistantSuburbsSF(data))
                .withSummarizerQualifier(createNearbyCitySF(data))
                .withSummarizerQualifier(createRuralFringeSF(data))
                .withSummarizerQualifier(createFarFromCitySF(data))
                .build();

        LinguisticSummaryGenerator genForm1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data).withSummarizator(medianIncome.getLabel("wealthy area"))
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withSummarizatorConjunction("are in")
                .build();
        LinguisticSummaryGenerator gen2Form1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data).withSummarizator(meanHouseholdType.getLabel("predominantly single small family"))
                .withQuantifier(createAbsolute(data))
                .withSubject("houses in Block Groups")
                .withSummarizatorConjunction("are")
                .build();

        LinguisticSummaryGenerator gen3Form1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data).withSummarizator(medianHouseAge.getLabel("recently built"))
                .withQuantifier(createAbsolute(data))
                .withSubject("houses in Block Groups")
                .withSummarizatorConjunction("are")
                .build();

        LinguisticSummaryGenerator gen4Form1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data)
                .withQuantifier(createAbsolute(data))
                .withSubject("Block Groups")
                .withSummarizatorConjunction("have")
                .withSummarizator(bedroomToRoomRatio.getLabel("excessive share of bedrooms"))
                .build();

        LinguisticSummaryGenerator gen5Form1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data)
                .withQuantifier(createAbsolute(data))
                .withSubject("Block Groups")
                .withSummarizatorConjunction("have")
                .withSummarizator(population.getLabel("huge population"))
                .build();


        LinguisticSummaryGenerator gen1Form2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withSummarizator(distanceSF.getLabel("nearby city(SF)"))
                .withQualifier(medianHouseValue.getLabel("practically worthless"))
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withSummarizatorConjunction("are")
                .withQualifierConjunction("that are")
                .build();

        LinguisticSummaryGenerator gen2Form2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(population.getLabel("practically unpopulated"))
                .withSummarizatorConjunction("are")
                .withSummarizator(distanceLA.getLabel("within city bounds(LA)"))
                .build();
        LinguisticSummaryGenerator gen3Form2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("where")
                .withQualifier(meanHouseholdType.getLabel("studio apartments dominant"))
                .withSummarizatorConjunction("are")
                .withSummarizator(medianHouseValue.getLabel("practically worthless"))
                .build();

        LinguisticSummaryGenerator gen4Form2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("houses in Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(medianHouseAge.getLabel("new"))
                .withSummarizatorConjunction("have")
                .withSummarizator(bedroomToRoomRatio.getLabel("balanced room distribution"))
                .build();

        LinguisticSummaryGenerator gen5Form2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are in")
                .withQualifier(medianIncome.getLabel("poor area"))
                .withSummarizatorConjunction("have")
                .withSummarizator(bedroomToRoomRatio.getLabel("insufficient share of bedrooms"))
                .build();

        LinguisticSummaryGenerator genForm1complex = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data)
                .withQuantifier(createAbsolute(data))
                .withSubject("Block Groups")
                .withSummarizatorConjunction("are in")
                .withSummarizator(medianIncome.getLabel("wealthy area").and(distanceLA.getLabel("far from city(LA)")))
                .build();

        LinguisticSummaryGenerator genForm2complex = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are in")
                .withQualifier(medianIncome.getLabel("poor area").and(bedroomToRoomRatio.getLabel("insufficient share of bedrooms")))
                .withSummarizatorConjunction("are")
                .withSummarizator(medianHouseValue.getLabel("practically worthless"))
                .build();

        LinguisticSummaryGenerator gen2Form2complex = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(population.getLabel("practically unpopulated"))
                .withSummarizatorConjunction("are")
                .withSummarizator(distanceLA.getLabel("within city bounds(LA)").and(medianHouseValue.getLabel("luxury estates")))
                .build();


        LinguisticSummaryGenerator gen4Form2complex = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("houses in Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(medianHouseAge.getLabel("new").and(meanHouseholdType.getLabel("multi family prevalent")))
                .withSummarizatorConjunction("have")
                .withSummarizator(bedroomToRoomRatio.getLabel("balanced room distribution").and(medianHouseValue.getLabel("moderately priced homes")))
                .build();


        LinguisticSummaryGenerator genForm2_1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(medianHouseAge.getLabel("aged").and(medianHouseValue.getLabel("practically worthless")))
                .withSummarizatorConjunction("have")
                .withSummarizator(meanHouseholdType.getLabel("predominantly single big family"))
                .build();

        LinguisticSummaryGenerator genForm2_2 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that have")
                .withQualifier(meanHouseholdType.getLabel("predominantly single big family").and(medianHouseValue.getLabel("practically worthless")))
                .withSummarizatorConjunction("are")
                .withSummarizator(medianHouseAge.getLabel("aged"))
                .build();

        LinguisticSummaryGenerator genForm2_3 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.Second)
                .withQuantifier(createRelative())
                .withSubject("Block Groups")
                .withQualifierConjunction("that are")
                .withQualifier(medianHouseValue.getLabel("practically worthless"))
                .withSummarizatorConjunction("are")
                .withSummarizator(medianHouseAge.getLabel("aged").and(meanHouseholdType.getLabel("predominantly single big family")))
                .build();


        TwoSubjectSummaryFirst twoSubjectSummaryFirst = new TwoSubjectSummaryFirst(Subject.SUB_HOUR_OCEAN, Subject.INLAND, createRelative(), medianHouseAge.getLabel("aged").and(meanHouseholdType.getLabel("predominantly single big family")), data, "are");
        List<Pair<String, Double>> summaries = twoSubjectSummaryFirst.generateSummaries();
        System.out.println(summaries.toString());

//        TwoSubjectSummaryForth tester2 = new TwoSubjectSummaryForth(Subject.INLAND,Subject.NEAR_BAY,population.getLabel("practically unpopulated").and(medianHouseValue.getLabel("practically worthless")),data);
//        System.out.println(tester2.generateSummaries().toString());
//        TwoSubjectSummarySecond tester3 = new TwoSubjectSummarySecond(Subject.NEAR_BAY,Subject.NEAR_OCEAN,createRelative(),population.getLabel("practically unpopulated"),medianHouseValue.getLabel("practically worthless"),data);
//        System.out.println(tester3.generateSummaries().toString());
//        TwoSubjectSummaryThird tester4 = new TwoSubjectSummaryThird(Subject.NEAR_BAY,Subject.NEAR_OCEAN,createRelative(),population.getLabel("practically unpopulated"),medianHouseValue.getLabel("practically worthless"),data);
//        System.out.println(tester4.generateSummaries().toString());

        CSV.save_pairs("outputs/MultiSubjects/Form1/P1-P2__S1_S2.csv", summaries);

    }
}
