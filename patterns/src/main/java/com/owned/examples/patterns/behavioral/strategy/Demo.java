package com.owned.examples.patterns.behavioral.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.owned.examples.patterns.behavioral.strategy.Color.GREEN;
import static com.owned.examples.patterns.behavioral.strategy.Color.RED;

public class Demo {

    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Apple> apples = Arrays.asList(new Apple(RED, 10), new Apple(GREEN, 20));
        /**
         * First approach
         */
        final List<Apple> apples1 = demo.filterGreenApples(apples);
        /**
         * Second approach
         */
        final List<Apple> apples2 = demo.filterApplesBy(apples, GREEN);
        /**
         * Third approach
         */
        final List<Apple> apples3 = demo.filterApplesBy(apples, 15);
        /**
         * Fourth approach
         */
        final List<Apple> apples4 = demo.filterApplesBy(apples, new AppleGreenPredicate());
        //OR
        final List<Apple> apples5 = demo.filterApplesBy(apples, apple -> GREEN.equals(apple.getColor()) );
    }


    private List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> greenApples = new ArrayList<>(); //accumulator
        for (Apple apple : apples) {
            if (GREEN.equals(apple.getColor())) {    //filtering --> how about we want to handle RED apples? Should we copy - paste changing the filtering?
                greenApples.add(apple);
            }
        }
        return greenApples;
    }

    /**
     * We start violating the 'DRY' principle
     */

    private List<Apple> filterApplesBy(List<Apple> apples, Color color) {
        List<Apple> greenApples = new ArrayList<>(apples.size()); //accumulator
        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {    //filtering --> how about we want to handle RED apples? Should we copy - paste changing the filtering?
                greenApples.add(apple);
            }
        }
        return greenApples;
    }

    private List<Apple> filterApplesBy(List<Apple> apples, int weight) {
        List<Apple> heavyApples = new ArrayList<>(apples.size()); //accumulator
        for (Apple apple : apples) {
            if (weight < apple.getWeight()) {    //filtering --> how about we want to handle RED apples? Should we copy - paste changing the filtering?
                heavyApples.add(apple);
            }
        }
        return heavyApples;
    }

    /**
     * Could be improved abstracting over list type
     */
    private List<Apple> filterApplesBy(List<Apple> apples, ApplePredicate applePredicate) {
        List<Apple> applesAccumulator = new ArrayList<>(apples.size()); //accumulator
        for (Apple apple : apples) {
            if (applePredicate.test(apple)) {    //filtering --> how about we want to handle RED apples? Should we copy - paste changing the filtering?
                applesAccumulator.add(apple);
            }
        }
        return applesAccumulator;
    }
}

enum Color { RED, GREEN }

class Apple {

    private final Color color;
    private final int weight;

    Apple(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    Color getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }
}

/**
 * Family of algorithms
 */
@FunctionalInterface
interface ApplePredicate extends Predicate<Apple> {

    @Override
    boolean test(Apple apple);
}

/**
 * Encapsulate green predicate algorithm to apple predicates
 */
class AppleGreenPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}

/**
 * Encapsulate heavy predicate algorithm to apple predicates
 */
class AppleHeavyPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
        return 15 < apple.getWeight();
    }
}
