package idea;

public class BitArray {
    private final int size;
    private boolean bitArray[];
    private static int BITS_IN_BYTE = 8;

    public BitArray(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("idea.BitArray size can't be negative.");
        }

        bitArray = new boolean[size];
        this.size = size;
    }

    public static BitArray valueOf(byte[] bytes) {
        BitArray resultBitArray = new BitArray(bytes.length * BITS_IN_BYTE);

        for (int i = 0; i < bytes.length; i++) {
            resultBitArray.add(bytes[i], i * BITS_IN_BYTE);
        }
        return resultBitArray;
    }

    public static BitArray fromBinaryString(String binaryString, int size) {
        BitArray bitArrayFromBinary = new BitArray(size);
        int numOff = size - binaryString.length();
        if (numOff < 0) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < binaryString.length(); i++) {
            bitArrayFromBinary.bitArray[i + numOff] = binaryString.charAt(i) == '1';
        }
        return bitArrayFromBinary;
    }

    private void add(byte byteVal, int indexFrom) {
        String bStr = Integer.toBinaryString(byteVal);
        String bStrWithLeadingZeros = String.format("%8s", bStr).replace(' ', '0');
        char[] bStrCharArray = bStrWithLeadingZeros.toCharArray();
        for (char c : bStrCharArray) {
            bitArray[indexFrom++] = '1' == c;
        }
    }

    public boolean get(int i) {
        return bitArray[i];
    }

    public void set(int i, boolean value) {
        bitArray[i] = value;
    }

    public void flip(int i) {
        bitArray[i] = !bitArray[i];
    }

    // TODO delete this method (available only for test cases)
    public boolean[] getBitArray() {
        return bitArray;
    }

    @Override
    public String toString() {
        return toHexString();
    }

    public int toInt() {
        int result = 0;
        for(int i = 0; i < size; i++) {
            result <<= 1;
            result += bitArray[i] ? 1 : 0;
        }
        return result;
    }

    public String toHexString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i += BITS_IN_BYTE) {
            int n = 0;
            for (int j = 0; j < BITS_IN_BYTE; j++) {
                n <<= 1;
                n += bitArray[i + j] ? 1 : 0;
            }
            sb.append(Integer.toHexString(0x100 | n).substring(1)).append(" ");
        }
        return "[" + sb.deleteCharAt(sb.length() - 1) + "]";
    }

    public String toASCIIString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i += BITS_IN_BYTE) {
            int n = 0;
            for (int j = 0; j < BITS_IN_BYTE; j++) {
                n <<= 1;
                n += bitArray[i + j] ? 1 : 0;
            }
            sb.append((char) n);
        }
        return sb.toString();
    }

    public BitArray shiftLeft(int shift) {
        boolean[] bitArrayShifted = new boolean[size];

        for (int i = 0; i < size; i++) {
            bitArrayShifted[i] = bitArray[(shift + i) % size];
        }
        return BitArray.fromBitArray(bitArrayShifted);
    }

    private static BitArray fromBitArray(boolean[] bitArray) {
        BitArray resultBitArray = new BitArray(bitArray.length);
        resultBitArray.bitArray = bitArray.clone();
        return resultBitArray;
    }

    public int size() {
        return size;
    }
}
