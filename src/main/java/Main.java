import idea.IDEACipher;
import idea.IDEAInputBlock;
import idea.IDEAKey;

public class Main {
    public static void main(String[] args) {
        String toEncrypt = "twobytes";
        String[] strBlocksToEncrypt = toEncrypt.split("(?<=\\G.{8})");
        IDEAKey ideaKey = IDEAKey.createFromString("examplesecretkey");
        IDEACipher ideaCipher = new IDEACipher(ideaKey);
        IDEAInputBlock[] ideaInputBlocks = new IDEAInputBlock[strBlocksToEncrypt.length];
        System.out.println("Encrypting...");
        for (int i = 0; i < strBlocksToEncrypt.length; i++) {
            ideaInputBlocks[i] = IDEAInputBlock.createFromString(strBlocksToEncrypt[i]);
            ideaCipher.encrypt(ideaInputBlocks[i]);
        }
        System.out.println("\nEncrypted text:");
        for (IDEAInputBlock ideaInputBlock: ideaInputBlocks) {
            System.out.print(ideaInputBlock.getBitArray().toASCIIString());
        }
        System.out.println("\n\nDecrypting...");
        for (int i = 0; i < strBlocksToEncrypt.length; i++) {
            ideaCipher.decrypt(ideaInputBlocks[i]);
        }
        System.out.println("\nDecrypted text:");
        for (IDEAInputBlock ideaInputBlock: ideaInputBlocks) {
            System.out.print(ideaInputBlock.getBitArray().toASCIIString());
        }
    }
}
