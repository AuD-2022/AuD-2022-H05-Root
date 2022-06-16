package h05.provider;

import h05.exception.*;
import h05.tree.Operator;
import kotlin.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TokenProvider {

    private static final long SEED = 0L;
    private static final int STREAM_SIZE = 5;

    public static class SimpleInteger implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateSimpleTokenList(
                    random.nextBoolean() ? Operator.ADD : Operator.SUB,
                    random.nextInt(10) + 1,
                    () -> Integer.toString(random.nextInt(10000))
                ))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class SimpleRational implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateSimpleTokenList(
                    random.nextBoolean() ? Operator.ADD : Operator.SUB,
                    random.nextInt(10) + 1,
                    () -> {
                        Pair<Integer, Integer> fraction = simplifyFraction(random.nextInt(10000), random.nextInt(10000));

                        return "%d/%d".formatted(fraction.getFirst(), fraction.getSecond());
                    }
                ))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class SimpleReal implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateSimpleTokenList(
                    random.nextBoolean() ? Operator.ADD : Operator.SUB,
                    random.nextInt(10) + 1,
                    () -> "%d.%d".formatted(random.nextInt(10000), random.nextInt(10000)).replaceAll("0*$", "")
                ))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class ComplexInteger implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateComplexTokenList(random, () -> Integer.toString(random.nextInt(10000))))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class ComplexRational implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateComplexTokenList(random,
                    () -> {
                        Pair<Integer, Integer> fraction = simplifyFraction(random.nextInt(10000), random.nextInt(10000));

                        return "%d/%d".formatted(fraction.getFirst(), fraction.getSecond());
                    }))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class ComplexReal implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> generateComplexTokenList(random,
                    () -> "%d.%d".formatted(random.nextInt(10000), random.nextInt(10000)).replaceAll("0*$", "")))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class Invalid implements ArgumentsProvider {

        private static final Function<String, List<String>> TOKENIZER_FUNCTION = s -> Arrays.asList(s.split(" "));

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                new Object[] {BadOperationException.class, Collections.emptyList()},
                new Object[] {ParenthesesMismatchException.class, TOKENIZER_FUNCTION.apply("+ 1 2 )")},
                new Object[] {UndefinedOperatorException.class, TOKENIZER_FUNCTION.apply("( ? 1 2 )")},
                new Object[] {WrongNumberOfOperandsException.class, TOKENIZER_FUNCTION.apply("( log 1 )")},
                new Object[] {IllegalIdentifierExceptions.class, TOKENIZER_FUNCTION.apply("( + ä ö ü )")}
            )
                .map(Arguments::of);
        }
    }

    private static List<String> generateSimpleTokenList(Operator operator,
                                                        int numberOfOperands,
                                                        Supplier<String> operandSupplier) {
        List<String> tokens = new ArrayList<>();

        tokens.add("(");
        tokens.add(operator.getSymbol());
        for (int i = 0; i < numberOfOperands; i++) {
            tokens.add(operandSupplier.get());
        }
        tokens.add(")");

        return tokens;
    }

    private static List<String> generateComplexTokenList(Random random, Supplier<String> operandSupplier) {
        Operator[] operators = new Operator[] {Operator.ADD, Operator.SUB, Operator.MUL, Operator.DIV};
        int numberOfOperations = random.nextInt(5) + 1;
        int numberOfOperands = random.nextInt(25) + 1;
        int openParentheses = 1;
        List<String> tokens = new ArrayList<>();

        tokens.add("(");
        tokens.add(operators[random.nextInt(operators.length)].getSymbol());

        while (numberOfOperations != 0 && numberOfOperands != 0) {
            int i = random.nextInt(3);
            if (i == 0 && numberOfOperations > 0) {
                tokens.add("(");
                tokens.add(operators[random.nextInt(operators.length)].getSymbol());
                tokens.add(operandSupplier.get());

                openParentheses++;
                numberOfOperations--;
                numberOfOperands--;
            } else if (i == 1 && numberOfOperands > numberOfOperations) {
                tokens.add(operandSupplier.get());
            } else if (i == 2 && openParentheses > 1) {
                tokens.add(")");
                openParentheses--;
            }
        }

        while (openParentheses-- > 0) {
            tokens.add(")");
        }

        return tokens;
    }

    private static Pair<Integer, Integer> simplifyFraction(Integer numerator, Integer denominator) {
        Integer gcd = new BigInteger(numerator.toString()).gcd(new BigInteger(denominator.toString())).intValue();

        return new Pair<>(numerator / gcd, denominator / gcd);
    }
}
