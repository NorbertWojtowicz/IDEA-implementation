import idea.IDEACipher;
import idea.IDEAInputBlock;
import idea.IDEAKey;

public class Main {
    public static void main(String[] args) {
        IDEAKey ideaKey = IDEAKey.createFromString("szesnasciebajtow");
        System.out.println(ideaKey.getBitArray());
        System.out.println(ideaKey.getBitArray().toASCIIString());
        IDEACipher ideaCipher = new IDEACipher(ideaKey);
        IDEAInputBlock ideaInputBlock = IDEAInputBlock.createFromString("secret23");
        System.out.println(ideaInputBlock.getBitArray());
        System.out.println(ideaInputBlock.getBitArray().toASCIIString());
        String encrypted = ideaCipher.encrypt(ideaInputBlock);
        System.out.println(encrypted);
    }
}
