package h05.provider;

import h05.math.MyNumber;
import h05.math.MyReal;
import h05.tree.Identifier;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class IdentifierExpressionNodeProvider {

    private static final long SEED = 0L;
    private static final int STREAM_SIZE = 5;
    private static final Function<Random, String> IDENTIFIER_GENERATOR = random -> {
        StringBuilder builder = new StringBuilder();
        random.ints(5, 'A', 'Z' + 1)
            .forEach(i -> builder.append((char) i));
        return builder.toString();
    };
    private static final Function<Random, String> ILLEGAL_IDENTIFIER_GENERATOR = random -> {
        char[] chars = "-a-b-cDeFG".toCharArray();
        chars[random.nextInt(chars.length)] = '+';
        return new String(chars);
    };

    public static class Default implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> IDENTIFIER_GENERATOR.apply(random))
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class ConstructorInvalid implements ArgumentsProvider {
        public enum IdentifierType {NULL, ILLEGAL_ARGUMENT, ILLEGAL_IDENTIFIER}

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            IdentifierType[] identifierTypes = IdentifierType.values();
            Random random = new Random(SEED);

            return Stream.generate(() -> switch (identifierTypes[random.nextInt(identifierTypes.length)]) {
                    case NULL -> new Object[] {IdentifierType.NULL, null};
                    case ILLEGAL_ARGUMENT -> new Object[] {IdentifierType.ILLEGAL_ARGUMENT, ""};
                    case ILLEGAL_IDENTIFIER -> new Object[] {IdentifierType.ILLEGAL_IDENTIFIER, ILLEGAL_IDENTIFIER_GENERATOR.apply(random)};
                })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class EvaluateInvalid implements ArgumentsProvider {
        public enum IdentifierType {ILLEGAL_IDENTIFIER, UNDEFINED_IDENTIFIER}

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            IdentifierType[] identifierTypes = IdentifierType.values();
            Random random = new Random(SEED);

            return Stream.generate(() -> {
                Map<String, MyNumber> map = new HashMap<>();
                for (int i = 0; i < 5; i++) {
                    map.put(IDENTIFIER_GENERATOR.apply(random), MyReal.ZERO);
                }

                switch (identifierTypes[random.nextInt(identifierTypes.length)]) {
                    case ILLEGAL_IDENTIFIER:
                        Identifier[] preDefinedIdentifiers = Identifier.values();
                        Identifier identifier = preDefinedIdentifiers[random.nextInt(preDefinedIdentifiers.length)];
                        map.put(identifier.getName(), MyReal.ZERO);

                        return new Object[] {IdentifierType.ILLEGAL_IDENTIFIER, identifier.getName(), map};

                    case UNDEFINED_IDENTIFIER:
                        String randomIdentifier = IDENTIFIER_GENERATOR.apply(random);
                        while (map.containsKey(randomIdentifier)) {
                            randomIdentifier = IDENTIFIER_GENERATOR.apply(random);
                        }

                        return new Object[] {IdentifierType.UNDEFINED_IDENTIFIER, randomIdentifier, map};

                    default:
                        return null;
                }
            })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class EvaluateValid implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> {
                Map<String, MyNumber> map = new HashMap<>();
                for (int i = 0; i < 5; i++) {
                    map.put(IDENTIFIER_GENERATOR.apply(random), new MyReal(new BigDecimal(i)));
                }

                return new Object[] {map.keySet().stream().findAny().get(), map};
            })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }
}
