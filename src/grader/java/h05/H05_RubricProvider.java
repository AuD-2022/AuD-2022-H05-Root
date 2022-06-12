package h05;

import h05.h1_1.RationalTests;
import h05.h1_2.MyIntegerTests;
import h05.h1_2.MyRationalTests;
import h05.h1_2.MyRealTests;
import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Callable;

@RubricForSubmission("h05")
public class H05_RubricProvider implements RubricProvider {
    @Override
    public Rubric getRubric() {
        return Rubric.builder()
            .title("H05")
            .addChildCriteria(
                makeCriterionFromChildCriteria("H1 | Racket-Zahlen (stark vereinfacht)",
                    makeCriterionFromChildCriteria("H1.1 | Rationale Zahlen",
                        makeCriterion("Der Konstruktor von [[[Rational]]] funktioniert mit positiven Brüchen.",
                            () -> RationalTests.class
                                .getDeclaredMethod("testConstructorPositive", BigInteger.class, BigInteger.class)),
                        makeCriterion("Der Konstruktor von [[[Rational]]] funktioniert mit negativen Brüchen.",
                            () -> RationalTests.class
                                .getDeclaredMethod("testConstructorNegative", BigInteger.class, BigInteger.class))
                    ),
                    makeCriterionFromChildCriteria("H1.2 | Konvertierung einer Zahlenmenge in eine andere Zahlenmenge.",
                        Criterion.builder()
                            .shortDescription("Methode [[[toRational()]]] in den Klassen [[[MyInteger]]], [[[MyRational]]] und"
                                + " [[[MyReal]]] gibt korrekte Werte zurück.")
                            .grader(Grader.testAwareBuilder()
                                .requirePass(JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> MyIntegerTests.class
                                        .getDeclaredMethod("testToRational", BigDecimal.class)),
                                    JUnitTestRef.ofMethod(() -> MyRationalTests.class
                                        .getDeclaredMethod("testToRational", BigDecimal.class)),
                                    JUnitTestRef.ofMethod(() -> MyRealTests.class
                                        .getDeclaredMethod("testToRational", BigDecimal.class))
                                ))
                                .pointsFailedMin()
                                .pointsPassedMax()
                                .build())
                            .build(),
                        Criterion.builder()
                            .shortDescription("Methode [[[toReal()]]] in den Klassen [[[MyInteger]]], [[[MyRational]]] und"
                                + " [[[MyReal]]] gibt korrekte Werte zurück.")
                            .grader(Grader.testAwareBuilder()
                                .requirePass(JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> MyIntegerTests.class
                                        .getDeclaredMethod("testToReal", BigDecimal.class)),
                                    JUnitTestRef.ofMethod(() -> MyRationalTests.class
                                        .getDeclaredMethod("testToReal", BigDecimal.class)),
                                    JUnitTestRef.ofMethod(() -> MyRealTests.class
                                        .getDeclaredMethod("testToReal", BigDecimal.class))
                                ))
                                .pointsFailedMin()
                                .pointsPassedMax()
                                .build())
                            .build()
                    )
                )
            )
            .build();
    }

    private static Criterion makeCriterion(String shortDescription, Callable<Method> callable) {
        return makeCriterion(shortDescription, callable, 0, 1);
    }

    private static Criterion makeCriterion(String shortDescription, Callable<Method> callable, int minPoints, int maxPoints) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .minPoints(minPoints)
            .maxPoints(maxPoints)
            .grader(Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(callable))
                .pointsFailedMin()
                .pointsPassedMax()
                .build())
            .build();
    }

    private static Criterion makeCriterionFromChildCriteria(String shortDescription, Criterion... criteria) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .addChildCriteria(criteria)
            .build();
    }
}
