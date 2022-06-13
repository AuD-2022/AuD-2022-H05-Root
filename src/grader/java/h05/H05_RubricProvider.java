package h05;

import h05.h1_1.RationalTests;
import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
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
                        makeCriterion("Methode [[[toRational()]]] in den Klassen [[[MyInteger]]], [[[MyRational]]] und"
                                + " [[[MyReal]]] gibt korrekte Werte zurück.",
                            () -> h05.h1_2.MyIntegerTests.class
                                .getDeclaredMethod("testToRational", BigDecimal.class),
                            () -> h05.h1_2.MyRationalTests.class
                                .getDeclaredMethod("testToRational", BigDecimal.class),
                            () -> h05.h1_2.MyRealTests.class
                                .getDeclaredMethod("testToRational", BigDecimal.class)
                        ),
                        makeCriterion("Methode [[[toReal()]]] in den Klassen [[[MyInteger]]], [[[MyRational]]] und"
                                + " [[[MyReal]]] gibt korrekte Werte zurück.",
                            () -> h05.h1_2.MyIntegerTests.class
                                .getDeclaredMethod("testToReal", BigDecimal.class),
                            () -> h05.h1_2.MyRationalTests.class
                                .getDeclaredMethod("testToReal", BigDecimal.class),
                            () -> h05.h1_2.MyRealTests.class
                                .getDeclaredMethod("testToReal", BigDecimal.class)
                        )
                    )
                ),
                makeCriterionFromChildCriteria("H2 | Arithmetische Operationen",
                    makeCriterionFromChildCriteria("H2.1 | Arithmetische Grundoperationen",
                        Criterion.builder()
                            .shortDescription("Die Methoden [[[plus()]]], [[[minus()]]], [[[times()]]] und [[[divide()]]]"
                                + " funktionieren wie beschrieben.")
                            .build(),
                        Criterion.builder()
                            .shortDescription("Die Methoden [[[plus(MyNumber)]]], [[[minus(MyNumber)]]], [[[times(MyNumber)]]]"
                                + " und [[[divide(MyNumber)]]] funktionieren wie beschrieben.")
                            .build()
                    ),
                    makeCriterionFromChildCriteria("H2.2 | Wurzel, Exponenzieren und Logarithmus",
                        makeCriterion("Methode [[[sqrt()]]] funktioniert in allen drei Klassen wie beschrieben.",
                            () -> h05.h2_2.MyIntegerTests.class.getDeclaredMethod("testSqrt", BigDecimal.class),
                            () -> h05.h2_2.MyRationalTests.class.getDeclaredMethod("testSqrt", BigDecimal.class),
                            () -> h05.h2_2.MyRealTests.class.getDeclaredMethod("testSqrt", BigDecimal.class)
                        ),
                        makeCriterion("Methode [[[exp()]]] funktioniert in allen drei Klassen wie beschrieben.",
                            () -> h05.h2_2.MyIntegerTests.class.getDeclaredMethod("testExp", BigDecimal.class),
                            () -> h05.h2_2.MyRationalTests.class.getDeclaredMethod("testExp", BigDecimal.class),
                            () -> h05.h2_2.MyRealTests.class.getDeclaredMethod("testExp", BigDecimal.class)
                        ),
                        makeCriterion("Methode [[[expt(MyNumber)]]] funktioniert in allen drei Klassen wie beschrieben.",
                            () -> h05.h2_2.MyIntegerTests.class.getDeclaredMethod("testExpt", BigDecimal.class, BigDecimal.class),
                            () -> h05.h2_2.MyRationalTests.class.getDeclaredMethod("testExpt", BigDecimal.class, BigDecimal.class),
                            () -> h05.h2_2.MyRealTests.class.getDeclaredMethod("testExpt", BigDecimal.class, BigDecimal.class)
                        ),
                        makeCriterion("Methode [[[ln()]]] funktioniert in allen drei Klassen wie beschrieben.",
                            () -> h05.h2_2.MyIntegerTests.class.getDeclaredMethod("testLn", BigDecimal.class),
                            () -> h05.h2_2.MyRationalTests.class.getDeclaredMethod("testLn", BigDecimal.class),
                            () -> h05.h2_2.MyRealTests.class.getDeclaredMethod("testLn", BigDecimal.class)
                        ),
                        makeCriterion("Methode [[[log(MyNumber)]]] funktioniert in allen drei Klassen wie beschrieben.",
                            () -> h05.h2_2.MyIntegerTests.class.getDeclaredMethod("testLog", BigDecimal.class, BigDecimal.class),
                            () -> h05.h2_2.MyRationalTests.class.getDeclaredMethod("testLog", BigDecimal.class, BigDecimal.class),
                            () -> h05.h2_2.MyRealTests.class.getDeclaredMethod("testLog", BigDecimal.class, BigDecimal.class)
                        )
                    )
                )
            )
            .build();
    }

    @SafeVarargs
    private static Criterion makeCriterion(String shortDescription, Callable<Method>... callables) {
        return makeCriterion(shortDescription, 0, 1, callables);
    }

    @SafeVarargs
    private static Criterion makeCriterion(String shortDescription, int minPoints, int maxPoints, Callable<Method>... callables) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .minPoints(minPoints)
            .maxPoints(maxPoints)
            .grader(Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.and(Arrays.stream(callables).map(JUnitTestRef::ofMethod).toArray(JUnitTestRef[]::new)))
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
