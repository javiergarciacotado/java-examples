package com.owned.examples.patterns.behavioral.visitor;

import java.util.Arrays;
import java.util.List;


public class Demo {

    public static void main(String[] args) {

        /**
         * Represents an operation to be performed on the elements of an object structure.
         */
        Visitor visitor = new Visitor() {
            @Override
            void visit(Visitable1 n1) {
                super.visit(n1);
            }

            @Override
            void visit(Visitable2 n2) {
                super.visit(n2);
            }
        };
        /**
         * This structure is not necessarily to be a collection. In can be a complex structure, such as a composite object.
         */
        List<Visitable> collection = Arrays.asList(new Visitable1(), new Visitable2());
        for (Visitable visitable : collection) {
            /**
             * avoiding if-else-if-else to perform an operation
             */
            visitable.accept(visitor);
        }
    }
}

interface Visitable {

    void accept(Visitor visitor);
}

class Visitable1 implements Visitable {


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Visitable2 implements Visitable {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

/**
 * It can be an abstract class or an interface. Visitors need to know how to visit each.
 */
abstract class Visitor {

    void visit(Visitable1 n1) {
        //perform some operation in node1
        System.out.println("Perform some operation on node1");
    }
    void visit(Visitable2 n2) {
        //perform some operation in node2
        System.out.println("Perform some operation on node2");
    }

}

