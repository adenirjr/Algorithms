package org.coursera.week2.interview;

public class StackMaxTest {

    private static void assertStackLIFO() {
        final StackMax stack = new StackMax();

        stack.push(10);
        stack.push(5);
        stack.push(3);

        int item = stack.pop();
        assert item == 3;

        item = stack.pop();
        assert item == 5;

        item = stack.pop();
        assert item == 10;
    }

    private static void assertStackReturnMax() {
        final StackMax stack = new StackMax();


        stack.push(1);
        stack.push(10);
        stack.push(5);
        stack.push(15);
        stack.push(3);
        stack.push(50);


        int item = stack.getMax();
        assert item == 50;

        stack.pop();

        item = stack.getMax();
        assert item == 15;

        stack.pop();
        stack.pop();

        item = stack.getMax();
        assert item == 10;

        stack.pop();
        stack.pop();

        item = stack.getMax();
        assert item == 1;
    }

    public static void main(String... args) {
        assertStackLIFO();
        assertStackReturnMax();
    }
}
