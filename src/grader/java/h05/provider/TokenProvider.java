package h05.provider;

import h05.exception.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class TokenProvider {

    private static final long SEED = 0L;
    private static final int STREAM_SIZE = 5;
    private static final Function<String, List<String>> TOKENIZER_FUNCTION = s -> Arrays.asList(s.split(" "));

    public static class SimpleInteger implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> makeArray(random))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }

        private static Object[] makeArray(Random random) {
            int result = 0;
            int numberOfOperands = random.nextInt(10) + 1;
            String operation = random.nextBoolean() ? "+" : "-";
            StringJoiner joiner = new StringJoiner(" ", "( ", " )")
                .add(operation);

            for (int i = 0; i < numberOfOperands; i++) {
                int value = random.nextInt(-1000, 1000);
                result = result + (operation.equals("+") ? value : -value);
                joiner.add(Integer.toString(value));
            }

            return new Object[] {result, TOKENIZER_FUNCTION.apply(joiner.toString())};
        }
    }

    public static class Invalid implements ArgumentsProvider {

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
}
