package com.owned.examples.patterns.structural.decorator;

public class Demo {

    public static void main(String[] args) {
        Beverage beverage = new CompressedTeaDecorator(new TeaBagsDecorator(new SimpleTea()));
        System.out.println(beverage.description());
    }
}

interface Beverage {

    double cost();
    String description();
}

class SimpleTea implements Beverage {

    @Override
    public double cost() {
        return 0;
    }

    @Override
    public String description() {
        return "Aromatic beverage commonly prepared by pouring hot or boiling water over\ncured leaves of the Camellia sinensis, an evergreen shrub (bush) native to East Asia.\n";
    }
}

/**
 * Adds functionality to a class (from outside) without modifying it
 * Is in both has-a ans is-a with the component (ie Beverage)
 */
abstract class PackagingTeaDecorator implements Beverage {

    private final Beverage beverage;

    PackagingTeaDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost();
    }

    @Override
    public String description() {
        return beverage.description();
    }
}

/**
 * Decorator Subclasses will usually add "before" and "after" functionality to some or all of the methods that they delegate to the real target
 */
class TeaBagsDecorator extends PackagingTeaDecorator {

    TeaBagsDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double cost() {
        return super.cost() + 0.1;
    }

    @Override
    public String description() {
        return super.description() + "One way to consume it is leaving the tea in the bag.";
    }
}

class CompressedTeaDecorator extends PackagingTeaDecorator {


    CompressedTeaDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double cost() {
        return super.cost();
    }

    @Override
    public String description() {
        return super.description() +  "It's Produced for convenience in transport, storage, and ageing.\n";
    }
}


