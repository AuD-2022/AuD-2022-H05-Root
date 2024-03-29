package h05;

import h05.h1_1.RationalTests;
import h05.h3_1.LiteralExpressionNodeTests;
import h05.h3_2.IdentifierExpressionNodeTests;
import h05.h3_3.OperationExpressionNodeTests;
import h05.h4_1.ExpressionTreeHandlerTests;
import h05.provider.IdentifierExpressionNodeProvider;
import h05.transformer.AccessTransformer;
import h05.tree.ListItem;
import h05.tree.Operator;
import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                ),
                makeCriterionFromChildCriteria("H3 | Arithmetischer Vielwegbaum",
                    makeCriterionFromChildCriteria("H3.1 | Literale",
                        makeCriterion("Die Methoden [[[evaluate(Map)]]] und [[[clone()]]] in Klasse "
                                + "LiteralExpressionNode funktionieren wie beschrieben.",
                            () -> LiteralExpressionNodeTests.class.getDeclaredMethod("testEvaluate", BigDecimal.class),
                            () -> LiteralExpressionNodeTests.class.getDeclaredMethod("testClone", BigDecimal.class)
                        )
                    ),
                    makeCriterionFromChildCriteria("H3.2 | Identifier",
                        makeCriterion("Der Konstruktor von IdentifierExpressionNode funktioniert wie beschrieben "
                                + "(inklusive Exceptionwürfe).",
                            () -> IdentifierExpressionNodeTests.class.getDeclaredMethod("testConstructor", String.class),
                            () -> IdentifierExpressionNodeTests.class.getDeclaredMethod("testConstructorExceptions",
                                IdentifierExpressionNodeProvider.ConstructorInvalid.IdentifierType.class,
                                String.class)
                        ),
                        makeCriterion("Methode [[[evaluate(Map)]]] funktioniert wie beschrieben "
                                + "(inklusive Exceptionwürfe).",
                            () -> IdentifierExpressionNodeTests.class.getDeclaredMethod("testEvaluate", String.class, Map.class),
                            () -> IdentifierExpressionNodeTests.class.getDeclaredMethod("testEvaluateExceptions",
                                IdentifierExpressionNodeProvider.EvaluateInvalid.IdentifierType.class,
                                String.class,
                                Map.class)
                        ),
                        makeCriterion("Methode [[[clone()]]] funktioniert wie beschrieben.",
                            () -> IdentifierExpressionNodeTests.class.getDeclaredMethod("testClone", String.class)
                        )
                    ),
                    makeCriterionFromChildCriteria("H3.3 | Operationsknoten",
                        makeCriterion("Der Konstruktor von OperationExpressionNode funktioniert wie beschrieben "
                            + "(inklusive Exceptionwürfe)",
                            () -> OperationExpressionNodeTests.class
                                .getDeclaredMethod("testConstructor", Operator.class, ListItem.class),
                            () -> OperationExpressionNodeTests.class
                                .getDeclaredMethod("testConstructorException", Operator.class, ListItem.class)
                        ),
                        Criterion.builder()
                            .shortDescription("Methode [[[evaluate(Map)]]] wertet Ausdrücke korrekt rekursiv aus.")
                            .build(),
                        makeCriterion("Methode [[[evaluate(Map)]]] gibt bei Aufrufen mit den Operatoren '+' bzw. '*' "
                                + " und keinen Operanden das neutrale Element zurück.",
                            () -> OperationExpressionNodeTests.class
                                .getDeclaredMethod("testEvaluateNeutralElement", Operator.class)
                        ),
                        makeCriterion("Methode [[[clone()]]] funktioniert wie beschrieben.",
                            () -> OperationExpressionNodeTests.class
                                .getDeclaredMethod("testClone", Operator.class, ListItem.class)
                        )
                    )
                ),
                makeCriterionFromChildCriteria("H4 | (Re-)Konstruktion eines arithmetischen Ausdrucks",
                    makeCriterionFromChildCriteria("H4.1 | Rekursiver Aufbau eines arithmetischen Vielwegbaums",
                        makeCriterion("Methode [[[buildRecursively(Iterator)]]] funktioniert mit einfachen Ausdrücken wie " +
                                "beschrieben.",
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildRecursivelySimple", List.class)
                        ),
                        makeCriterion("Methode [[[buildRecursively(Iterator)]]] funktioniert mit verschachtelten Ausdrücken" +
                                "Ausdrücken wie beschrieben.", 0, 2,
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildRecursivelyComplex", List.class)
                        ),
                        makeCriterion("Methode [[[buildRecursively(Iterator)]]] wirft die richtigen Exceptions.",
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildRecursivelyExceptions", Class.class, List.class)
                        )
                    ),
                    makeCriterionFromChildCriteria("H4.2 | Iterativer Aufbau eines arithmetischen Vielwegbaums",
                        makeCriterion("Methode [[[buildIteratively(Iterator)]]] funktioniert mit einfachen Ausdrücken wie "
                                + "beschrieben.",
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildIterativelySimple", List.class)
                        ),
                        makeCriterion("Methode [[[buildIteratively(Iterator)]]] funktioniert mit verschachtelten "
                                + "Ausdrücken wie beschrieben.", 0, 2,
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildIterativelyComplex", List.class)
                        ),
                        makeCriterion("Methode [[[buildIteratively(Iterator)]]] wirft die richtigen Exceptions.",
                            () -> ExpressionTreeHandlerTests.class
                                .getDeclaredMethod("testBuildIterativelyExceptions", Class.class, List.class)
                        )
                    ),
                    makeCriterionFromChildCriteria("H4.3 | Rekonstruktion eines arithmetischen Ausdrucks",
                        Criterion.builder()
                            .shortDescription("Methode [[[reconstruct(ArithmeticExpressionNode)]]] gibt die korrekten Tokens "
                                + "für eine simple Eingabe zurück.")
                            .build(),
                        Criterion.builder()
                            .shortDescription("Methode [[[reconstruct(ArithmeticExpressionNode)]]] gibt die korrekten Tokens "
                                + "für eine komplexere Eingabe zurück.")
                            .build()
                    )
                ),
                makeCriterionFromChildCriteria("H5 | Schrittweise Auswertung eines Ausdrucks",
                    Criterion.builder()
                        .shortDescription("Methode [[[nextStep()]]] ersetzt unbekannte Identifier durch den im Blatt "
                            + "vorgegebenen String.")
                        .build(),
                    Criterion.builder()
                        .shortDescription("Wiederholte Aufrufe der Methode [[[nextStep()]]] gibt korrekte Werte für eine "
                            + "simple Eingabe zurück.")
                        .build(),
                    Criterion.builder()
                        .shortDescription("Wiederholte Aufrufe der Methode [[[nextStep()]]] gibt korrekte Werte für eine "
                            + "komplexere Eingabe zurück.")
                        .build()
                )
            )
            .build();
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new AccessTransformer());
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
