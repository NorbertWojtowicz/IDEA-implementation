package idea;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDEAInputBlock {
    private BitArray bitArray;
    private static int BLOCK_SIZE = 64;

    private IDEAInputBlock(byte[] blockBytes) {
        System.out.println(Arrays.toString(blockBytes));
        initializeBitArray(blockBytes);
    }

    public static IDEAInputBlock createFromString(String value) {
        byte[] blockBytes = new byte[BLOCK_SIZE / 8];
        byte[] bytesInString = value.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(bytesInString, 0, blockBytes, 0, bytesInString.length);
        if (blockBytes.length * 8 > BLOCK_SIZE) {
            throw new RuntimeException("Blok przekracza limit " + BLOCK_SIZE + " bitow...");
        }
        return new IDEAInputBlock(blockBytes);
    }

    public void assembleResultBitArrays(List<BitArray> bitArrays) {
        validateResultBitArrays(bitArrays);

        BitArray resultPartOne = bitArrays.get(0);
        BitArray resultPartTwo = bitArrays.get(1);
        BitArray resultPartThree = bitArrays.get(2);
        BitArray resultPartFour = bitArrays.get(3);

        for(int i =0; i < 64; i++) {
            boolean bitValue = false;

            if (i < 16) {
                bitValue = resultPartOne.get(i % 16);
            } else if (i < 32) {
                bitValue = resultPartTwo.get(i % 16);
            } else if (i < 48) {
                bitValue = resultPartThree.get(i % 16);
            } else {
                bitValue = resultPartFour.get(i % 16);
            }

            bitArray.set(i, bitValue);
        }
    }

    private static void validateResultBitArrays(List<BitArray> bitArrays) {
        if (bitArrays.size() != 4) {
            throw new IllegalArgumentException();
        }
        for (BitArray bArr: bitArrays) {
            if (bArr.size() != 16) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void initializeBitArray(byte[] blockBytes) {
        bitArray = BitArray.valueOf(blockBytes);
    }

    public BitArray getBitArray() {
        return bitArray;
    }

    public List<BitArray> getSubBlocks() {
        List<BitArray> subBlocks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            subBlocks.add(generateSubBlock(i));
        }
        return subBlocks;
    }

    public BitArray generateSubBlock(int id) {
        BitArray subBlockBits = new BitArray(16);
        for (int i = 0; i < 16; i++) {
            subBlockBits.set(i, bitArray.get(id * 16 + i));
        }
        return subBlockBits;
    }
}
