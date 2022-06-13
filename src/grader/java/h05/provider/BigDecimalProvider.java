package h05.provider;

import h05.math.MyReal;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;

public class BigDecimalProvider implements ArgumentsProvider {

    private static final long SEED = 0L;
    private static final int LOWER_BOUND = (int) -1E4;
    private static final int UPPER_BOUND = (int) 1E4;
    private static final BigDecimal MAX_DEVIATION = new BigDecimal("0.001");
    private static final int STREAM_SIZE = 5;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Random random = new Random(SEED);

        return Stream.generate(() -> new BigDecimal("%d.%d".formatted(random.nextLong(LOWER_BOUND, UPPER_BOUND),
                Math.abs(random.nextLong(LOWER_BOUND, UPPER_BOUND)))))
            .limit(STREAM_SIZE)
            .map(bigDecimal -> bigDecimal.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE))
//            .map(value -> Arguments.of(value, value.subtract(MAX_DEVIATION), value.add(MAX_DEVIATION)));
            .map(Arguments::of);
    }
}
