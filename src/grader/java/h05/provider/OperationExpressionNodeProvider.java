package h05.provider;

import h05.math.MyNumber;
import h05.math.MyReal;
import h05.tree.ArithmeticExpressionNode;
import h05.tree.ListItem;
import h05.tree.Operator;
import h05.utils.ListItemUtils;
import kotlin.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.*;
import java.util.stream.Stream;

import static h05.tree.Operator.*;

public class OperationExpressionNodeProvider {

    private static final long SEED = 0L;
    private static final int STREAM_SIZE = 5;
    private static final Map<Operator, Pair<Integer, Integer>> AMOUNT_OF_OPERANDS = Map.of(
        ADD, new Pair<>(0, 10),
        SUB, new Pair<>(1, 10),
        MUL, new Pair<>(0, 10),
        DIV, new Pair<>(1, 10),
        EXP, new Pair<>(1, 1),
        EXPT, new Pair<>(2, 2),
        LN, new Pair<>(1, 1),
        LOG, new Pair<>(2, 2),
        SQRT, new Pair<>(1, 1)
    );

    public static class Default implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Operator[] operators = Operator.values();
            Random random = new Random(SEED);

            return Stream.generate(() -> {
                Operator operator = operators[random.nextInt(operators.length)];
                Pair<Integer, Integer> range = AMOUNT_OF_OPERANDS.get(operator);
                List<ArithmeticExpressionNode> operands = new ArrayList<>();
                int numberOfOperands = random.nextInt(range.getFirst(), range.getSecond() + 1);

                for (int i = 0; i < numberOfOperands; i++) {
                    operands.add(new ArithmeticExpressionNodeImpl(String.valueOf((char) ('A' + i))));
                }

                return new Object[] {operator, ListItemUtils.fromList(operands)};
            })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    public static class ConstructorInvalid implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Operator[] operators = Operator.values();
            Random random = new Random(SEED);

            return random.ints(0, operators.length)
                .filter(i -> i != ADD.ordinal() && i != MUL.ordinal())
                .limit(STREAM_SIZE)
                .mapToObj(i -> Arguments.of(operators[i], null));
        }
    }

    private static class ArithmeticExpressionNodeImpl implements ArithmeticExpressionNode {

        private final String identifier;

        private ArithmeticExpressionNodeImpl(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public MyNumber evaluate(Map<String, MyNumber> identifiers) {
            return MyReal.ONE;
        }

        @Override
        public boolean isOperand() {
            return true;
        }

        @Override
        public boolean isOperation() {
            return false;
        }

        @Override
        public ArithmeticExpressionNode clone() {
            return new ArithmeticExpressionNodeImpl(identifier);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ArithmeticExpressionNodeImpl nodeImpl)) return false;
            return Objects.equals(identifier, nodeImpl.identifier);
        }
    }
}
