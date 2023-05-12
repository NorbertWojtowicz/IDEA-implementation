package idea.operators;

import idea.BitArray;

import java.nio.ByteBuffer;

public class IDEAModuloMultiplierOperator implements IDEABinaryOperator {
    @Override
    public BitArray apply(BitArray binaryOne, BitArray binaryTwo) {
        long binaryOneInt = binaryOne.toInt();
        long binaryTwoInt = binaryTwo.toInt();

        long res = ((binaryOneInt * binaryTwoInt) % 65537);

        BitArray result;
        if(res == 65536) {
            byte[] empty2Bytes = ByteBuffer.allocate(2).array();
            result = BitArray.valueOf(empty2Bytes);
        } else {
            result = BitArray.fromBinaryString(Integer.toBinaryString((int) res), 16);
        }

        return result;
    }
}
