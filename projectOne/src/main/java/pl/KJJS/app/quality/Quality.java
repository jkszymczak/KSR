package pl.KJJS.app.quality;

import pl.KJJS.app.features.Countries;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quality {
    public Double calculateAccuracy(List<Label> articles){
        int accumulator = 0;
        for(Label a:articles){
            if(a.getExpected()==a.getResult()) accumulator++;
        }
        return (double) (accumulator/articles.size());
    }
    public Double calculatePrecision(List<Label> articles, Countries c){
        Double all = 0.0;
        Double pk =0.0;
        for(Label a:articles){
            if(a.getResult()==c) {
                all++;
                if(a.getResult()==a.getExpected()) pk++;
            }
        }
        return pk/all;
    }
    public Double calculateRecall(List<Label> articles,Countries c){
        Double all = 0.0;
        Double pk =0.0;
        for(Label a:articles){
            if(a.getExpected()==c) {
                all++;
                if(a.getExpected()==a.getResult()) pk++;
            }
        }
        return pk/all;
    }
    public Double calculateF(List<Label>articles,Countries c){
        Double acc = this.calculateAccuracy(articles);
        Double rec = this.calculateRecall(articles,c);
      return 2*(acc*rec)/(acc+rec);
    }


}
