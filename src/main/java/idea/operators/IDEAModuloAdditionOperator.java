package idea.operators;

import idea.BitArray;

public class IDEAModuloAdditionOperator implements IDEABinaryOperator {

    @Override
    public BitArray apply(BitArray binaryOne, BitArray binaryTwo) {
        int size = binaryOne.size();
        if (size != binaryTwo.size()) {
            throw new IllegalArgumentException("BitArrays of AdditionOperator must be of the same size.");
        }

        BitArray result = new BitArray(size);
        boolean overflow = false;
        boolean bitResultValue;
        for (int i = size - 1; i >= 0; i--) {
            boolean bitA = binaryOne.get(i);
            boolean bitB = binaryTwo.get(i);

            if (bitA && bitB || bitA && overflow || bitB && overflow) {
                bitResultValue = bitA && bitB && overflow;
                overflow = true;
            } else {
                bitResultValue = overflow || bitA || bitB;
                overflow = false;
            }
            result.set(i, bitResultValue);
        }
        return result;
    }
}
