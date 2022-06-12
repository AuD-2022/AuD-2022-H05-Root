package h05.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;

public class H1_2Provider implements ArgumentsProvider {

    private static final long SEED = 0L;
    private static final BigDecimal MAX_DEVIATION = new BigDecimal("0.001");
    private static final int STREAM_SIZE = 5;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Random random = new Random(SEED);

        return Stream.generate(() -> new BigDecimal("%d.%d".formatted(random.nextLong(), Math.abs(random.nextLong()))))
            .limit(STREAM_SIZE)
//            .map(value -> Arguments.of(value, value.subtract(MAX_DEVIATION), value.add(MAX_DEVIATION)));
            .map(Arguments::of);
    }
}
