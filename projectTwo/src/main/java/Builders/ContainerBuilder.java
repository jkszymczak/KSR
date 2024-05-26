package Builders;

import FuzzyCalculations.Container;
import FuzzyCalculations.SummarizerQualifier;

import java.util.LinkedList;
import java.util.List;

public class ContainerBuilder implements Builder<Container,Container> {
    private List<SummarizerQualifier> labels =null;

    public ContainerBuilder withSummarizerQualifier(SummarizerQualifier summarizerQualifier){
        if(this.labels == null) this.labels=new LinkedList<>();
        this.labels.add(summarizerQualifier);
        return this;
    }

    public static ContainerBuilder builder(){
        return new ContainerBuilder();
    }

    @Override
    public Container build() {
        return end();
    }

    @Override
    public Container end() {
        return new Container(this.labels);
    }
}
