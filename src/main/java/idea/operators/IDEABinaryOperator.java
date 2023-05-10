package idea.operators;

import idea.BitArray;

interface IDEABinaryOperator {
    BitArray apply(BitArray binaryOne, BitArray binaryTwo);
}
