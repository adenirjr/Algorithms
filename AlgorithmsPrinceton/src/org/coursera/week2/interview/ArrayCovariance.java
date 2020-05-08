package org.coursera.week2.interview;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Java generics. Explain why Java prohibits generic array creation.
 *
 * "
 * Arrays are covariant, i.e., an array of a supertype (Super[]) is also a supertype of an array of a subtype (Sub[]).
 * ...
 * Arrays are also called reified (probably the best synonym: “concretized”).
 * They always have a “concrete”, known type during runtime.
 * ...
 * Generics are invariant, i.e., a generic collection of a supertype (List<Super>) is neither a supertype
 * nor a subtype of a collection of a subtype (<Sub>). Or regarding the code example above:
 * [Array]List<Number> is neither a supertype nor a subtype of [Array]List<BigDecimal>.
 * They simply don’t have any hierarchy connection between each other.
 * ...
 * Generics are also called non-reified (“non-concretized”).
 * They don’t have a “concrete”, known type during runtime, for the reason given in the following section.
 * "
 *
 * @Author https://www.simplexacode.ch/en/blog/2018/08/the-problem-with-creating-generic-arrays/
 */
public class ArrayCovariance {

    public static void main(String... args) {
        BigDecimal bigDecimal = new BigDecimal(10);
        Number number = bigDecimal;
        bigDecimal = (BigDecimal) number;
        BigInteger bigInteger = (BigInteger) number;  /* Fails at runtime. */


        BigDecimal[] bigDecimalArray = new BigDecimal[10];
        Number[] numberArray = bigDecimalArray;
        bigDecimalArray = (BigDecimal[]) numberArray;
        BigInteger[] bigIntegerArray = (BigInteger[]) numberArray;  /* Fails at runtime. */


        List<Number> numberList = new ArrayList<>();
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        //numberList = bigDecimalList;  /* Fails at compile time. */
    }
}
