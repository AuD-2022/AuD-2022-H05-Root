package h05.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class BiBigDecimalProvider implements ArgumentsProvider {

    private static final long SEED = 0L;
    private static final int LOWER_BOUND = (int) -1E3;
    private static final int UPPER_BOUND = (int) 1E3;
    private static final Function<Random, BigDecimal> GENERATOR = random -> new BigDecimal(
        "%d.%d".formatted(random.nextLong(LOWER_BOUND, UPPER_BOUND), Math.abs(random.nextLong(LOWER_BOUND, UPPER_BOUND)))
    );
    private static final int STREAM_SIZE = 5;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Random random = new Random(SEED);

        return Stream.generate(() -> new BigDecimal[] {GENERATOR.apply(random), GENERATOR.apply(random)})
            .limit(STREAM_SIZE)
//            .map(value -> Arguments.of(value, value.subtract(MAX_DEVIATION), value.add(MAX_DEVIATION)));
            .map(Arguments::of);
    }
}
