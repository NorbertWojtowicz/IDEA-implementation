package idea.operators;

import idea.BitArray;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class IDEAModuloMultiplierOperator implements IDEABinaryOperator {
    @Override
    public BitArray apply(BitArray binaryOne, BitArray binaryTwo) {
//        if(binaryOne.size() != 16 || binaryTwo.size() != 16) {
//            throw new IllegalArgumentException("ModuloMultiplier accepts only 16 bits values");
//        }

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
