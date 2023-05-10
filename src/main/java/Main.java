import idea.IDEACipher;
import idea.IDEAInputBlock;
import idea.IDEAKey;

public class Main {
    public static void main(String[] args) {
        String toEncrypt = "Lorem ipsum mammon 60";
        String[] strBlocksToEncrypt = toEncrypt.split("(?<=\\G.{8})");
        IDEAKey ideaKey = IDEAKey.createFromString("szesnasciebajtow");
        IDEACipher ideaCipher = new IDEACipher(ideaKey);
        IDEAInputBlock[] ideaInputBlocks = new IDEAInputBlock[strBlocksToEncrypt.length];
        for (int i = 0; i < strBlocksToEncrypt.length; i++) {
            ideaInputBlocks[i] = IDEAInputBlock.createFromString(strBlocksToEncrypt[i]);
            ideaCipher.encrypt(ideaInputBlocks[i]);
        }
        System.out.println("Encrypted text:");
        for (IDEAInputBlock ideaInputBlock: ideaInputBlocks) {
            System.out.print(ideaInputBlock.getBitArray().toASCIIString());
        }
        System.out.println("\nDecrypting...");
        for (int i = 0; i < strBlocksToEncrypt.length; i++) {
            ideaCipher.decrypt(ideaInputBlocks[i]);
        }
        System.out.println("Decrypted text:");
        for (IDEAInputBlock ideaInputBlock: ideaInputBlocks) {
            System.out.print(ideaInputBlock.getBitArray().toASCIIString());
        }
    }
}
