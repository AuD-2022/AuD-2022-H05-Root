package h05.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Stream;

public class BigIntegerProvider implements ArgumentsProvider {

    private static final long SEED = 0L;
    private static final int STREAM_SIZE = 5;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Random random = new Random(SEED);

        return Stream.generate(() -> {
            BigInteger[] args = new BigInteger[] {
                new BigInteger(Long.toString(random.nextLong())),
                new BigInteger(Long.toString(random.nextLong()))
            };

            if (args[1].signum() == 0) {
                args[1] = BigInteger.ONE;
            }

            return args;
        })
            .limit(STREAM_SIZE)
            .map(Arguments::of);
    }
}
