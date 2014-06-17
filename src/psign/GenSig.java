/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psign;

/**
 *
 * @author DanishM
 */
import java.io.*;
import java.security.*;

class GenSig {

    public static void main(String[] args) {

        

        if (args.length != 1) {
            System.out.println("Usage: GenSig nameOfFileToSign");
        }
        else try {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
		
		KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
		
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(priv);
		
		FileInputStream fis = new FileInputStream("helloji");
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bufin.read(buffer)) >= 0) {
        dsa.update(buffer, 0, len);
         };
        bufin.close();
		
		byte[] realSig = dsa.sign();
		
		
        FileOutputStream sigfos = new FileOutputStream("E:/MMS/Dan.txt");
        sigfos.write(realSig);
        sigfos.close();
		
		byte[] key = pub.getEncoded();
        FileOutputStream keyfos = new FileOutputStream("E:/MMS/Danpub.txt");
        keyfos.write(key);
        keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}