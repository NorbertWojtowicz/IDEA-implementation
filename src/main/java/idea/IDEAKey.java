package idea;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IDEAKey {
    public static final int KEY_BYTES = 128;
    private BitArray bitArray;
    private IDEAKey(byte[] keyBytes) {
        initializeBitArray(keyBytes);
    }

    public static IDEAKey createFromString(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length * 8 != KEY_BYTES) {
            throw new RuntimeException("For security reasons, the key size must be " + KEY_BYTES + " bits.");
        }
        return new IDEAKey(keyBytes);
    }

    private void initializeBitArray(byte[] keyBytes) {
        bitArray = BitArray.valueOf(keyBytes);
    }

    public List<BitArray> getSubKeys(int blockPerSubKey) {
        List<BitArray> subKeys = new ArrayList<>();
        for (int i = 0; i < blockPerSubKey; i++) {
            subKeys.add(generateSubKey(i));
        }
        return subKeys;
    }

    private BitArray generateSubKey(int id) {
        BitArray subKeyBits = new BitArray(16);
        for (int i = 0; i < 16; i++) {
            subKeyBits.set(i, bitArray.get(id * 16 + i));
        }
        return subKeyBits;
    }

    public void shiftLeft(int shift) {
        bitArray = bitArray.shiftLeft(shift);
    }
}
