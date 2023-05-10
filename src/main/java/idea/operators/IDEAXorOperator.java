package idea.operators;

import idea.BitArray;

public class IDEAXorOperator implements IDEABinaryOperator {
    @Override
    public BitArray apply(BitArray binaryOne, BitArray binaryTwo) {
        int size = binaryOne.size();
        if (size != binaryTwo.size()) {
            throw new IllegalArgumentException("BitArrays of XorOperator must be of the same size.");
        }

        BitArray result = new BitArray(size);
        for (int i = 0;i < size; i++) {
            boolean bitA = binaryOne.get(i);
            boolean bitB = binaryTwo.get(i);
            result.set(i, bitA != bitB);
        }
        return result;
    }
}
