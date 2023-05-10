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
//        for (int i = 0; i < encryptionSubKeys.size(); i++) {
//            if (i % 6 == 0) {
//                System.out.println("--------");
//            }
//            System.out.println(encryptionSubKeys.get(i));
//        }
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

    }

    public String encrypt(IDEAInputBlock inputBlock) {
        return applyRounds(inputBlock, encryptionSubKeys);
    }

    public String decrypt(String encrypted) {
        return "decrypted";
    }

    private String applyRounds(IDEAInputBlock inputBlock, List<BitArray> keys) {
        IDEARoundExecutor ideaRoundExecutor = new IDEARoundExecutor(inputBlock);

        for (int i = 0; i < 8; i++) {
            ideaRoundExecutor.apply(List.of(keys.get(i * 6), keys.get(i * 6 + 1), keys.get(i * 6 + 2),
                    keys.get(i * 6 + 3), keys.get(i * 6 + 4), keys.get(i * 6 + 5)));
            System.out.println(inputBlock.getBitArray().toHexString());
        }
        ideaRoundExecutor.applyOutputTransformation(List.of(keys.get(48), keys.get(49), keys.get(50), keys.get(51)));

        return inputBlock.getBitArray().toHexString();
    }
}
