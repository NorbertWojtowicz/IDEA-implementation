package idea;

import java.util.ArrayList;
import java.util.List;

public class IDEACipher {
    private IDEAKey key;
    private List<BitArray> encryptionSubKeys;
    private List<BitArray> decryptionSubKeys;

    public IDEACipher(IDEAKey key) {
        this.key = key;
        this.encryptionSubKeys = new ArrayList<>();
        this.decryptionSubKeys = new ArrayList<>();
        generateSubKeys();
    }

    private void generateSubKeys() {
        // Must be called before 'generateDecryptionSubKeys()'
        generateEncryptionSubKeys();
        generateDecryptionSubKeys();
    }

    private void generateEncryptionSubKeys() {
        for (int i = 0; i < 6; i++) {
            encryptionSubKeys.addAll(key.getSubKeys(8));
            key.shiftLeft(25);
        }
        // Add output transformation SubKeys
        encryptionSubKeys.addAll(key.getSubKeys(4));
    }

    private void generateDecryptionSubKeys() {
        generateOutputTransformationDecryptionKeys();
        for(int i = 7; i >= 0; i--) {
            decryptionSubKeys.add(encryptionSubKeys.get(i * 6 + 4));
            decryptionSubKeys.add(encryptionSubKeys.get(i * 6 + 5));
            decryptionSubKeys.add(encryptionSubKeys.get(i * 6).reverse());
            if (i == 0) {
                decryptionSubKeys.add(encryptionSubKeys.get(1).binaryTwosComplement());
                decryptionSubKeys.add(encryptionSubKeys.get(2).binaryTwosComplement());
            } else {
                decryptionSubKeys.add(encryptionSubKeys.get(i * 6 + 2).binaryTwosComplement());
                decryptionSubKeys.add(encryptionSubKeys.get(i * 6 + 1).binaryTwosComplement());
            }
            decryptionSubKeys.add(encryptionSubKeys.get(i * 6 + 3).reverse());
        }
    }

    private void generateOutputTransformationDecryptionKeys() {
        decryptionSubKeys.add(encryptionSubKeys.get(48).reverse());
        decryptionSubKeys.add(encryptionSubKeys.get(49).binaryTwosComplement());
        decryptionSubKeys.add(encryptionSubKeys.get(50).binaryTwosComplement());
        decryptionSubKeys.add(encryptionSubKeys.get(51).reverse());
    }

    public IDEAInputBlock encrypt(IDEAInputBlock inputBlock) {
        return applyRounds(inputBlock, encryptionSubKeys);
    }

    public IDEAInputBlock decrypt(IDEAInputBlock inputBlockToDecrypt) {
        return applyRounds(inputBlockToDecrypt, decryptionSubKeys);
    }

    private IDEAInputBlock applyRounds(IDEAInputBlock inputBlock, List<BitArray> keys) {
        IDEARoundExecutor ideaRoundExecutor = new IDEARoundExecutor(inputBlock);

        for (int i = 0; i < 8; i++) {
            ideaRoundExecutor.apply(List.of(keys.get(i * 6), keys.get(i * 6 + 1), keys.get(i * 6 + 2),
                    keys.get(i * 6 + 3), keys.get(i * 6 + 4), keys.get(i * 6 + 5)));
            System.out.println(inputBlock.getBitArray().toHexString() + " - result block of " + (i + 1) + " round");
        }
        ideaRoundExecutor.applyOutputTransformation(List.of(keys.get(48), keys.get(49), keys.get(50), keys.get(51)));
        System.out.println(inputBlock.getBitArray().toHexString() + " - result block after applying 'output transformation'");

        return inputBlock;
    }
}
