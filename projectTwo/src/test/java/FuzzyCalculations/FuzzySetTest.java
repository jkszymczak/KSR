package FuzzyCalculations;

import Builders.FuzzySetBuilder;
import Database.BlockGroup;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FuzzySetTest extends TestCase {

    public void testSet(){
//        MembershipFunction func1 = new Triangle(0,10,20);
////        MembershipFunction func2 = new Trapezoidal(10,20,30,40);
//        Map<String,Object> m1 = new HashMap<>();
//        Map<String,Object> m2 = new HashMap<>();
//        m1.put("index",1);
//        m1.put("population",10.0);
//        m2.put("index",2);
//        m2.put("population",20.0);
//        BlockGroup b1 = new BlockGroup(m1);
//        BlockGroup b2 = new BlockGroup(m2);
//        List<BlockGroup> blockGroups = new LinkedList<>();
//        blockGroups.add(b1);
//        blockGroups.add(b2);
//        FuzzySet fuzzySet = new FuzzySet(blockGroups,func1,"Mało",Columns.population);
//        assertEquals(1,fuzzySet.getElements().size());
//        System.out.println(fuzzySet.getElements().toString());
    }
    public void testAndInSet(){
//        Map<String,Object> m1 = new HashMap<>();
//        Map<String,Object> m2 = new HashMap<>();
//        m1.put("index",1);
//        m1.put("population",10.0);
//        m1.put("households",9.0);
//
//        m2.put("index",2);
//        m2.put("population",15.0);
//        m2.put("households",15.0);
//
//        Map<String,Object> m3 = new HashMap<>();
//        Map<String,Object> m4 = new HashMap<>();
//        m3.put("index",3);
//        m3.put("population",10.0);
//        m3.put("households",15.0);
//
//        m4.put("index",4);
//        m4.put("population",20.0);
//        m4.put("households",20.0);
//
//        BlockGroup b1 = new BlockGroup(m1);
//        BlockGroup b2 = new BlockGroup(m2);
//        BlockGroup b3 = new BlockGroup(m3);
//        BlockGroup b4 = new BlockGroup(m4);
//        List<BlockGroup> blockGroups = new LinkedList<>();
//        blockGroups.add(b1);
//        blockGroups.add(b2);
//        blockGroups.add(b3);
//        blockGroups.add(b4);
//
//        // setting first set
//        MembershipFunction func1 = new Triangle(0,10,20);
//        FuzzySet fuzzySet = new FuzzySet(blockGroups,func1,"Mało",Columns.population);
////        System.out.println("Size of set: "+fuzzySet.getElements().size());
////        System.out.println(fuzzySet.toString());
//        // setting second set
//        MembershipFunction func2 = new Trapezoidal(10,20,30,40);
//        FuzzySet fuzzySet2 = new FuzzySet(blockGroups,func2,"Dużę",Columns.households);
//        FuzzySet joined = fuzzySet.and(fuzzySet2);
//        assertEquals(2,joined.getElements().size());
//        assertEquals(fuzzySet2.and(fuzzySet).getElements().keySet(),joined.getElements().keySet());
    }

    public void testBuilder(){
//        Map<String,Object> m1 = new HashMap<>();
//        Map<String,Object> m2 = new HashMap<>();
//        m1.put("index",1);
//        m1.put("population",10.0);
//        m1.put("households",9.0);
//
//        m2.put("index",2);
//        m2.put("population",15.0);
//        m2.put("households",15.0);
//
//        Map<String,Object> m3 = new HashMap<>();
//        Map<String,Object> m4 = new HashMap<>();
//        m3.put("index",3);
//        m3.put("population",10.0);
//        m3.put("households",15.0);
//
//        m4.put("index",4);
//        m4.put("population",20.0);
//        m4.put("households",20.0);
//
//        BlockGroup b1 = new BlockGroup(m1);
//        BlockGroup b2 = new BlockGroup(m2);
//        BlockGroup b3 = new BlockGroup(m3);
//        BlockGroup b4 = new BlockGroup(m4);
//        List<BlockGroup> blockGroups = new LinkedList<>();
//        blockGroups.add(b1);
//        blockGroups.add(b2);
//        blockGroups.add(b3);
//        blockGroups.add(b4);
//
//        FuzzySet test =  FuzzySetBuilder.builder().onColumn(Columns.population).withLabel("Works")
//                .withCandidates(blockGroups).createMembershipFunction()
//                .createTriangle(0,10,20).build().end();
//        System.out.println(test);

    }

}